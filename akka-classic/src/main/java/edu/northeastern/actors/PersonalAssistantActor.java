package edu.northeastern.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import edu.northeastern.models.*;

public class PersonalAssistantActor extends AbstractActor {
    private final ActorRef processor;

    public PersonalAssistantActor(ActorRef processor) {
        this.processor = processor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TellReminderCommand.class, msg -> {
                    System.out.println("[PERSONAL ASSISTANT] sending TELL request...");
                    processor.tell(new ReminderMessage("Go for a run!"), getSelf());
                })
                .match(AskDateCommand.class, msg -> {
                    System.out.println("[PERSONAL ASSISTANT] sending ASK request...");
                    processor.tell(new DateRequest(), getSelf());
                })
                .match(ForwardCommand.class, msg -> {
                    System.out.println("[PERSONAL ASSISTANT] sending FORWARD request...");
                    processor.forward(new ForwardEmail("team@office.com"), getContext());
                })
                .match(DateResponse.class, response -> {
                    System.out.println("[PERSONAL ASSISTANT] Received response: " + response.getDate());
                })
                .build();
    }
}
