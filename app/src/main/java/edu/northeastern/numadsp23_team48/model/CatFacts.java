package edu.northeastern.numadsp23_team48.model;

import java.util.ArrayList;

public class CatFacts {
    ArrayList<String> data;

    public CatFacts(ArrayList<String> data) {
        this.data = data;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
