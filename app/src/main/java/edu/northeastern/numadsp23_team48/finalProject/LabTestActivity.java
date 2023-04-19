package edu.northeastern.numadsp23_team48.finalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.finalProject.schema.LabTestModel;

public class LabTestActivity extends AppCompatActivity {

    private ImageButton signOutBtn, profileBtn;
    private HashMap<String, String> item;
    private ArrayList list;
    private SimpleAdapter sa;
    private Button btnGoToOrder, btnBack;
    private ArrayList<LabTestModel> labTestList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        signOutBtn = findViewById(R.id.signOutBtn);
        profileBtn = findViewById(R.id.profileBtn);
//        btnGoToOrder = findViewById(R.id.button_go_to_order);
        btnBack = findViewById(R.id.button_back);

        signOutBtn.setOnClickListener(view -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();

            if (auth.getCurrentUser() != null) {
                auth.signOut();
            }

//             navigate to login activity and clear activity stack
            Intent intent = new Intent(LabTestActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

//        profile button listener
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LabTestActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestActivity.this, HomepageActivity.class));
            }
        });

        list = new ArrayList();
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines_lab_test,
                new String[]{"line1", "line2", "line3"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c});
        ListView listView = findViewById(R.id.lab_test_list);
        listView.setAdapter(sa);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("labTest");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    LabTestModel labTest = dataSnapshot.getValue(LabTestModel.class);
                    labTestList.add(labTest);
                    String name = labTest.getName();
                    String price = String.valueOf(labTest.getPrice());
                    item = new HashMap<String, String>();
                    item.put("line1", "Package: " + name);
                    item.put("line2", "");
                    item.put("line3", "Total cost: " + price);
                    list.add(item);
                }
                sa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // go to lab test details page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                intent.putExtra("name", labTestList.get(position).getName());
                intent.putExtra("price", labTestList.get(position).getPrice());
                intent.putExtra("details", labTestList.get(position).getDetails());
                startActivity(intent);
            }
        });
    }
}