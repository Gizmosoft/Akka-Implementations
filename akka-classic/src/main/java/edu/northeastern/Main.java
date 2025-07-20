package edu.northeastern;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import edu.northeastern.actors.PersonalAssistantActor;
import edu.northeastern.actors.ProcessorActor;
import edu.northeastern.models.AskDateCommand;
import edu.northeastern.models.ForwardCommand;
import edu.northeastern.models.TellReminderCommand;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Initializing Actor System
        ActorSystem actorSystem = ActorSystem.create("ClassicActorSystem");

        // Initialize Actors in the system
        ActorRef processor = actorSystem.actorOf(akka.actor.Props.create(ProcessorActor.class), "processor");
        ActorRef assistant = actorSystem.actorOf(akka.actor.Props.create(PersonalAssistantActor.class, processor), "assistant");

        // Commands call
        tellCommand(assistant);
        Thread.sleep(2000);

        askCommand(assistant);
        Thread.sleep(2000);

        forwardCommand(assistant);
        Thread.sleep(2000);

        actorSystem.terminate();
    }

    private static void tellCommand(ActorRef assistant) {
        System.out.println("\n--- TELL DEMO ---");
        assistant.tell(new TellReminderCommand(), ActorRef.noSender());
    }

    private static void askCommand(ActorRef assistant) {
        System.out.println("\n--- ASK DEMO ---");
        assistant.tell(new AskDateCommand(), ActorRef.noSender());
    }

    private static void forwardCommand(ActorRef assistant) {
        System.out.println("\n--- FORWARD DEMO ---");
        assistant.tell(new ForwardCommand(), assistant);
    }
}