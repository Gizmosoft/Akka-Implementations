package edu.northeastern.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import edu.northeastern.models.*;

import java.util.HashMap;
import java.util.Map;

public class InventoryActor extends AbstractBehavior<InventoryCommand> {
    private Map<String, String> ordersLog = new HashMap<>();

    // create instance of InventoryActor
    public static Behavior<InventoryCommand> create() {
        return Behaviors.setup(InventoryActor::new);
    }

    // private constructor
    private InventoryActor(ActorContext<InventoryCommand> context) {
        super(context);
    }

    @Override
    public Receive<InventoryCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(PrepareOrder.class, this::onPrepareOrder)
                .onMessage(GetOrderStatus.class, this::onGetOrderStatus)
                .onMessage(ForwardOrder.class, this::onForwardOrder)
                .build();
    }

    /**
     * TELL - Handle order preparation request
     */
    private Behavior<InventoryCommand> onPrepareOrder(PrepareOrder command) {
        System.out.println("[InventoryActor] Received PrepareOrder via TELL - Product: " + command.productName + 
                ", Quantity: " + command.quantity + ", Customer: " + command.customerName);
        
        // Simulate inventory check
        if (command.quantity <= 10) {
            System.out.println("[InventoryActor] Order placed successfully - Stock available");
            ordersLog.put(command.customerName, command.productName);
        } else {
            System.out.println("[InventoryActor] Order failed - Insufficient stock");
        }
        
        return this;
    }

    /**
     * ASK - Handle order status query and send response
     */
    private Behavior<InventoryCommand> onGetOrderStatus(GetOrderStatus command) {
        System.out.println("[InventoryActor] Received GetOrderStatus via ASK for customer: " + command.customerName);
        
        // Simulate order status lookup
        String product = ordersLog.get(command.customerName);
        String status = "";
        if (product != null) {
            status = product + " order is being processed for " + command.customerName;
        } else {
            status = "No order found for " + command.customerName;
        }

        InventoryResponse response = new InventoryResponse(status);
        
        // Send response back to the asking actor
        command.replyTo.tell(response);
//        System.out.println("[InventoryActor] Sent response back to asking actor");
        return this;
    }

    /**
     * FORWARD - Handle order forwarding to cancellation system
     */
    private Behavior<InventoryCommand> onForwardOrder(ForwardOrder command) {
        System.out.println("[InventoryActor] Received ForwardOrder via FORWARD - Order: " + command.order);
        
        // Simulate forwarding to cancellation system
        System.out.println("[InventoryActor] Forwarding order to Cancellation System: " + command.order);
        System.out.println("[InventoryActor] Order forwarded successfully for Cancellation");
        
        return this;
    }
}
