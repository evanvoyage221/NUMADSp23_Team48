package edu.northeastern.numadsp23_team48.model;

public class ReceivedSticker {
    private String message;
    private String sticker;
    public String date;

    public ReceivedSticker() {
    }

    public ReceivedSticker(String sticker, String date) {
        this.sticker = sticker;
        this.date = date;
    }

    public ReceivedSticker(String message, String sticker, String date) {
        this.message = message;
        this.sticker = sticker;
        this.date = date;
    }
}
