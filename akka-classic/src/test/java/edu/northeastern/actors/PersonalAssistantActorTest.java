package edu.northeastern.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import akka.testkit.TestProbe;
import edu.northeastern.models.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalAssistantActorTest {
    private static ActorSystem system;

    @BeforeAll
    static void setup() {
        system = ActorSystem.create("PersonalAssistantActorTestSystem");
    }

    @AfterAll
    static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    void tellReminderCommand_forwardsReminderMessage() {
        new TestKit(system) {
            {
                TestProbe processorProbe = new TestProbe(system);
                ActorRef processor = system.actorOf(Props.create(ProcessorActor.class));
                ActorRef assistant = system.actorOf(Props.create(PersonalAssistantActor.class, processor));

                java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
                java.io.PrintStream originalOut = System.out;
                System.setOut(new java.io.PrintStream(outContent));

                try {
                    assistant.tell(new TellReminderCommand(), getRef());
                    Thread.sleep(100); // allow for async print
                    String output = outContent.toString();
                    assertTrue(output.contains("[PROCESSOR] Reminder received via TELL: Go for a run!"));
                } catch (InterruptedException e) {
                    fail("Interrupted");
                } finally {
                    System.setOut(originalOut);
                }
            }
        };
    }

    @Test
    void askDateCommand_sendsDateRequest() throws InterruptedException {
        new TestKit(system) {
            {
                // TestProbe processorProbe = new TestProbe(system);
                // ActorRef assistant =
                // system.actorOf(Props.create(PersonalAssistantActor.class,
                // processorProbe.ref()));

                // // send the ask-date command
                // assistant.tell(new AskDateCommand(), getRef());

                // // verify the probe sees exactly one DateRequest
                // processorProbe.expectMsgClass(DateRequest.class);
                TestProbe processorProbe = new TestProbe(system);
                ActorRef assistant = system.actorOf(Props.create(PersonalAssistantActor.class, processorProbe.ref()));

                // Capture System.out
                java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
                java.io.PrintStream originalOut = System.out;
                System.setOut(new java.io.PrintStream(outContent));

                try {
                    // Send the ask-date command
                    assistant.tell(new AskDateCommand(), getRef());

                    // The processor should receive a DateRequest
                    processorProbe.expectMsgClass(DateRequest.class);

                    // Simulate the processor replying with a DateResponse
                    DateResponse response = new DateResponse();
                    processorProbe.reply(response);

                    // Wait for the async callback to execute
                    Thread.sleep(200); // adjust if needed

                    // Check the output
                    String output = outContent.toString();
                    assertTrue(
                            output.contains(
                                    "[PERSONAL ASSISTANT] Received response: Today's date is " + response.getDate()),
                            "Expected output to contain the response print statement");
                } finally {
                    // Restore System.out
                    System.setOut(originalOut);
                }
            }
        };
    }

    @Test
    void forwardCommand_preservesSenderAndEmail() {
        new TestKit(system) {
            {
                TestProbe processorProbe = new TestProbe(system);
                ActorRef assistant = system.actorOf(Props.create(PersonalAssistantActor.class, processorProbe.ref()));

                // send the forward command, with getRef() as the original sender
                assistant.tell(new ForwardCommand(), getRef());

                // the probe should receive a ForwardEmail with the correct recipient
                ForwardEmail email = processorProbe.expectMsgClass(ForwardEmail.class);
                assertEquals("team@office.com", email.getRecipient(),
                        "The assistant should forward to the hard-coded email address");

                // and the sender of that forwarded message should be our test actor
                ActorRef lastSender = processorProbe.lastSender();
                assertEquals(getRef(), lastSender,
                        "Forward must preserve the original sender when forwarding");
            }
        };
    }
}
