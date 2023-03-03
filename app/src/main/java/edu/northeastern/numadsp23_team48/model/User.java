package edu.northeastern.numadsp23_team48.model;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import edu.northeastern.numadsp23_team48.R;

public class User {

    public String username;
    public int numberOfStickersSent;
    public HashMap<String, ArrayList<ReceivedSticker>> receivedStickers;


    public User() {
    }

    public User(String username) {
        this.username = username;
        this.numberOfStickersSent = 0;
        this.receivedStickers = new HashMap<>();

//        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
//        Calendar obj = Calendar.getInstance();
//        String dateStr = formatter.format(obj.getTime());

    }

    public String getUsername() {
        return username;
    }

    public int getNumberOfStickersSent() {
        return numberOfStickersSent;
    }

    public HashMap<String, ArrayList<ReceivedSticker>> getReceivedStickers() {
        return receivedStickers;
    }
}
