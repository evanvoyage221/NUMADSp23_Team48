package edu.northeastern.numadsp23_team48.finalProject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.numadsp23_team48.R;

public class FindDoctorActivity extends AppCompatActivity {
    private CardView familyPhysician;
    private CardView dietitian;
    private CardView dentist;
    private CardView surgeon;
    private CardView cardiologists;
    private CardView back;
    private ImageButton signOutBtn, profileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);
        familyPhysician = findViewById(R.id.cardFDFamilyPhysician);
        dietitian = findViewById(R.id.cardFDDietitian);
        dentist = findViewById(R.id.cardFDDentist);
        surgeon = findViewById(R.id.cardFDSurgeon);
        cardiologists = findViewById(R.id.cardFDCardiologists);
        back = findViewById(R.id.cardFDBack);
        signOutBtn = findViewById(R.id.signOutBtn);
        profileBtn = findViewById(R.id.profileBtn);
        signOutBtn.setOnClickListener(view -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            // FirebaseAuth.getInstance().signOut();

            if (auth.getCurrentUser() != null) {
                auth.signOut();
            }

//             navigate to login activity and clear activity stack
            Intent intent = new Intent(FindDoctorActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

//        profile button listener
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(FindDoctorActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        familyPhysician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
                it.putExtra("title", "Family Physicians");
                startActivity(it);
            }
        });
        dietitian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
                it.putExtra("title", "Dietitian");
                startActivity(it);
            }
        });
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
                it.putExtra("title", "Dentist");
                startActivity(it);
            }
        });
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
                it.putExtra("title", "Surgeon");
                startActivity(it);
            }
        });
        cardiologists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
                it.putExtra("title", "Cardiologists");
                startActivity(it);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindDoctorActivity.this, HomepageActivity.class));
            }
        });
    }
}