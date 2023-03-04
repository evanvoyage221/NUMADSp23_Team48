package edu.northeastern.numadsp23_team48.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
        SentItem sent = sentItems.get(position);
        holder.getCount().setText(sent.getCount());
        Log.d(TAG, "Image:" + sent.getImageID());

        switch(sent.getImageID()) {
            case "2131165271":
                holder.getSticker().setImageResource(R.drawable.cat_icon_1);
                break;
            case "2131165308":
                holder.getSticker().setImageResource(R.drawable.cat_icon_2);
                break;
            case "2131165309":
                holder.getSticker().setImageResource(R.drawable.cat_icon_3);
                break;
            case "2131165325":
                holder.getSticker().setImageResource(R.drawable.cat_icon_4);
                break;
            case "2131165368":
                holder.getSticker().setImageResource(R.drawable.cat_icon_5);
                break;
            case "2131165369":
                holder.getSticker().setImageResource(R.drawable.cat_icon_6);
                break;
            default:
                holder.getSticker().setImageResource(R.drawable.cat_icon_7);
                break;
        }
    }
    @Override
    public int getItemCount() {
        return sentItems.size();
    }
}
