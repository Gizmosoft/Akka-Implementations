package edu.northeastern.models;

public class Order {
    public final String productName;
    public final int quantity;
    public final String customerName;

    public Order(String productName, int quantity, String customerName) {
        this.productName = productName;
        this.quantity = quantity;
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
