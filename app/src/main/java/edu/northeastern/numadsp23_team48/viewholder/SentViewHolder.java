package edu.northeastern.numadsp23_team48.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numadsp23_team48.R;

public class SentViewHolder extends RecyclerView.ViewHolder{

    TextView count;
    ImageView sticker;

    public SentViewHolder(@NonNull View itemView) {
        super(itemView);
        this.count = itemView.findViewById(R.id.sent_sticker_count);
        this.sticker = itemView.findViewById(R.id.sent_sticker);
    }

    public TextView getCount() {
        return count;
    }

    public ImageView getSticker() {
        return sticker;
    }
}
