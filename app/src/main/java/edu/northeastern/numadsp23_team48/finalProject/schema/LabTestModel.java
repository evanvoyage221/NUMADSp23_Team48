package edu.northeastern.numadsp23_team48.finalProject.schema;

public class LabTestModel {
    private String name;
    private int price;

    public LabTestModel() {
    }

    public LabTestModel(String name, int price) {

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
