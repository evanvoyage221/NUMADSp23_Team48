package edu.northeastern.numadsp23_team48.finalProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numadsp23_team48.R;

public class HomepageActivity extends AppCompatActivity {
    private Switch logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        logout = findViewById(R.id.switch_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Clear login info
                startActivity(new Intent(HomepageActivity.this, LoginActivity.class));
            }
        });
    }
    
}
