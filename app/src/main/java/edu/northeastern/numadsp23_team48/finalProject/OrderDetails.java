package edu.northeastern.numadsp23_team48.finalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.finalProject.adapter.OrderAdapter;
import edu.northeastern.numadsp23_team48.finalProject.schema.MedicineModel;
import edu.northeastern.numadsp23_team48.finalProject.schema.OrderModel;

public class OrderDetails extends AppCompatActivity {
    private ArrayList<OrderModel> orders = new ArrayList<>();

    private RecyclerView recyclerViewOrders;

    private FirebaseAuth auth;
    private ImageButton signOutBtn, profileBtn;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        recyclerViewOrders = findViewById(R.id.list_order_detail);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        OrderAdapter adapter = new OrderAdapter(orders);
        recyclerViewOrders.setAdapter(adapter);

        signOutBtn = findViewById(R.id.signOutBtn);
        profileBtn = findViewById(R.id.profileBtn);
        btnBack = findViewById(R.id.btn_back);

        // Initialize the Firestore client
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get the currently authenticated user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Get a reference to the user's "Orders" collection
            CollectionReference ordersRef = db.collection("Users").document(userId).collection("Orders");

            // Read data from the "Orders" collection
            ordersRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            //String orderId = documentSnapshot.getId();
                            String itemName = documentSnapshot.getString("ItemName");
                            Timestamp date = documentSnapshot.getTimestamp("OrderDate");
                            String totalPrice = documentSnapshot.getString("totalPrice");

                            OrderModel order = new OrderModel(totalPrice,itemName,date.toDate().toString());
                            System.out.println(order.getTotalPrice());
                            orders.add(order);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(OrderDetails.this, "No order found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("Error getting orders: " + task.getException());
                }
            });
        } else {
            System.out.println("User not authenticated!");
        }

        signOutBtn.setOnClickListener(view -> {
            // FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();

            if (auth.getCurrentUser() != null) {
                auth.signOut();
            }

            // navigate to login activity and clear activity stack
            Intent intent = new Intent(OrderDetails.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // profile button listener
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(OrderDetails.this, ProfileActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(OrderDetails.this, HomepageActivity.class);
            startActivity(intent);
        });
    }
}