package edu.northeastern.numadsp23_team48.finalProject;

import static edu.northeastern.numadsp23_team48.HwA6.TAG;
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.finalProject.adapter.AppointmentAdapter;
import edu.northeastern.numadsp23_team48.finalProject.schema.AppointmentModel;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton signOutBtn, profileBtn;
    private TextView nameTv, statusTv;
    private FirebaseFirestore db;
    DocumentReference userRef;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private CircleImageView profileImage;
    private ImageButton editProfileBtn;
    private RecyclerView recyclerView;
    FirestoreRecyclerAdapter<AppointmentModel, AppointmentHolder> adapter;
    ArrayList<AppointmentModel> appointmentList = new ArrayList<>();
    AppointmentAdapter appointmentAdapter;

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
        profileImage = findViewById(R.id.profileImage);
        editProfileBtn = findViewById(R.id.edit_profileImage);
        nameTv = findViewById(R.id.nameTv);
        statusTv = findViewById(R.id.statusTV);
        recyclerView = findViewById(R.id.recyclerview);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        userRef = db.collection("Users").document(user.getUid());

        loadUserInfo();

        recyclerView.setItemAnimator(null);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        loadUserAppointment();

        appointmentAdapter = new AppointmentAdapter(this, appointmentList);
        recyclerView.setAdapter(appointmentAdapter);

    }

    private void loadUserAppointment() {

        Query query = userRef.collection("appointments").orderBy("time", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<AppointmentModel> options = new FirestoreRecyclerOptions.Builder<AppointmentModel>()
                .setQuery(query, AppointmentModel.class)
                .build();
        // Get a reference to the user's appointments collection
        CollectionReference appointmentsRef = userRef.collection("appointments");
        // Query the appointments collection to retrieve all the documents
        appointmentsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Create an AppointmentModel object with the appointment data
                    AppointmentModel appointmentModel = document.toObject(AppointmentModel.class);
                    appointmentList.add(appointmentModel);
                }
                // Call a method to display the appointmentList in a RecyclerView
                displayAppointmentsInRecyclerView(appointmentList);
            } else {
                Log.d(TAG, "Error getting appointments: ", task.getException());
            }
        });

    }

    private void displayAppointmentsInRecyclerView(ArrayList<AppointmentModel> appointmentList) {
        // Get a reference to the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        // Create a new instance of the RecyclerView adapter
        AppointmentAdapter adapter = new AppointmentAdapter(this, appointmentList);

        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(adapter);

        // Set the layout manager on the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                final String profileURL = value.getString("profileImage");

                nameTv.setText(name);
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

    public static class AppointmentHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, addressTextView, contactTextView, feeTextView, dateTextView, timeTextView;

        public AppointmentHolder(TextView idTextView, View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tvName);
            addressTextView = itemView.findViewById(R.id.tvAddress);
            contactTextView = itemView.findViewById(R.id.tvContact);
            feeTextView = itemView.findViewById(R.id.tvFee);
            dateTextView = itemView.findViewById(R.id.tvDate);
            timeTextView = itemView.findViewById(R.id.tvTime);
        }

        public void bind(AppointmentModel appointment) {
            nameTextView.setText(appointment.getName());
            addressTextView.setText(appointment.getHospital());
            contactTextView.setText(appointment.getContact());
            feeTextView.setText(appointment.getFees());
            dateTextView.setText(appointment.getDate());
            timeTextView.setText(appointment.getTime());
        }
    }


}