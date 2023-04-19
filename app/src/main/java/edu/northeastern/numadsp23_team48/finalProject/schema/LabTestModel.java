package edu.northeastern.numadsp23_team48.finalProject.schema;

public class LabTestModel {
    private String name;
    private int price;
    private String details;

    public LabTestModel() {
    }

    public LabTestModel(String name, int price, String details) {

        this.name = name;
        this.price = price;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    public String getDetails() {
        return details;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
