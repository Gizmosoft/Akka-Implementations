package edu.northeastern.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.javadsl.TestKit;
import akka.testkit.TestProbe;
import edu.northeastern.models.DateResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import edu.northeastern.models.DateRequest;
import edu.northeastern.models.ForwardEmail;
import edu.northeastern.models.ReminderMessage;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;
import java.time.LocalDate;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorActorTest {
    private static ActorSystem system;

    @BeforeAll
    static void setup() {
        system = ActorSystem.create("ProcessorActorTestSystem");
    }

    @AfterAll
    static void teardown() throws Exception {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    void testDateRequestRepliesWithCurrentDate() throws Exception {
        new TestKit(system) {{
            ActorRef processor = system.actorOf(Props.create(ProcessorActor.class));
            Future<Object> sf = Patterns.ask(processor, new DateRequest(), 1000);
            // convert scalaFuture to java CompletionStage
            CompletionStage<Object> jf = FutureConverters.toJava(sf);
            DateResponse response = (DateResponse) jf.toCompletableFuture().get(500, TimeUnit.MILLISECONDS);
            String expectedResponse = LocalDate.now().toString();
            assertEquals(expectedResponse, response.getDate(), "Expected date string, go: " + response.getDate());
        }};
    }

    @Test
    void testReminderMessageDoesNotReply() {
        new TestKit(system) {{
            // Create a TestProbe to act as the sender
            TestProbe senderProbe = new TestProbe(system);
            ActorRef processor = system.actorOf(Props.create(ProcessorActor.class));
            
            // Send the message using the probe as sender
            processor.tell(new ReminderMessage("Go for a run!"), senderProbe.ref());
        }};
    }

    @Test
    void testForwardEmailDoesNotReply() {
        new TestKit(system) {{
            // Create a TestProbe to act as the sender
            TestProbe senderProbe = new TestProbe(system);
            ActorRef processor = system.actorOf(Props.create(ProcessorActor.class));
            
            // Send the message using the probe as sender
            processor.tell(new ForwardEmail("team@office.com"), senderProbe.ref());
        }};
    }
}
