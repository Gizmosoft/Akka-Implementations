package edu.northeastern.models;

public class SubmitOrder implements OrderCommand {
    public final String productName;
    public final int quantity;
    public final String customerName;

    public SubmitOrder(String productName, int quantity, String customerName) {
        this.productName = productName;
        this.quantity = quantity;
        this.customerName = customerName;
    }
}
