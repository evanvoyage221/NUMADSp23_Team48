package edu.northeastern.numadsp23_team48.model;

public class SentItem {
    private final String imageID;
    private final String count;

    public SentItem(String imageID, String count) {
        this.imageID = imageID;
        this.count = count;
    }

    public String getImageID() {
        return imageID;
    }

    public String getCount() {
        return count;
    }
}
