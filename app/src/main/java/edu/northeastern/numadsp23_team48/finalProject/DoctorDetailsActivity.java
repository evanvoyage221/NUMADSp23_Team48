package edu.northeastern.numadsp23_team48.finalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.model.Doctor;

public class DoctorDetailsActivity extends AppCompatActivity {
    private ImageButton signOutBtn, profileBtn;
    private TextView tv;
    private Button btn;
    private HashMap<String, String> item;
    private ArrayList list;
    private SimpleAdapter sa;
    private ArrayList<Doctor> doctorlist = new ArrayList();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewDDTitle);
        btn = findViewById(R.id.buttonDDBack);
        signOutBtn = findViewById(R.id.signOutBtn);
        profileBtn = findViewById(R.id.profileBtn);
        signOutBtn.setOnClickListener(view -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();

            if (auth.getCurrentUser() != null) {
                auth.signOut();
            }

//             navigate to login activity and clear activity stack
            Intent intent = new Intent(DoctorDetailsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

//        profile button listener
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorDetailsActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });
        list = new ArrayList<>();
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("doctorType").child(title);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Doctor doctor = dataSnapshot.getValue(Doctor.class);
                    doctorlist.add(doctor);
                    String doctorName = doctor.getDoctorName();
                    String hospital = doctor.getHospital();
                    String exp = doctor.getExp();
                    String mobile = doctor.getMobile();
                    String fee = String.valueOf(doctor.getFee());
                    item = new HashMap<String, String>();
                    item.put("line1", "Doctor Name: " + doctorName);
                    item.put("line2", "Hospital: " + hospital);
                    item.put("line3", "Experience: " + exp);
                    item.put("line4", "Mobile No.: " + mobile);
                    item.put("line5", "Fee: " + fee + "/-");
                    list.add(item);
                }
                sa.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2", "Doctor Name: " + doctorlist.get(position).getDoctorName());
                it.putExtra("text3", "Hospital: " + doctorlist.get(position).getHospital());
                it.putExtra("text4", "Mobile No.: " + doctorlist.get(position).getMobile());
                it.putExtra("text5", "Fee: " + doctorlist.get(position).getFee());
                startActivity(it);
            }
        });
    }
}