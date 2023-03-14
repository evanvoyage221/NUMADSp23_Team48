package edu.northeastern.numadsp23_team48.finalProject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.numadsp23_team48.R;

public class CreateAccountActivity extends Activity {
    /**
     * username regex: start with a letter, 4-30 characters, only letters, numbers, and underscores
     */
    public static final String USERNAME_REGEX = "^[A-Za-z]\\w{3,29}$";
    public static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private TextInputLayout nameEt, emailEt, passwordEt;
    private RelativeLayout progressBar;
    private Button signup_login_button, nextBtn, signup_back_button;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        init();

        clickListener();
    }

    private void init() {
        nameEt = findViewById(R.id.signup_username);
        emailEt = findViewById(R.id.signup_email);
        passwordEt = findViewById(R.id.signup_password);
        signup_login_button = findViewById(R.id.signup_login_button);
        nextBtn = findViewById(R.id.signup_next_button);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
    }

    private void clickListener() {

        signup_login_button.setOnClickListener(v -> {
            startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
            finish();
        });

        nextBtn.setOnClickListener(v -> {

            String name = Objects.requireNonNull(nameEt.getEditText()).getText().toString().trim();
            String email = Objects.requireNonNull(emailEt.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(passwordEt.getEditText()).getText().toString().trim();

            if (name.isEmpty() || !name.matches(USERNAME_REGEX)) {
                nameEt.setError("Please input valid name");
                return;
            }

            if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                emailEt.setError("Please input valid email");
                return;
            }

            if (password.isEmpty() || password.length() < 6) {
                passwordEt.setError("Please input valid password");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            createAccount(name, email, password);

        });
    }

    private void createAccount(final String name, final String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                FirebaseUser user = auth.getCurrentUser();

//                        TODO: replace this with a real image
                String image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwp--EwtYaxkfsSPIpoSPucdbxAo6PancQX1gw6ETSKI6_pGNCZY4ts1N6BV5ZcN3wPbA&usqp=CAU";

                UserProfileChangeRequest.Builder request = new UserProfileChangeRequest.Builder();
                request.setDisplayName(name);
                request.setPhotoUri(Uri.parse(image));

                assert user != null;
                user.updateProfile(request.build());

                user.sendEmailVerification().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(CreateAccountActivity.this, "Email verification link sent", Toast.LENGTH_SHORT).show();
                    }
                });
                uploadUser(user, name, email);
            } else {
                progressBar.setVisibility(View.GONE);
                String exception = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(CreateAccountActivity.this, "Error: " + exception, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadUser(FirebaseUser user, String name, String email) {

        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("email", email);
        map.put("profileImage", " ");
        map.put("uid", user.getUid());
        map.put("status", " ");

//        search drugs?
//        map.put("search", name.toLowerCase());

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid()).set(map).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                finish();

            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CreateAccountActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
