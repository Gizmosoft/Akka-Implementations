package edu.northeastern.models;

import akka.actor.typed.ActorRef;

public class GetOrderStatus implements InventoryCommand {
    public String customerName;
    public final ActorRef<InventoryResponse> replyTo;

    public GetOrderStatus(ActorRef<InventoryResponse> replyTo, String customerName) {
        this.replyTo = replyTo;
        this.customerName = customerName;
    }
}
