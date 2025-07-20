package edu.northeastern;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import edu.northeastern.actors.PersonalAssistantActor;
import edu.northeastern.actors.ProcessorActor;
import edu.northeastern.models.AskDateCommand;
import edu.northeastern.models.ForwardCommand;
import edu.northeastern.models.TellReminderCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
    This class has the integration tests for the Actor System to simulate the output achieved from running the program via the main method
 */
public class ActorsTest {
    @Test
    void integrationTest_matchesMainOutput() throws Exception {
        System.out.println("\n---------- Integration Tests --------------");
        ActorSystem system = ActorSystem.create("ClassicActorSystem");
        try {
            ActorRef processor = system.actorOf(Props.create(ProcessorActor.class), "processor");
            ActorRef assistant = system.actorOf(Props.create(PersonalAssistantActor.class, processor), "assistant");

            java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
            java.io.PrintStream originalOut = System.out;
            System.setOut(new java.io.PrintStream(outContent));

            try {
                // --- TELL DEMO ---
                System.out.println("--- TELL DEMO ---");
                assistant.tell(new TellReminderCommand(), ActorRef.noSender());
                Thread.sleep(100);

                // --- ASK DEMO ---
                System.out.println("\n--- ASK DEMO ---");
                assistant.tell(new AskDateCommand(), ActorRef.noSender());
                Thread.sleep(300);

                // --- FORWARD DEMO ---
                System.out.println("\n--- FORWARD DEMO ---");
                assistant.tell(new ForwardCommand(), assistant);
                Thread.sleep(100);

                // Wait for all async prints to complete
                Thread.sleep(200);

                String output = outContent.toString();

                // Optionally, print the output for visual inspection
                System.setOut(originalOut);
                System.out.println(output);

                // Optionally, assert output contains expected lines in order
                assertTrue(output.contains("[PERSONAL ASSISTANT] sending TELL request..."));
                assertTrue(output.contains("[PROCESSOR] Reminder received via TELL: Go for a run!"));
                assertTrue(output.contains("[PERSONAL ASSISTANT] sending ASK request..."));
                assertTrue(output.contains("[PROCESSOR] Date requested via ASK"));
                assertTrue(output.contains("[PERSONAL ASSISTANT] Received response: Today's date is "));
                assertTrue(output.contains("[PERSONAL ASSISTANT] sending FORWARD request..."));
                assertTrue(output.contains("[PROCESSOR] Forwarding Email to: team@office.com from Actor[akka://ClassicActorSystem/user/assistant"));
            } finally {
                System.setOut(originalOut);
            }
        } finally {
            TestKit.shutdownActorSystem(system);
        }
    }
}