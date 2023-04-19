package edu.northeastern.numadsp23_team48.finalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.numadsp23_team48.R;

public class LabTestDetailsActivity extends AppCompatActivity {

    private ImageButton signOutBtn, profileBtn;
    private EditText etDetails;
    private TextView tvCost;
    private Button btnAddToOrder,btnBack;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        signOutBtn = findViewById(R.id.signOutBtn);
        profileBtn = findViewById(R.id.profileBtn);

        etDetails = findViewById(R.id.edit_text_multiLine);
        tvCost = findViewById(R.id.text_view_cost);

        btnBack = findViewById(R.id.button_back);
        btnAddToOrder = findViewById(R.id.button_add_to_order);

        auth = FirebaseAuth.getInstance();

        signOutBtn.setOnClickListener(view -> {
            // FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();

            if (auth.getCurrentUser() != null) {
                auth.signOut();
            }

            // navigate to login activity and clear activity stack
            Intent intent = new Intent(LabTestDetailsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // profile button listener
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LabTestDetailsActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // back button listener
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(LabTestDetailsActivity.this, LabTestActivity.class);
            startActivity(intent);
        });

        // disable edit text
        etDetails.setKeyListener(null);

        // get data from intent
        Intent intent = getIntent();
        etDetails.setText(intent.getStringExtra("details").replace("\\n", "\n"));
        tvCost.setText("Total Cost: " + intent.getIntExtra("price", 0));

        // add to order button listener
        btnAddToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabTestDetailsActivity.this, HomepageActivity.class);
                startActivity(intent);
                Date date = Calendar.getInstance().getTime();

                // add order to database
                FirebaseUser user = auth.getCurrentUser();
                auth.updateCurrentUser(user).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, Object> order = new HashMap<>();
                        order.put("ItemName", getIntent().getStringExtra("name"));
                        order.put("totalPrice", String.valueOf(getIntent().getIntExtra("price", 0)));
                        order.put("OrderDate", date);

                        // get order id and add order to database
                        String id = FirebaseFirestore.getInstance().collection("Users")
                                .document(user.getUid()).collection("Orders")
                                .document()
                                .getId();
                        FirebaseFirestore.getInstance().collection("Users")
                                .document(user.getUid())
                                .collection("Orders")
                                .document(id)
                                .set(order);

                        Toast.makeText(LabTestDetailsActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                    } else {
                        String exception = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(LabTestDetailsActivity.this, "Error: " + exception, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}