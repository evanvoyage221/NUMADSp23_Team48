package edu.northeastern.numadsp23_team48.finalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
    private Button btnGoToCart, btnBack;
    private ArrayList<LabTestModel> labTestList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        btnGoToCart = findViewById(R.id.button_go_to_cart);
        btnBack = findViewById(R.id.button_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestActivity.this, HomepageActivity.class));
            }
        });

        list = new ArrayList();
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
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
                    item.put("line1", name);
                    item.put("line2", price);
                    item.put("line3", "");
                    item.put("line4", "");
                    item.put("line5", "");
                    list.add(item);
                }
                sa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //TODO: add to cart function
    }
}