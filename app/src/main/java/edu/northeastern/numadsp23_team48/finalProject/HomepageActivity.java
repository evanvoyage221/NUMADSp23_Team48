package edu.northeastern.numadsp23_team48.finalProject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numadsp23_team48.R;

public class HomepageActivity extends AppCompatActivity {
    private Switch logout;
    Boolean switchBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        logout = findViewById(R.id.switch_logout);
        logout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Log.d("switch:", "User logged out");
                    switchBoolean = true;
                    //TODO: CLEAN LOGIN INFO
                    startActivity(new Intent(HomepageActivity.this, LoginActivity.class));
                }
            }
        });
    }
    
}
