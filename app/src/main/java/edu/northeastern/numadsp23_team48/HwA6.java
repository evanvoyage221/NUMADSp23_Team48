package edu.northeastern.numadsp23_team48;
import edu.northeastern.numadsp23_team48.model.CatFacts;
import edu.northeastern.numadsp23_team48.client.FetchData;
import edu.northeastern.numadsp23_team48.client.RetrofitClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HwA6 extends AppCompatActivity {
    private Button submit;
    private EditText editText;
    private TypedArray imageResources;
    public static final String TAG = "A6 Activity";
    private RecyclerView catFactsRecyclerView;
    private ArrayList<String> factsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw_a6);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        editText = findViewById(R.id.text_inputNum);
        submit = findViewById(R.id.btn_submitNum);
        catFactsRecyclerView = findViewById(R.id.recyclerview);
        imageResources = getResources().obtainTypedArray(R.array.image_resources);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,editText.getText().toString());
                FetchData fetchData = RetrofitClient.getRetrofitInstance().create(FetchData.class);
                Call<CatFacts> call = fetchData.getAllData(Integer.parseInt(editText.getText().toString()));
                call.enqueue(new Callback<CatFacts>() {
                    @Override
                    public void onResponse(Call<CatFacts> call, Response<CatFacts> response) {
                        Log.e(TAG,"on response: code: " + response.code());
                        factsList = response.body().getData();
                        CatFactsAdapter newAdapter = new CatFactsAdapter(factsList, imageResources);
                        catFactsRecyclerView.setAdapter(newAdapter);

                        Log.e(TAG,"fact list size: " + factsList.size());
                        for (String fact: factsList){
                            Log.e(TAG,"onResponse: fact: "+ fact);
                        }
                    }
                    @Override
                    public void onFailure(Call<CatFacts> call, Throwable t) {
                        Log.e(TAG,"onFailure: "+ t.getMessage());
                    }
                });
            }
        });
        catFactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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