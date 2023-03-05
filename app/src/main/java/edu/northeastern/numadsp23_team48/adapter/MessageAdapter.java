package edu.northeastern.numadsp23_team48.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.model.ChatMessage;
import edu.northeastern.numadsp23_team48.viewholder.UserViewHolder;

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
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_message, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // TODO: What's the design in the activity_message.xml
        ChatMessage chatMessage = chats.get(position);

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}