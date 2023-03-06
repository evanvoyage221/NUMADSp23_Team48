package edu.northeastern.numadsp23_team48;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.northeastern.numadsp23_team48.adapter.SentAdapter;
import edu.northeastern.numadsp23_team48.model.SentItem;

public class UserProfileActivity extends AppCompatActivity {
    RecyclerView rv;
    DatabaseReference dbr;
    SentAdapter adapter;
    ArrayList<SentItem> items;
    String userKey;
    String username;
    private static final String TAG = "";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle("YOUR PROFILE");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        userKey = getIntent().getExtras().getString("userKey");
        username = getIntent().getStringExtra("username");

        TextView displayUser = findViewById(R.id.user_name_profile);
        displayUser.setText("Username: " + username);


        rv = findViewById(R.id.sent_items);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        items = new ArrayList<>();
        adapter = new SentAdapter(UserProfileActivity.this, items);
        rv.setAdapter(adapter);

        dbr = FirebaseDatabase.getInstance().getReference("users");

        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot stickerCount = snapshot.child(userKey).child("stickerCountMap");

                for (DataSnapshot ds : stickerCount.getChildren()) {
                    String image = ds.getKey();
                    String count = String.valueOf(ds.getValue(Long.class));
                    SentItem item = new SentItem(image, count);
                    items.add(item);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void openAbout(View view){
        int id = view.getId();
        if (id == R.id.About){
            Intent intent = new Intent(UserProfileActivity.this, AboutAppActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
