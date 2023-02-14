package edu.northeastern.numadsp23_team48;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numadsp23_team48.model.CatFacts;

public class HwA6 extends AppCompatActivity {
    RecyclerView catFactsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw_a6);

        // List<CatFacts> catFacts = new ArrayList<>(response);

        catFactsRecyclerView = findViewById(R.id.recyclerview);
        catFactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        catFactsRecyclerView.setAdapter(new CatFactsAdapter(getApplicationContext(), catFacts));
    }
}