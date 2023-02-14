package edu.northeastern.numadsp23_team48;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnA6 = findViewById(R.id.btn_a6);
        Button btnA7 = findViewById(R.id.btn_a7);
        Button btnGroup = findViewById(R.id.btn_group_project);

        btnA6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToA6Activity();
            }
        });

    }

    public void goToA6Activity(){
        Intent intent = new Intent(this, HwA6.class);
        startActivity(intent);
    }
}