package edu.northeastern.numadsp23_team48.finalProject;

import static edu.northeastern.numadsp23_team48.finalProject.CreateAccountActivity.EMAIL_REGEX;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.numadsp23_team48.MainActivity;
import edu.northeastern.numadsp23_team48.R;


// Note: This Activity use cloud firestore to store user data instead of realtime database
// User data is stored in the collection "users" including,
// user's name, email, profileImage, uid and status
public class LoginActivity extends AppCompatActivity {

    /**
     * The TextInputEditText class is provided to be used as the input text child of this layout.
     */
    private TextInputLayout emailEt, passwordEt;
    private Button signUpBtn, forget_password;
    private Button loginBtn, googleSignInBtn;
    private RelativeLayout progressBar;
    private ImageView backBtn;

    private static final int RC_SIGN_IN = 53;
    GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> signInLauncher;


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        clickListener();
    }

    private void init() {

        emailEt = findViewById(R.id.emailET);
        passwordEt = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.letTheUserLogIn);
        googleSignInBtn = findViewById(R.id.googleSignInBtn);
        signUpBtn = findViewById(R.id.SignUpBtn);
        forget_password = findViewById(R.id.forget_password);
        backBtn = findViewById(R.id.login_back_button);
        progressBar = findViewById(R.id.login_progress_bar);

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        default showing the google selection dialog
        mGoogleSignInClient.revokeAccess();

        signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    assert account != null;
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("TAG", "Google sign in failed", e);
                    // ...
                }
            }
        });
    }


    private void clickListener() {

//        if user click on the back button, it will go back to the main activity
        backBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));

        forget_password.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class)));

        loginBtn.setOnClickListener(v -> {

            String email = Objects.requireNonNull(emailEt.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(passwordEt.getEditText()).getText().toString().trim();

            if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                emailEt.setError("Input valid email");
                return;
            }

            if (password.isEmpty() || password.length() < 6) {
                passwordEt.setError("Input 6-digit valid password");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    FirebaseUser user = auth.getCurrentUser();

                    assert user != null;
                    if (user.isEmailVerified()) {
                        sendUserToMainActivity();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        auth.signOut();
                        Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_SHORT)
                                .show();
                    }

                } else {
                    String exception = "Error: " + Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(LoginActivity.this, exception, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
        googleSignInBtn.setOnClickListener(v -> signIn());

        signUpBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));
    }

    private void sendUserToMainActivity() {

        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
        finish();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInLauncher.launch(signInIntent);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                throw new RuntimeException(e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = auth.getCurrentUser();
                        assert user != null;
                        updateUi(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                        String exception = "Error: " + Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(LoginActivity.this, exception, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void updateUi(FirebaseUser user) {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);

        Map<String, Object> map = new HashMap<>();

        assert account != null;
        map.put("name", account.getDisplayName());
        map.put("email", account.getEmail());
        map.put("profileImage", String.valueOf(account.getPhotoUrl()));
        map.put("uid", user.getUid());
        map.put("status", " ");
//        TODO: maybe other information can be added


        FirebaseFirestore.getInstance()
                .collection("Users")
                .document(user.getUid())
                .set(map)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        sendUserToMainActivity();

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this,
                                        "Error: " + Objects.requireNonNull(task.getException())
                                                .getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}