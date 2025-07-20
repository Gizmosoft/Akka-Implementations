package edu.northeastern.models;

public class ForwardOrder implements InventoryCommand {
    public final Order order;

    public ForwardOrder(Order order) {
        this.order = order;
    }
}
