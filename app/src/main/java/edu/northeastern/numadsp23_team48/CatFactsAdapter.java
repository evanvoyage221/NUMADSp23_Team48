package edu.northeastern.numadsp23_team48;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.northeastern.numadsp23_team48.model.CatFacts;

public class CatFactsAdapter extends RecyclerView.Adapter<CatFactsViewHolder> {
    private ArrayList<String> listOfCatFacts;
    private int[] imageResources = {R.drawable.cat_icon_1, R.drawable.cat_icon_2, R.drawable.cat_icon_3, R.drawable.cat_icon_4, R.drawable.cat_icon_5, R.drawable.cat_icon_6, R.drawable.cat_icon_7, R.drawable.cat_icon_8, R.drawable.cat_icon_9, R.drawable.cat_icon_10, R.drawable.cat_icon_11, R.drawable.cat_icon_12, R.drawable.cat_icon_13, R.drawable.cat_icon_14, R.drawable.cat_icon_15, R.drawable.cat_icon_16, R.drawable.cat_icon_17, R.drawable.cat_icon_18, R.drawable.cat_icon_19, R.drawable.cat_icon_20, R.drawable.cat_icon_21, R.drawable.cat_icon_22, R.drawable.cat_icon_23};

    public CatFactsAdapter(ArrayList<String> listOfCatFacts) {
        this.listOfCatFacts = listOfCatFacts;
    }

    @NonNull
    @Override
    public CatFactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CatFactsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_fact_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CatFactsViewHolder holder, int position) {
        Random random = new Random();
        int randomNumber = random.nextInt(imageResources.length);
        int imageResource = imageResources[randomNumber];

        holder.catFactsView.setText(listOfCatFacts.get(position));
        holder.imageView.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        return listOfCatFacts.size();
    }
}
