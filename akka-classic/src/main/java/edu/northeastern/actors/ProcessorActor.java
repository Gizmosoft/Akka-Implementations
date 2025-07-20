package edu.northeastern.actors;

import akka.actor.AbstractActor;
import edu.northeastern.models.DateRequest;
import edu.northeastern.models.DateResponse;
import edu.northeastern.models.ForwardEmail;
import edu.northeastern.models.ReminderMessage;

import java.time.LocalDate;

public class ProcessorActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ReminderMessage.class, msg -> {
                    System.out.println("[PROCESSOR] Reminder received via TELL: " + msg.getMessage());
                })
                .match(DateRequest.class, msg -> {
                    System.out.println("[PROCESSOR] Date requested via ASK");
                    getSender().tell(new DateResponse("Today's date is: " + LocalDate.now()), getSelf());
                })
                .match(ForwardEmail.class, msg -> {
                    System.out.println("[PROCESSOR] Forwarding Email to: " + msg.getRecipient() + " from " + getSender());
                })
                .matchAny(msg -> {
                    System.out.println("[PROCESSOR] Unknown message: " + msg);
                })
                .build();
    }
}
