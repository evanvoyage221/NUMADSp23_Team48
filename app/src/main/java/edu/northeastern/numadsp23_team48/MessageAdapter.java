package edu.northeastern.numadsp23_team48;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// TODO: Implement the RecyclerView.Adapter class
public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    List<ChatMessage> chats;

    public MessageAdapter(Context context, List<ChatMessage> chats) {
        this.context = context;
        this.chats = chats;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        TODO: Implement this method
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        TODO: Implement this method
    }

    @Override
    public int getItemCount() {
//        TODO: Implement this method
        return 0;
    }
}
