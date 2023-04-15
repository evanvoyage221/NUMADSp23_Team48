package edu.northeastern.numadsp23_team48.finalProject.schema;

public class MedicineModel {
    private String name;
    private String price;

    public MedicineModel() {
    }

    public MedicineModel(String name, String price) {

        this.name = name;
        this.price = price;
    }



    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
