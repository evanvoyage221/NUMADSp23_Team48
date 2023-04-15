package edu.northeastern.numadsp23_team48.finalProject.schema;

public class OrderModel {
    private String totalPrice;
    private String ItemName;
    private String OrderDate;

    public OrderModel(String price, String itemType, String orderDate) {
        this.totalPrice = price;
        ItemName = itemType;
        OrderDate = orderDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }
}
