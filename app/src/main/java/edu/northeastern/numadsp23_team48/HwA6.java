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
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HwA6 extends AppCompatActivity {
    private EditText editText;
    private TypedArray imageResources;
    public static final String TAG = "A6 Activity";
    private RecyclerView catFactsRecyclerView;
    private ArrayList<String> factsList = new ArrayList<>();

    /**
     * A new Thread is created to execute the network call, and the response is obtained by
     * calling the execute method on the Call object, which blocks the thread until the response
     * is obtained.
     *
     * Once the response is obtained, a Handler is used to post a Runnable that updates the UI
     * on the main thread with the obtained data.
     *
     * Since the network call is not executed on a background thread, it's safe to call the
     * blocking execute method instead of the non-blocking enqueue method.
     * @param savedInstanceState the state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hw_a6);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        editText = findViewById(R.id.text_inputNum);
        Button submit = findViewById(R.id.btn_submitNum);
        catFactsRecyclerView = findViewById(R.id.recyclerview);
        imageResources = getResources().obtainTypedArray(R.array.image_resources);

        submit.setOnClickListener(view -> {
            Log.e(TAG, editText.getText().toString());
            ProgressBar progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE); // show progress bar

            new Thread(() -> {
                FetchData fetchData = RetrofitClient.getRetrofitInstance().create(FetchData.class);
                Call<CatFacts> call = fetchData.getAllData(Integer.parseInt(editText.getText().toString()));
                try {
                    Response<CatFacts> response = call.execute();
                    assert response.body() != null;
                    factsList = response.body().getData();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE); // hide progress bar

                        CatFactsAdapter newAdapter = new CatFactsAdapter(factsList, imageResources);
                        catFactsRecyclerView.setAdapter(newAdapter);
                        Log.e(TAG, "fact list size: " + factsList.size());
                        for (String fact : factsList) {
                            Log.e(TAG, "onResponse: fact: " + fact);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE); // failure, hide progress bar
                }
            }).start();
        });

        catFactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Save the state of the activity when the screen is rotated.
     * @param outState the state of the activity
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("factsList", factsList);
        outState.putBoolean("progressBar", findViewById(R.id.progress_bar).getVisibility() == View.VISIBLE);
        outState.putBoolean("catFactsRecyclerView", catFactsRecyclerView.getVisibility() == View.VISIBLE);
        outState.putBoolean("editText", editText.getVisibility() == View.VISIBLE);
    }

    /**
     * Restore the state of the activity when the screen is rotated.
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        factsList = savedInstanceState.getStringArrayList("factsList");
        findViewById(R.id.progress_bar).setVisibility(savedInstanceState.getBoolean("progressBar") ? View.VISIBLE : View.GONE);
        catFactsRecyclerView.setVisibility(savedInstanceState.getBoolean("catFactsRecyclerView") ? View.VISIBLE : View.GONE);
        editText.setVisibility(savedInstanceState.getBoolean("editText") ? View.VISIBLE : View.GONE);
        CatFactsAdapter newAdapter = new CatFactsAdapter(factsList, imageResources);
        catFactsRecyclerView.setAdapter(newAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Recycle the TypedArray to avoid memory leaks.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageResources.recycle();
    }
}