package edu.northeastern.numadsp23_team48.finalProject;

import static edu.northeastern.numadsp23_team48.finalProject.utils.Constants.PREF_DIRECTORY;
import static edu.northeastern.numadsp23_team48.finalProject.utils.Constants.PREF_NAME;
import static edu.northeastern.numadsp23_team48.finalProject.utils.Constants.PREF_STORED;
import static edu.northeastern.numadsp23_team48.finalProject.utils.Constants.PREF_URL;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.northeastern.numadsp23_team48.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton signOutBtn, profileBtn;
    private TextView toolbarNameTv, nameTv, statusTv;
    private FirebaseFirestore db;
    DocumentReference userRef;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private CircleImageView profileImage;
    private ImageButton editProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        find component by id and get current user
        init();

//        sign out / profile / findDoctor button listener
        clickListener();
    }

    private void clickListener() {
        signOutBtn.setOnClickListener(view -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
//             TODO: CLEAN LOGIN INFO
            FirebaseAuth.getInstance().signOut();

//             sign out for signInWithEmailAndPassword
            if (auth.getCurrentUser() != null) {
                auth.signOut();
            }

//             navigate to login activity and clear activity stack
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

//        profile button listener
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void init() {
        signOutBtn = findViewById(R.id.signOutBtn);
        profileBtn = findViewById(R.id.profileBtn);
        toolbarNameTv = findViewById(R.id.toolbarNameTV);
        profileImage = findViewById(R.id.profileImage);
        editProfileBtn = findViewById(R.id.edit_profileImage);
        nameTv = findViewById(R.id.nameTv);
        statusTv = findViewById(R.id.statusTV);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        userRef = db.collection("Users").document(user.getUid());

        loadUserInfo();
    }

    private void loadUserInfo() {

        userRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Tag_0", error.getMessage());
                return;
            }

            if (value != null && value.exists()) {
                String name = value.getString("name");
                String status = value.getString("status");
                toolbarNameTv.setText(name);
                final String profileURL = value.getString("profileImage");

                nameTv.setText(name);
                toolbarNameTv.setText(name);
                assert status != null;
                if (!status.isEmpty() && !status.equals(" "))
                    statusTv.setText(status);
                try {

                    Glide.with(getApplicationContext())
                            .load(profileURL)
                            .placeholder(R.drawable.ic_person)
                            .circleCrop()
                            .listener(new RequestListener<>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                    Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                                    storeProfileImage(bitmap, profileURL);
                                    return false;
                                }
                            })
                            .timeout(6500)
                            .into(profileImage);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void storeProfileImage(Bitmap bitmap, String profileURL) {

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isStored = preferences.getBoolean(PREF_STORED, false);
        String urlString = preferences.getString(PREF_URL, "");

        SharedPreferences.Editor editor = preferences.edit();

        if (isStored && urlString.equals(profileURL))
            return;

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());

        File directory = contextWrapper.getDir("image_data", Context.MODE_PRIVATE);
        boolean result;
        if (!directory.exists())
            result = directory.mkdirs();

        File path = new File(directory, "profile.png");

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                assert outputStream != null;
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        editor.putBoolean(PREF_STORED, true);
        editor.putString(PREF_URL, profileURL);
        editor.putString(PREF_DIRECTORY, directory.getAbsolutePath());
        editor.apply();
    }

}