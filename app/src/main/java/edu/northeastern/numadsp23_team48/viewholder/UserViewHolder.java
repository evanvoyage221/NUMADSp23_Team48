package edu.northeastern.numadsp23_team48.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numadsp23_team48.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public TextView userName;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        this.userName = itemView.findViewById(R.id.username_text);
    }
}
