package edu.northeastern.numadsp23_team48.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numadsp23_team48.R;

public class CatFactsViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView catFactsView;

    public CatFactsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        catFactsView = itemView.findViewById(R.id.fact);
    }
}
