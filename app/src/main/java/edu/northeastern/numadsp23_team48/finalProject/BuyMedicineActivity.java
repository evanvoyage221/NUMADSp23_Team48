package edu.northeastern.numadsp23_team48.finalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.finalProject.adapter.MedicineAdapter;
import edu.northeastern.numadsp23_team48.finalProject.schema.MedicineModel;

public class BuyMedicineActivity extends AppCompatActivity {
    ArrayList medicineList = new ArrayList<MedicineModel>();
    MedicineAdapter adapter;
    RecyclerView recyclerView;
    Button placeOrder;
    private FirebaseAuth auth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);
        placeOrder = findViewById(R.id.btn_buy_medicine);
        auth = FirebaseAuth.getInstance();

        adapter = new MedicineAdapter(medicineList);

        recyclerView = findViewById(R.id.recyclerview_buy_medicine);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("Medicine Type");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MedicineModel medicineModel = dataSnapshot.getValue(MedicineModel.class);
                    medicineList.add(medicineModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //place order and write to database
                startActivity(new Intent(BuyMedicineActivity.this, HomepageActivity.class));
                Date date = Calendar.getInstance().getTime();

                FirebaseUser user = auth.getCurrentUser();
                auth.updateCurrentUser(user).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        // Create a new Random object
                        Random random = new Random();

                        // Generate a random integer between 1 and 3 (inclusive)
                        int randomNum = random.nextInt(3) + 1;
                        if (randomNum == 1) {
                            addUserAppointment(user, "Prescribed medicine", "200.00", date);
                        } else if (randomNum == 2) {
                            addUserAppointment(user, "Gastritis treatment", "250.99", date);
                        }else {
                            addUserAppointment(user, "Fever treatment", "60.59", date);
                        }
                        Toast.makeText(BuyMedicineActivity.this, "Your order has been placed successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        String exception = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(BuyMedicineActivity.this, "Error: " + exception, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void addUserAppointment(FirebaseUser user, String name, String price, Date date) {
        Map<String, Object> order = new HashMap<>();

        order.put("ItemName", name);
        order.put("totalPrice", price);
        order.put("OrderDate", date);


        String id = FirebaseFirestore.getInstance().collection("Users").document(user.getUid()).collection("Orders").document().getId();
        FirebaseFirestore.getInstance().collection("Users").document(user.getUid()).collection("Orders").document(id).set(order);
    }
}