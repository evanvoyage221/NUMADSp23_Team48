package edu.northeastern.numadsp23_team48;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.numadsp23_team48.adapter.UserAdapter;
import edu.northeastern.numadsp23_team48.model.User;

public class AllUsersActivity extends AppCompatActivity {

    // creating a variable for Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for reference for Firebase.
    DatabaseReference databaseReference;
    DatabaseReference messages;

    private RecyclerView recyclerView;

    List<User> usersList;

    ConstraintLayout constraintLayout;
    String userKey;
    String currentUserName;
    String userName;

    private static final int NOTIFICATION_UNIQUE_ID = 13;
    private static final int notigen = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        userKey = getIntent().getExtras().getString("userKey");
        currentUserName = getIntent().getExtras().getString("currentUserName");

        //Instantiate the array list of websites or get from the bundle.
        if (savedInstanceState == null) {
            usersList = new ArrayList<>();
        } else {
            usersList = savedInstanceState.getParcelableArrayList("usersList");
        }

        // below line is used to get the
        // instance of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("");

        //Link to recycle view.
        recyclerView = findViewById(R.id.user_recycler_view);

        //Set the layout manager for the recycle view.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Set the custom adapter to the recycle view.
        recyclerView.setAdapter(new UserAdapter(usersList, this));

        //Decoration to add line after each item in the view.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);

        //Initialize a constraint layout to display the snack bar.
        constraintLayout = findViewById(R.id.constraintLayout);

        if (savedInstanceState == null) {
            // Attach a listener to read the data at our posts reference
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.child("users").getChildren();
                    for (DataSnapshot next : snapshotIterator) {
                        //add users other than current user.
                        if (!currentUserName.equals(next.child("userName").getValue())) {
//                            TODO: the path is the image id in firebase storage. Not set yet because I don't have access to firebase storage.
                            Map<String, Long> stickerMap = new HashMap<>();
                            stickerMap.put(next.child("stickerCountMap").child("1").getKey(), (Long) next.child("stickerCountMap").child("1").getValue());
                            stickerMap.put(next.child("stickerCountMap").child("2").getKey(), (Long) next.child("stickerCountMap").child("2").getValue());
                            stickerMap.put(next.child("stickerCountMap").child("3").getKey(), (Long) next.child("stickerCountMap").child("3").getValue());
                            stickerMap.put(next.child("stickerCountMap").child("4").getKey(), (Long) next.child("stickerCountMap").child("4").getValue());
                            stickerMap.put(next.child("stickerCountMap").child("5").getKey(), (Long) next.child("stickerCountMap").child("5").getValue());
                            stickerMap.put(next.child("stickerCountMap").child("6").getKey(), (Long) next.child("stickerCountMap").child("6").getValue());

                            User user = new User(Objects.requireNonNull(next.child("uid").getValue()).toString(), Objects.requireNonNull(next.child("userName").getValue()).toString(), currentUserName, stickerMap);
                            usersList.add(user);

                            userName = next.child("userName").getValue(String.class);
                            String chatId = "";

                            assert userName != null;
                            int compare = currentUserName.compareTo(userName);

                            if (compare < 0) {
                                chatId = currentUserName + userName;
                            } else if (compare > 0) {
                                chatId = userName + currentUserName;
                            }

                            messages = databaseReference.child("chats").child(chatId);
                            messages.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    sendNotif(snapshot);
                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    sendNotif(snapshot);
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

                        //Notify the adapter about the newly added item.
                        if (recyclerView != null && recyclerView.getAdapter() != null)
                            recyclerView.getAdapter().notifyItemInserted(recyclerView.getAdapter().getItemCount());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
    }

    private void sendNotif(DataSnapshot snapshot) {
        String receive = snapshot.child("receiver").getValue(String.class);
        String key = snapshot.getKey();
        String currentStatus = snapshot.child("readStatus").getValue(String.class);
        if (receive != null && receive.equalsIgnoreCase(currentUserName)) {
            assert currentStatus != null;
            if (currentStatus.equalsIgnoreCase("unread")) {
                String sender = snapshot.child("sender").getValue(String.class);
                int image_id = snapshot.child("imageID").getValue(int.class);
                sendNotification(image_id, sender);
                assert key != null;
                messages.child(key).child("readStatus").setValue("read");
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void sendNotification(int image_id, String sender) {
//        TODO: the image id is not set to the firebase yet. It's random for now.
        NotificationChannel channel =
                new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        Bitmap myBitmap;

        switch (image_id) {
            case 1:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_1);
                break;
            case 2:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_2);
                break;
            case 3:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_3);
                break;
            case 4:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_4);
                break;
            case 5:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_5);
                break;
            case 6:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_6);
                break;
            default:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_icon_7);
                break;
        }

        Intent resultingIntent = new Intent(this, MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("currentUserName", currentUserName);
        resultingIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                (int) System.currentTimeMillis(),
                resultingIntent,
                PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentTitle("MRP Sticker Notified!")
                .setSmallIcon(R.mipmap.ic_launcher_team48_round)
                .setContentText(sender + " Sent You:")
                .setLargeIcon(myBitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(myBitmap)
                        .bigLargeIcon(null))
                .setAutoCancel(true);
        //.setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_UNIQUE_ID + notigen, builder.build());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("usersList",
                (ArrayList<? extends Parcelable>) usersList);
    }

    public void openProfile(View view) {
        int theId = view.getId();
        if (theId == R.id.profile) {
            Intent intent = new Intent(AllUsersActivity.this, UserProfileActivity.class);
            intent.putExtra("userKey", userKey);
            intent.putExtra("username", currentUserName);
            startActivity(intent);
        }
    }
}
