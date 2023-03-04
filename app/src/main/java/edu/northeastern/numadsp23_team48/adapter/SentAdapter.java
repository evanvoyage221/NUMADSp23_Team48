package edu.northeastern.numadsp23_team48.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.model.SentItem;
import edu.northeastern.numadsp23_team48.viewholder.SentViewHolder;

public class SentAdapter extends RecyclerView.Adapter<SentViewHolder> {

    private static final String TAG = "";
    Context context;
    ArrayList<SentItem> sentItems;

    public SentAdapter(Context context, ArrayList<SentItem> sentItems) {
        this.context = context;
        this.sentItems = sentItems;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public SentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SentViewHolder(LayoutInflater.from(context).inflate(R.layout.sent_number_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull SentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return sentItems.size();
    }
}
