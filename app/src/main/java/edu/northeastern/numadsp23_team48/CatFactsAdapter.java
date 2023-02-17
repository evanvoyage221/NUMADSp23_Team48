package edu.northeastern.numadsp23_team48;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatFactsAdapter extends RecyclerView.Adapter<CatFactsViewHolder> {
    private final ArrayList<String> listOfCatFacts;
    private final TypedArray imageResources;

    public CatFactsAdapter(ArrayList<String> listOfCatFacts, TypedArray imageResources) {
        this.listOfCatFacts = listOfCatFacts;
        this.imageResources = imageResources;
    }

    @NonNull
    @Override
    public CatFactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CatFactsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_fact_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CatFactsViewHolder holder, int position) {
//        Log.d("CatFactsAdapter", "onBindViewHolder: " + position);
        if (position >= imageResources.length()) {
            position = position % imageResources.length();
        }
        Drawable imageDrawable = imageResources.getDrawable(position);

        holder.catFactsView.setText(listOfCatFacts.get(position));
        holder.imageView.setImageDrawable(imageDrawable);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
//        recycle should be called in the onDestroy method of the activity
//        imageResources.recycle();
    }
    @Override
    public int getItemCount() {
        return listOfCatFacts.size();
    }
}