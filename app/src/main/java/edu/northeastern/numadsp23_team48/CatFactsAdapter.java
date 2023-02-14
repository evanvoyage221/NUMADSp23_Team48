package edu.northeastern.numadsp23_team48;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import edu.northeastern.numadsp23_team48.model.CatFacts;

public class CatFactsAdapter extends RecyclerView.Adapter<CatFactsViewHolder> {
    private final ArrayList<String> listOfCatFacts;
    private final Context context;

    public CatFactsAdapter(ArrayList<String> listOfCatFacts, Context context) {
        this.listOfCatFacts = listOfCatFacts;
        this.context = context;
    }

    @NonNull
    @Override
    public CatFactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CatFactsViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_fact_view, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull CatFactsViewHolder holder, int position) {
        holder.catFactsView.setText(listOfCatFacts.get(position));
        // holder.imageView.setImageResource();

    }

    @Override
    public int getItemCount() {
        return listOfCatFacts.size();
    }
}