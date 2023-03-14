package edu.northeastern.numadsp23_team48;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.numadsp23_team48.adapter.MessageAdapter;
import edu.northeastern.numadsp23_team48.model.ChatMessage;

// Class for the chat activity. This class is responsible for displaying the chat between two users.
// It also allows the user to send stickers to the other user.
public class MessageActivity extends AppCompatActivity {
    // creating a variable for Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for reference for Firebase.
    DatabaseReference databaseReference;
    DatabaseReference messages;

    // sticker chosen by the user to send.
    public static int chosenImageId = 0;
    private static final String channelId = "NUMAD_channel_id";
    private static final int NOTIFICATION_UNIQUE_ID = 13;

    // bundle with data from previous activity.
    Bundle bundle = null;

    // unique id which represent two users.
    String chatId = null;

    // Recycler View and list of chat
    private RecyclerView messageRecyclerView;

    ArrayList<ChatMessage> chatMessageList;

    MessageAdapter adapter;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        // instance of the Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference for the database.
        databaseReference = firebaseDatabase.getReference("");

        // get the text view in which the different usernames must be displayed.
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

        // Link to recycle view.
        messageRecyclerView = findViewById(R.id.user_recycler_view);

        // Set the layout manager for the recycle view.
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the custom adapter to the recycle view.
        // recyclerView.setAdapter(new ChatAdapter(chatMessageList, this));
        adapter = new MessageAdapter(this, chatMessageList);
        messageRecyclerView.setAdapter(adapter);

        // Decoration to add line after each item in the view.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);

        messageRecyclerView.addItemDecoration(dividerItemDecoration);


        messages = databaseReference.child("chats").child(chatId);
        messages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                displayChatSendNotif(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                displayChatSendNotif(snapshot);
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

    /**
     * This is a method that adds stickers to a LinearLayout.
     * It starts by getting a reference to the LinearLayout with id "stickers" using findViewById.
     * It then uses a LayoutInflater to inflate six views from the "sticker" layout resource file,
     * one for each sticker.
     *
     * For each sticker, it sets the ImageView resource to a drawable resource,
     * sets an ID for the ImageView, and adds the view to the sticker LinearLayout using addView.
     *
     * It then sets onClickListeners for each ImageView that change the background color of
     * the selected ImageView to highlight it,
     * and set the chosenImageId to the ID of the selected ImageView.
     *
     * Finally, it adds the last view to the sticker LinearLayout and returns.
     */
    @SuppressLint("ResourceType")
    private void addStickersList() {

        LinearLayout sticker = findViewById(R.id.stickers);

        LayoutInflater inflater = LayoutInflater.from(this);

//         cat icon 1 sticker
        View view1 = inflater.inflate(R.layout.sticker, sticker, false);

        imageView1 = view1.findViewById(R.id.stickerImageView);
        imageView1.setImageResource(R.drawable.cat_icon_1);
        imageView1.setId(2131165271);
        sticker.addView(view1);

//        cat icon 2 sticker
        View view2 = inflater.inflate(R.layout.sticker, sticker, false);

        imageView2 = view2.findViewById(R.id.stickerImageView);
        imageView2.setImageResource(R.drawable.cat_icon_2);
        imageView2.setId(2131165308);

        sticker.addView(view2);

//        cat icon 3 sticker
        View view3 = inflater.inflate(R.layout.sticker, sticker, false);

        imageView3 = view3.findViewById(R.id.stickerImageView);
        imageView3.setImageResource(R.drawable.cat_icon_3);
        imageView3.setId(2131165309);

        sticker.addView(view3);

//        cat icon 4 sticker
        View view4 = inflater.inflate(R.layout.sticker, sticker, false);

        imageView4 = view4.findViewById(R.id.stickerImageView);
        imageView4.setImageResource(R.drawable.cat_icon_4);
        imageView4.setId(2131165325);

        sticker.addView(view4);

//        cat icon 5 sticker
        View view5 = inflater.inflate(R.layout.sticker, sticker, false);

        imageView5 = view5.findViewById(R.id.stickerImageView);
        imageView5.setImageResource(R.drawable.cat_icon_5);
        imageView5.setId(2131165368);

        sticker.addView(view5);

//        cat icon 6 sticker
        View view6 = inflater.inflate(R.layout.sticker, sticker, false);

        imageView6 = view6.findViewById(R.id.stickerImageView);
        imageView6.setImageResource(R.drawable.cat_icon_6);
        imageView6.setId(2131165369);

//        setting on click listeners, change background color on click
        imageView1.setOnClickListener(view -> {
            imageView1.setBackgroundColor(Color.rgb(218, 246, 169));
            imageView2.setBackgroundColor(0);
            imageView3.setBackgroundColor(0);
            imageView4.setBackgroundColor(0);
            imageView5.setBackgroundColor(0);
            imageView6.setBackgroundColor(0);

            chosenImageId = view.getId();
        });
        imageView2.setOnClickListener(view -> {
            imageView1.setBackgroundColor(0);
            imageView2.setBackgroundColor(Color.rgb(218, 246, 169));
            imageView3.setBackgroundColor(0);
            imageView4.setBackgroundColor(0);
            imageView5.setBackgroundColor(0);
            imageView6.setBackgroundColor(0);

            chosenImageId = view.getId();
        });
        imageView3.setOnClickListener(view -> {
            imageView1.setBackgroundColor(0);
            imageView2.setBackgroundColor(0);
            imageView3.setBackgroundColor(Color.rgb(218, 246, 169));
            imageView4.setBackgroundColor(0);
            imageView5.setBackgroundColor(0);
            imageView6.setBackgroundColor(0);

            chosenImageId = view.getId();
        });

        imageView4.setOnClickListener(view -> {
            imageView1.setBackgroundColor(0);
            imageView2.setBackgroundColor(0);
            imageView3.setBackgroundColor(0);
            imageView4.setBackgroundColor(Color.rgb(218, 246, 169));
            imageView5.setBackgroundColor(0);
            imageView6.setBackgroundColor(0);

            chosenImageId = view.getId();
        });
        imageView5.setOnClickListener(view -> {
            imageView1.setBackgroundColor(0);
            imageView2.setBackgroundColor(0);
            imageView3.setBackgroundColor(0);
            imageView4.setBackgroundColor(0);
            imageView5.setBackgroundColor(Color.rgb(218, 246, 169));
            imageView6.setBackgroundColor(0);

            chosenImageId = view.getId();
        });
        imageView6.setOnClickListener(view -> {
            imageView1.setBackgroundColor(0);
            imageView2.setBackgroundColor(0);
            imageView3.setBackgroundColor(0);
            imageView4.setBackgroundColor(0);
            imageView5.setBackgroundColor(0);
            imageView6.setBackgroundColor(Color.rgb(218, 246, 169));

            chosenImageId = view.getId();
        });

        sticker.addView(view6);
    }

    /**
     * Method called when user clicks on send button.
     *
     * In this function, databaseReference is a reference to the Firebase Realtime Database,
     * chatId is the unique ID of the current chat, and chatMessage is the ChatMessage object
     * that needs to be written to the database.
     *
     * The push() method generates a new child location under the specified chats node, with a unique key,
     * and setValue() writes the ChatMessage object to this location.
     * If the write operation fails, an OnFailureListener is triggered, which displays an error message.
     *
     * @param view current view.
     */
    public void sendButtonClicked(View view) {
    
        if(chosenImageId == 0){
            Toast.makeText(MessageActivity.this, "Please choose a sticker", Toast.LENGTH_SHORT).show();
            return;
        }

        // create a chat message object.
        ChatMessage chatMessage = new ChatMessage(chosenImageId, bundle.getString("currentUserName"), bundle.getString("userName"), "unread");

        databaseReference.child("chats").child(chatId).push().setValue(chatMessage).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MessageActivity.this, "Unable to send sticker. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });

        // update sticker counts, Attach a listener to read the data at our posts reference
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.child("users").getChildren();
                for (DataSnapshot next : snapshotIterator) {
                    String currentUserName = getIntent().getExtras().getString("currentUserName");
                    if (currentUserName.equals(next.child("userName").getValue())) {

                        Map<String, Long> currentCount = (Map<String, Long>) next.child("stickerCountMap").getValue();
                        if (currentCount != null)
                            currentCount.put(chosenImageId + "", currentCount.getOrDefault(chosenImageId + "", 0L) + 1L);
                        databaseReference.child("users").child(Objects.requireNonNull(next.getKey())).child("stickerCountMap").setValue(currentCount).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MessageActivity.this, "Unable to send sticker. Please try again later", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
//        set image view background to transparent.
        imageView1.setBackgroundColor(0);
        imageView2.setBackgroundColor(0);
        imageView3.setBackgroundColor(0);
        imageView4.setBackgroundColor(0);
        imageView5.setBackgroundColor(0);
        imageView6.setBackgroundColor(0);
//        toast to show that the sticker has been sent.
        Toast.makeText(MessageActivity.this, "Sticker sent", Toast.LENGTH_SHORT).show();
    }

    /**
     * This function sendNotification should create a notification channel and sets
     * the notification image based on the image ID passed to it.
     *
     * @param image  the image ID of the sticker.
     * @param sender the name of the user who sent the sticker.
     */
    @SuppressLint("MissingPermission")
    private void sendNotification(int image, String sender) {
        CharSequence name = "Sticker_notify";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel =
                new NotificationChannel(channelId, name, importance);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        Bitmap myBitmap;

        switch(image) {
            case 2131165271:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_1);
                break;
            case 2131165308:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_2);
                break;
            case 2131165309:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_3);
                break;
            case 2131165325:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_4);
                break;
            case 2131165368:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_5);
                break;
            case 2131165369:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_6);
                break;
            default:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_7);
                break;
        }
    }

    /**
     * This function should display a chat send notification to the user when a new chat message is received.
     *
     * It takes a DataSnapshot object as a parameter, which represents the current state of the data
     * at the specific location in the Firebase Realtime Database.
     *
     * The function first should extracts the sender's name from the snapshot,
     * and then create a new ChatMessage object using information from the snapshot.
     *
     * It then should check if the chatMessageList already contains the newly created ChatMessage.
     * If not, it should add the ChatMessage to the list and updates the messageRecyclerView accordingly.
     *
     * Next, it should extract the imageID, sender, and receiver from the ChatMessage,
     * as well as the unique key of the chat message from the snapshot.
     *
     * It also should extract the current user's name and the readStatus of
     * the chat message from the snapshot.
     *
     * If the receiver of the chat message is the current user and the readStatus is unread,
     * the function should call the sendNotification function to display a chat send notification with
     * the appropriate image and sender.
     *
     * It then updates the readStatus of the chat message to read in the Firebase Realtime Database.
     *
     * @param snapshot the snapshot of the database.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void displayChatSendNotif(DataSnapshot snapshot) {
//         Extract sender's name from the snapshot
        String sender = snapshot.child("sender").getValue(String.class);
        if (sender != null) {
            ChatMessage chatMessage = new ChatMessage((Long) snapshot.child("imageID").getValue(),
                    String.valueOf(snapshot.child("timestamp").getValue()),
                    String.valueOf(snapshot.child("sender").getValue()),
                    String.valueOf(snapshot.child("receiver").getValue()));
            if(!chatMessageList.contains(chatMessage)) {
                chatMessageList.add(chatMessage);

                if(messageRecyclerView != null && messageRecyclerView.getAdapter() != null)
                    messageRecyclerView.getAdapter().notifyItemInserted(messageRecyclerView.getAdapter().getItemCount());

                String chatSender = chatMessage.getSender();
                int image = (int) chatMessage.getImageID();
                String receive = chatMessage.getReceiver();
                String key = snapshot.getKey();


                String current = bundle.getString("currentUserName");
                String currentStatus = snapshot.child("readStatus").getValue(String.class);

                if (receive.equalsIgnoreCase(current)) {
                    assert currentStatus != null;
                    if (currentStatus.equalsIgnoreCase("unread")) {
                        sendNotification(image, chatSender);
                        assert key != null;
                        messages.child(key).child("readStatus").setValue("read");
                    }
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("chatMessageList", chatMessageList);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        state.putParcelableArrayList("chatMessageList", chatMessageList);
    }
}
