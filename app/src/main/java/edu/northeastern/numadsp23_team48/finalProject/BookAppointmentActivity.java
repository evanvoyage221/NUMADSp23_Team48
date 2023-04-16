package edu.northeastern.numadsp23_team48.finalProject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.numadsp23_team48.R;

public class BookAppointmentActivity extends AppCompatActivity {
    private EditText ed1, ed2, ed3, ed4;
    private TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnBook, btnBack;
    private ImageButton signOutBtn, profileBtn;
    private FirebaseAuth auth;
    private String date;
    private String time;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        tv = findViewById(R.id.textViewDDTitle);
        ed1 = findViewById(R.id.editTextAppFullName);
        ed2 = findViewById(R.id.editTextAppAddress);
        ed3 = findViewById(R.id.editTextAppContactNumber);
        ed4 = findViewById(R.id.editTextAppFees);
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);
        btnBook = findViewById(R.id.buttonBookAppointment);
        btnBack = findViewById(R.id.buttonAppBack);
        signOutBtn = findViewById(R.id.signOutBtn);
        profileBtn = findViewById(R.id.profileBtn);
        auth = FirebaseAuth.getInstance();
        signOutBtn.setOnClickListener(view -> {

            if (auth.getCurrentUser() != null) {
                auth.signOut();
            }

//             navigate to login activity and clear activity stack
            Intent intent = new Intent(BookAppointmentActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

//        profile button listener
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(BookAppointmentActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String fullname = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        tv.setText(title);
        ed1.setText(fullname);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText(fees + "/-");

        initDatePicker();
        dateButton.setOnClickListener(v -> datePickerDialog.show());

        initTimePicker();
        timeButton.setOnClickListener(v -> timePickerDialog.show());

        btnBack.setOnClickListener(v -> startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class)));

        btnBook.setOnClickListener(v -> {
            startActivity(new Intent(BookAppointmentActivity.this, HomepageActivity.class));

            FirebaseUser user = auth.getCurrentUser();
            assert user != null;
            auth.updateCurrentUser(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    addUserAppointment(user, fullname, address, contact, fees, date, time);
                    Toast.makeText(BookAppointmentActivity.this, "Your appointment has been booked successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    String exception = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(BookAppointmentActivity.this, "Error: " + exception, Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void addUserAppointment(FirebaseUser user, String name, String address, String contact, String fee, String date, String time) {
        Map<String, Object> appointment = new HashMap<>();

        appointment.put("name", name);
        appointment.put("hospital", address);
        appointment.put("contact", contact);
        appointment.put("fees", fee);
        appointment.put("date", date);
        appointment.put("time", time);

        String id = FirebaseFirestore.getInstance().collection("Users").document(user.getUid()).collection("appointments").document().getId();
        FirebaseFirestore.getInstance().collection("Users").document(user.getUid()).collection("appointments").document(id).set(appointment);
    }

    private void initDatePicker() {
        @SuppressLint("SetTextI18n") DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month += 1;
            dateButton.setText(month + "/" + dayOfMonth + "/" + year);
            date = month + "/" + dayOfMonth + "/" + year;
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            timeButton.setText(hourOfDay + ":" + minute);
            time = hourOfDay + ":" + minute;
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hrs, mins, true);
    }
}