package edu.northeastern.numadsp23_team48;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

// Class for the chat activity. This class is responsible for displaying the chat between two users.
// It also allows the user to send stickers to the other user.
public class MessageActivity extends AppCompatActivity {
    // creating a variable for Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for reference for Firebase.
    DatabaseReference databaseReference;
    DatabaseReference messages;

    //sticker chosen by the user to send.
    public static int chosenImageId = 0;

    //bundle with data from previous activity.
    Bundle bundle = null;

    //unique id which represent two users.
    String chatId = null;

    //Recycler View and list of chat
    private RecyclerView messageRecyclerView;

    ArrayList<ChatMessage> chatMessageList;

    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // instance of the Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference for the database.
        databaseReference = firebaseDatabase.getReference("");

        //get the text view in which the different usernames must be displayed.
        bundle = getIntent().getExtras();
        TextView displayUserNameTV = findViewById(R.id.displayUserName);
        displayUserNameTV.setText(bundle.getString("userName"));

        if (savedInstanceState == null) {
            chatMessageList = new ArrayList<>();
        } else {
            chatMessageList = savedInstanceState.getParcelableArrayList("chatMessageList");
        }

        int compare = bundle.getString("currentUserName").compareTo(bundle.getString("userName"));
        if (compare < 0) {
            chatId = bundle.getString("currentUserName") + bundle.getString("userName");
        } else if (compare > 0) {
            chatId = bundle.getString("userName") + bundle.getString("currentUserName");
        }

        // list all the stickers in horizontal scroll view.
        addStickersList();

        //Link to recycle view.
        messageRecyclerView = findViewById(R.id.user_recycler_view);

        //Set the layout manager for the recycle view.
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Set the custom adapter to the recycle view.
        //recyclerView.setAdapter(new ChatAdapter(chatMessageList, this));
        adapter = new MessageAdapter(this, chatMessageList);
        messageRecyclerView.setAdapter(adapter);

        //Decoration to add line after each item in the view.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);

        messageRecyclerView.addItemDecoration(dividerItemDecoration);


        messages = databaseReference.child("chats").child(chatId);
        messages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // TODO: Add the new message to the chat.
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void addStickersList() {
//        TODO: Add the list of stickers to the horizontal scroll view.
    }

    /**
     * Method called when user clicks on send button.
     *
     * @param view current view.
     */
    public void sendButtonClicked(View view) {
//        TODO: Add the message to the chat.
    }


    private void sendNotification(int image, String sender) {
//        TODO: Send the notification to the other user.
    }

    private void displayChatSendNotif(DataSnapshot snapshot) {
//        TODO: Display the chat and send the notification.
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("chatMessageList", chatMessageList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        state.putParcelableArrayList("chatMessageList", chatMessageList);
    }
}
