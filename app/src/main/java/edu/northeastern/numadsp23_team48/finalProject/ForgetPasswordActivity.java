package edu.northeastern.numadsp23_team48.finalProject;

import static edu.northeastern.numadsp23_team48.finalProject.CreateAccountActivity.EMAIL_REGEX;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import edu.northeastern.numadsp23_team48.R;

public class ForgetPasswordActivity extends AppCompatActivity {

//    private ImageView loginTv;
    private Button recoverBtn;
    private TextInputLayout emailEt;

    private FirebaseAuth auth;
    private ImageView backBtn;

    private RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        init();

        clickListener();
    }

    private void init() {
        backBtn = findViewById(R.id.forget_password_back_btn);
        recoverBtn = findViewById(R.id.recoverBtn);
        emailEt = findViewById(R.id.emailET);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
    }

    private void clickListener() {
//        backBtn.setOnClickListener(v -> startActivity(new Intent(ForgetPasswordActivity.this, MainActivity.class)));

        backBtn.setOnClickListener(v -> onBackPressed());

        recoverBtn.setOnClickListener(v -> {

            String email = Objects.requireNonNull(emailEt.getEditText()).getText().toString().trim();

            if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                emailEt.setError("Input valid email");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Password reset email send successfully", Toast.LENGTH_SHORT).show();
                    //   emailEt.setText("");
                } else {
                    String errMsg = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(ForgetPasswordActivity.this, "Error: " + errMsg, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            });
        });
    }
}