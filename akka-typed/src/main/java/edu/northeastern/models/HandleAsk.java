package edu.northeastern.models;

public class HandleAsk implements OrderCommand {
    public String customerName;

    public HandleAsk(String customerName) {
        this.customerName = customerName;
    }
}
