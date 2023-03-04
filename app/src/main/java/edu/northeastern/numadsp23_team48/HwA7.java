package edu.northeastern.numadsp23_team48;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import edu.northeastern.numadsp23_team48.model.User;

public class HwA7 extends AppCompatActivity {

//    Only for testing
//    Button testGetUser;
//    Button testSetUser;
//    String testUserName1 = "testUserName1";


//    creating a variable for user class
    User user;
//    creating a variable for our Firebase Database Reference.
    private DatabaseReference databaseReference;
//    creating a variable for our Firebase Database.
    FirebaseDatabase firebaseDatabase;
    // creating variables for EditText and buttons.
    private EditText userNameEdt;
    String userKey;
    private static final String TAG = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw_a7);

        // initializing our edittext and button
        userNameEdt = findViewById(R.id.editTextUserName);

        // instance of the Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference for the database.
        databaseReference = firebaseDatabase.getReference("");
    }

    public void openLogin(View view){
        int theId = view.getId();
        if (theId == R.id.loginButton) {
            // getting text from our edittext fields.
            String userName = userNameEdt.getText().toString();

            // below line is for checking whether the
            // edittext fields are empty or not.
            if (TextUtils.isEmpty(userName)) {
                // if the text fields are empty
                // then show the below message.
                Toast.makeText(HwA7.this, "Please enter user name.",
                        Toast.LENGTH_SHORT).show();
            } else {
                // else call the method to add
                // data to our database.
                addDataToFirebase(userName);
            }
        }
    }

    private void addDataToFirebase(String userName) {
        //flag to check if user was created.
        final boolean[] created = {true};
        final boolean[] exists = {false};
        // below 3 lines of code is used to set
        // data in our object class.
        user = new User(userName);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.child("users").getChildren()) {
                    if (userName.equalsIgnoreCase(Objects.requireNonNull(data.child("userName").getValue()).toString())) {
                        //user name exists
                        userKey = data.getKey();
                        Log.d(TAG, "Userkey: " + userKey);
                        exists[0] = true;
                        break;
                    }
                }

                if(!exists[0]) {
                    //user name does not exists, create new
                    // data base reference will sends data to firebase.
                    DatabaseReference db = databaseReference.child("users").push();
                    userKey = db.getKey();
                    Log.d(TAG, "Userkey: " + userKey);
                    db.setValue(user).addOnFailureListener(e -> {
                        //there was an issue
                        created[0] = false;
                        Toast.makeText(HwA7.this, "Unable to add user. Please try again later", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    created[0] = false;
                }

                if(created[0]) {
                    // after adding this data we are showing toast message.
                    Toast.makeText(HwA7.this, "New user added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HwA7.this, "User logged in successfully!", Toast.LENGTH_SHORT).show();
                }
                //Move to next activity.
                Intent clickIntent = new Intent(HwA7.this, AllUsersActivity.class);
                clickIntent.putExtra("currentUserName", userName);
                clickIntent.putExtra("userKey", userKey);
                startActivity(clickIntent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });


    }

//    private void getUserFromDb(View view) {
//        databaseReference.child("users").child(testUserName1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

//    private void addUserToDb(View view) {
//        User user = new User(testUserName1);
//        Task<Void> t1 = databaseReference.child("users").child(user.getUserName()).setValue(user);
//
//        if(!t1.isSuccessful()){
//            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
//        }
//
//    }


}