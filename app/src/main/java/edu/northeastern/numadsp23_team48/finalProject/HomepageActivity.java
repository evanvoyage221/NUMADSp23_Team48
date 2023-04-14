package edu.northeastern.numadsp23_team48.finalProject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.northeastern.numadsp23_team48.R;

public class HomepageActivity extends AppCompatActivity {
    private CardView findDoctor;
    private ImageButton signOutBtn, profileBtn;
    private TextView sayHiTV;
    private FirebaseFirestore db;
    DocumentReference userRef;
    private FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

//        find component by id and get current user
        init();

//        sign out / profile / findDoctor button listener
        clickListener();

    }
    private void clickListener() {
//        sign out button listener
        signOutBtn.setOnClickListener(view -> {
            auth = FirebaseAuth.getInstance();
//             TODO: CLEAN LOGIN INFO
            // FirebaseAuth.getInstance().signOut();

//             sign out for signInWithEmailAndPassword
            if (auth.getCurrentUser() != null) {
                auth.signOut();
            }

//             navigate to login activity and clear activity stack
            Intent intent = new Intent(HomepageActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

//        profile button listener
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(HomepageActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        findDoctor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepageActivity.this, FindDoctorActivity.class));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        signOutBtn = findViewById(R.id.signOutBtn);
        profileBtn = findViewById(R.id.profileBtn);
        findDoctor = findViewById(R.id.cardFindDoctor);
        sayHiTV = findViewById(R.id.say_hi);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        userRef = db.collection("Users").document(user.getUid());

        userRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Tag_0", error.getMessage());
                return;
            }

            if (value != null && value.exists()) {
                String name = value.getString("name");
//                Log.d("Tag_0", "name: " + name);
                sayHiTV.setText("Welcome, " + name);
            }
        });
    }
    
}
