package edu.northeastern.numadsp23_team48;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.northeastern.numadsp23_team48.finalProject.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnA6 = findViewById(R.id.btn_a6);
        Button btnA8 = findViewById(R.id.btn_a7);
        Button btnGroup = findViewById(R.id.btn_group_project);

        btnA6.setOnClickListener(view -> goToA6Activity());

        btnA8.setOnClickListener(view -> goToA7Activity());

        btnGroup.setOnClickListener(view -> goToGroupActivity());
    }

    public void goToA6Activity(){
        Intent intent = new Intent(this, HwA6.class);
        startActivity(intent);
    }

    public void goToA7Activity(){
        Intent intent = new Intent(this, HwA7.class);
        startActivity(intent);
    }

    public void goToGroupActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}