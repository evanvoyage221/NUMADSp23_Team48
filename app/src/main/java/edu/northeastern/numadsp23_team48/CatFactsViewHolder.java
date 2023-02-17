package edu.northeastern.numadsp23_team48;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CatFactsViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView catFactsView;
    //private Context context;

    public CatFactsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        catFactsView = itemView.findViewById(R.id.fact);
    }

    public TextView getCatFactsView() {
        return catFactsView;
    }
}
