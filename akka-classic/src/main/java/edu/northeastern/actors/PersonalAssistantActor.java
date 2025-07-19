package edu.northeastern.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.pattern.Patterns;
import scala.compat.java8.FutureConverters;
import scala.compat.java8.FutureConverters.*;

import java.util.concurrent.CompletionStage;


public class PersonalAssistantActor extends AbstractActor {
    private final ActorRef processor;

    public PersonalAssistantActor(ActorRef processor) {
        this.processor = processor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()

                .matchEquals("tellCommand", msg -> {
                    System.out.println("[PERSONAL ASSISTANT] sending TELL request...");
                    processor.tell("Remind me to go for a run", getSelf());
                })
                .matchEquals("askCommand", msg -> {
                    System.out.println("[PERSONAL ASSISTANT] sending ASK request...");
                    scala.concurrent.Future<Object> scalaFuture = Patterns.ask(processor, "What's the date today?", 5000);
                    CompletionStage<Object> future = FutureConverters.toJava(scalaFuture);
                    future.thenAccept(result -> {
                        System.out.println("[PERSONAL ASSISTANT] Received response via ASK: " + result);
                    });
                })
                .matchEquals("forwardCommand", msg -> {
                    System.out.println("[PERSONAL ASSISTANT] sending FORWARD request...");
                    processor.forward("Forward Email to Office", getContext());
                })
                .build();
    }
}
