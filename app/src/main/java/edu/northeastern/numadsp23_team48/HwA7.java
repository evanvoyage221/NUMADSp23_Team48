package edu.northeastern.numadsp23_team48;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.numadsp23_team48.model.User;

public class HwA7 extends AppCompatActivity {
    Button testGetUser;
    Button testSetUser;
    private DatabaseReference mDatabase;
    String testUserName1 = "testUserName1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw_a7);

        testGetUser = findViewById(R.id.btn_getInfo);
        testSetUser = findViewById(R.id.btn_setInfo);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        testSetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserToDb(view);
            }
        });

        testGetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserFromDb(view);
            }
        });
    }

    /***
     *
     * test method for getting info from db
     */
    private void getUserFromDb(View view) {
        mDatabase.child("users").child(testUserName1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /***
     *
     * test method for writing info from db
     */

    private void addUserToDb(View view) {
        User user = new User(testUserName1);
        Task<Void> t1 = mDatabase.child("users").child(user.getUsername()).setValue(user);

        if(!t1.isSuccessful()){
            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
        }

    }


}