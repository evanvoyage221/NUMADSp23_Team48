package edu.northeastern.numadsp23_team48.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Chat message object which represents a chat by the image ID, time stamp at which the message was sent and who sent it.
 */
public class ChatMessage implements Parcelable {

    private final long imageID;
    private final String timestamp;
    private final String sender;
    private final String receiver;
    private String readStatus;
    DateFormat simpleDateFormat;
    String sdf;


    /**
     * Implementing Parcelable for data persistence.
     */
    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    public ChatMessage(long imageID, String timestamp, String sender, String receiver) {
        this.imageID = imageID;
        this.timestamp = timestamp;
        this.sender = sender;
        this.receiver = receiver;
    }

    @SuppressLint("SimpleDateFormat")
    public ChatMessage(int imageID, String sender, String receiver, String readStatus) {
        this.imageID = imageID;
        long millis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(millis);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf = simpleDateFormat.format(timestamp);
        this.timestamp = sdf;
        this.sender = sender;
        this.receiver = receiver;
        this.readStatus = readStatus;
    }

    protected ChatMessage(Parcel in) {
        imageID = in.readLong();
        timestamp = in.readString();
        sender = in.readString();
        receiver = in.readString();
    }

    public long getImageID() {
        return imageID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getReadStatus() {
        return readStatus;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(imageID);
        parcel.writeString(timestamp);
        parcel.writeString(sender);
        parcel.writeString(receiver);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ChatMessage))
            return false;

        ChatMessage that = (ChatMessage) o;

        return getImageID() == that.getImageID()
                && getTimestamp().equals(that.getTimestamp())
                && getSender().equals(that.getSender())
                && getReceiver().equals(that.getReceiver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImageID(), getTimestamp(), getSender(), getReceiver());
    }
}
