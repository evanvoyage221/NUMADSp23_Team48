package edu.northeastern.numadsp23_team48.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import edu.northeastern.numadsp23_team48.MessageActivity;
import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.viewholder.UserViewHolder;
import edu.northeastern.numadsp23_team48.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{

    private final List<User> users;
    private final Context context;
    String currentUsername;
    DatabaseReference messages;

    /**
     * Constructs a UserAdapter with the array list of user objects.
     * @param users arraylist of user object.
     * @param context context of the activity.
     */
    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user_card, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.userName.setText(users.get(position).getUserName());

        String currentUsername =  users.get(position).getCurrentUserName();
        String username = users.get(position).getUserName();

        //on click of each item
        holder.itemView.setOnClickListener(view -> {
            //Move to next activity.
            Bundle bundle = new Bundle();
            bundle.putString("uid", users.get(position).getUID());
            bundle.putString("userName", username);
            bundle.putString("currentUserName", currentUsername);

            Intent clickIntent = new Intent(context, MessageActivity.class);
            clickIntent.putExtras(bundle);
            context.startActivity(clickIntent);
        });
    }


    @Override
    public int getItemCount() {
        if(users == null)
            return 0;
        return users.size();
    }
}
