package edu.northeastern.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import edu.northeastern.models.*;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class OrderActor extends AbstractBehavior<OrderCommand> {
    // inject InventoryActor
    private final ActorRef<InventoryCommand> inventory;

    // create instance of OrderActor
    public static Behavior<OrderCommand> create(ActorRef<InventoryCommand> inventory) {
        return Behaviors.setup(context -> new OrderActor(context, inventory));
    }

    // private constructor to initialize the actor context
    private OrderActor(ActorContext<OrderCommand> context, ActorRef<InventoryCommand> inventory) {
        super(context);
        this.inventory = inventory;
    }

    // Handler Registry for different OrderCommand
    @Override
    public Receive<OrderCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(SubmitOrder.class, this::onSubmitOrder)
                .onMessage(HandleAsk.class, this::askOrderStatus)
                .onMessage(Response.class, this::onResponse)
                .onMessage(ForwardRequest.class, this::onForwardRequest)
                .build();
    }

    /**
     * TELL
     * Send message to InventoryActor
     */
    private Behavior<OrderCommand> onSubmitOrder(SubmitOrder command) {
        System.out.println("[OrderActor] Sending PrepareOrder via TELL");
        inventory.tell(new PrepareOrder(command.productName, command.quantity, command.customerName));
        return this;
    }

    /**
     * ASK
     * Send message to InventoryActor expecting a reply
     */
    private Behavior<OrderCommand> askOrderStatus(HandleAsk command) {
        System.out.println("[OrderActor] Sending GetStatus via ASK");

        CompletionStage<InventoryResponse> future = AskPattern.ask(
                inventory,
                replyTo -> new GetOrderStatus(replyTo, command.customerName),
                Duration.ofSeconds(4),
                getContext().getSystem().scheduler()
        );

        future.whenComplete((response, throwable) -> {
            if(response != null) {
                getContext().getSelf().tell(new Response(response));
            } else {
                System.out.println("[OrderActor] Failed to get order status");
            }
        });
        return this;
    }

    /**
     * RESPONSE HANDLER
     * Receive reply to ASK as a Response Object
     */
    private Behavior<OrderCommand> onResponse(Response responseObject) {
        System.out.println("[OrderActor] Got response: " + responseObject.response.status);
        return this;
    }

    /**
     * FORWARD
     * Send order to Inventory to Forward it to Cancellation Dept
     */
    private Behavior<OrderCommand> onForwardRequest(ForwardRequest command) {
        System.out.println("[OrderActor] Sending ForwardRequest to Inventory");
        inventory.tell(new ForwardOrder(new Order("Nike AirMax", 1, "Brian Kelly")));
        return this;
    }
}
