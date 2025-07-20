# Order-Inventory System using Akka Typed

This project demonstrates the use of Akka Typed API with Java to implement an actor system that showcases the three main communication patterns: **tell()**, **ask()**, and **forward()**.

## Project Structure

The project consists of two main actors:

### OrderActor
- Handles customer orders
- Uses **tell()** to send order preparation requests to InventoryActor
- Uses **ask()** to query InventoryActor for order status
- Uses **forward()** to forward orders to the cancellation system

### InventoryActor
- Manages inventory and order processing
- Receives and processes messages from OrderActor
- Responds to ask() requests with order status
- Handles order forwarding to cancellation system

## Communication Patterns Demonstrated

1. **TELL Pattern**: Fire-and-forget message passing
   - OrderActor sends `PrepareOrder` to InventoryActor
   - No response expected

2. **ASK Pattern**: Request-response message passing
   - OrderActor asks InventoryActor for order status
   - InventoryActor responds with `InventoryResponse`

3. **FORWARD Pattern**: Message forwarding
   - OrderActor forwards order to cancellation system via InventoryActor
   - InventoryActor acts as an intermediary

## How to Run

1. Make sure you have Java 17+ and Maven installed
2. Navigate to the project directory
3. Run the application:

```bash
mvn compile exec:java
```

## Expected Output

The application will demonstrate all three communication patterns with clear logging:

```
Starting Application for Order-Inventory System...
=== TELL method ===
[OrderActor] Sending PrepareOrder via TELL
[InventoryActor] Received PrepareOrder via TELL - Product: iPhone 16, Quantity: 2, Customer: Bill Huggins
[InventoryActor] Order placed successfully - Stock available

=== ASK method ===
[OrderActor] Sending GetStatus via ASK
[InventoryActor] Received GetOrderStatus via ASK for customer: Bill Huggins
[OrderActor] Got response: iPhone 16 order is being processed for Bill Huggins

=== FORWARD method ===
[OrderActor] Sending ForwardRequest to Inventory
[InventoryActor] Received ForwardOrder via FORWARD - Order: Order{productName='Nike AirMax', quantity=1, customerName='Brian Kelly'}
[InventoryActor] Forwarding order to Cancellation System: Order{productName='Nike AirMax', quantity=1, customerName='Brian Kelly'}
[InventoryActor] Order forwarded successfully for Cancellation

```

## Key Features

- **Type Safety**: Uses Akka Typed API for compile-time type safety
- **Message Passing**: Demonstrates three fundamental actor communication patterns
- **Asynchronous**: All communication is asynchronous and non-blocking
- **Error Handling**: Includes basic error handling for ask() pattern
- **Clean Architecture**: Separation of concerns between order and inventory management 