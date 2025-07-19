package edu.northeastern.actors;

import akka.actor.AbstractActor;
import java.time.LocalDate;

public class ProcessorActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            // TELL
                .matchEquals("Remind me to go for a run", msg -> {
                    System.out.println("[PROCESSOR] Reminder received via TELL: " + msg);
                })
                .matchEquals("What's the date today?", msg -> {
                    System.out.println("[PROCESSOR] Date requested via ASK");
                    getSender().tell("Today's date is: " + LocalDate.now(), getSelf());
                })
                .matchEquals("Forward Email to Office", msg -> {
                    System.out.println("[PROCESSOR] '" + msg + "' request received from " + getSender() + " and has been completed");
                })
                .matchAny(msg -> {
                    System.out.println("[PROCESSOR] Unknown message: " + msg);
                })
                .build();
    }
}
