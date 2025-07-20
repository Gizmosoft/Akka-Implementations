package edu.northeastern;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import edu.northeastern.actors.OrderActor;
import edu.northeastern.actors.InventoryActor;
import edu.northeastern.models.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Application for Order-Inventory System...\n");
        
        ActorSystem<OrderCommand> system = ActorSystem.create(
                Behaviors.setup(context -> {
                    // Create InventoryActor
                    ActorRef<InventoryCommand> inventory = context.spawn(InventoryActor.create(), "InventoryActor");
                    // Create OrderActor with reference to InventoryActor
                    ActorRef<OrderCommand> order = context.spawn(OrderActor.create(inventory), "OrderActor");
                    
                    // Demonstrate TELL - Submit an order
                    System.out.println("=== TELL method ===");
                    order.tell(new SubmitOrder("iPhone 16", 2, "Bill Huggins"));
                    
                    // Wait before next demonstration
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    // Demonstrate ASK - Query order status
                    System.out.println("\n=== ASK method ===");
                    order.tell(new HandleAsk("Bill Huggins"));
                    
                    // Wait a bit before next demonstration
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    // Demonstrate FORWARD - Forward order to cancellation system
                    System.out.println("\n=== FORWARD method ===");
                    order.tell(new ForwardRequest());
                    
                    return Behaviors.empty();
                }), "OrderInventorySystem"
        );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        system.terminate();
    }
}