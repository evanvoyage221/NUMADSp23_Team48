package edu.northeastern.numadsp23_team48;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class GroupProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_project);

        // Manually configure Firebase Options. The following fields are REQUIRED:
        //   - Project ID
        //   - App ID
        //   - API Key
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("team48-group-project")
                .setApplicationId("1:942190032581:android:43b68e409ceff1d945e2ac")
                .setApiKey("AIzaSyBs0-RQRh09CXfu-EDQAx3dmIabe8n99x0")
                // setDatabaseURL(...)
                // setStorageBucket(...)
                .build();

        // Initialize with group project options
        FirebaseApp.initializeApp(this, options, "groupProject");

        // Retrieve groupProject FirebaseApp
        FirebaseApp groupProjectApp = FirebaseApp.getInstance("groupProject");
    }
}