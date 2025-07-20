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
        new TestKit(system) {{
            // create a TestProbe to stand in for the ProcessorActor
            TestProbe processorProbe = new TestProbe(system);

            // create the PersonalAssistantActor, injecting the probe as its processor
            ActorRef assistant =
                    system.actorOf(Props.create(PersonalAssistantActor.class, processorProbe.ref()));

            // send a TellReminderCommand from this test actor (getRef())
            assistant.tell(new TellReminderCommand(), getRef());

            // verify the probe received exactly one ReminderMessage with the right task
            ReminderMessage msg = processorProbe.expectMsgClass(ReminderMessage.class);
            assertEquals("Go for a run!", msg.getMessage(),
                    "The assistant should wrap the hard-coded task in a ReminderMessage");
        }};
    }

    @Test
    void askDateCommand_sendsDateRequest() {
        new TestKit(system) {{
            TestProbe processorProbe = new TestProbe(system);
            ActorRef assistant =
                    system.actorOf(Props.create(PersonalAssistantActor.class, processorProbe.ref()));

            // send the ask-date command
            assistant.tell(new AskDateCommand(), getRef());

            // verify the probe sees exactly one DateRequest
            processorProbe.expectMsgClass(DateRequest.class);
        }};
    }

    @Test
    void forwardCommand_preservesSenderAndEmail() {
        new TestKit(system) {{
            TestProbe processorProbe = new TestProbe(system);
            ActorRef assistant =
                    system.actorOf(Props.create(PersonalAssistantActor.class, processorProbe.ref()));

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
        }};
    }
}
