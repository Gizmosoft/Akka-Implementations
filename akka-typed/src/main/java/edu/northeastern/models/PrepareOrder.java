package edu.northeastern.models;

public class PrepareOrder implements InventoryCommand {
    public final String productName;
    public final int quantity;
    public final String customerName;

    public PrepareOrder(String productName, int quantity, String customerName) {
        this.productName = productName;
        this.quantity = quantity;
        this.customerName = customerName;
    }
}
