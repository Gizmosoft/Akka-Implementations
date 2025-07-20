package edu.northeastern.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReminderMessageTest {

    @Test
    void getMessageReturnsConstructorValue() {
        String message = "Finish report";
        ReminderMessage msg = new ReminderMessage(message);
        String result = msg.getMessage();

        assertEquals(message, result, "getMessage() should return the value passed into the constructor");
    }

    @Test
    void toStringContainsClassNameAndMessage() {
        String message = "Run 5km";
        ReminderMessage msg = new ReminderMessage(message);
        String text = msg.toString();

        String expected = "ReminderMessage{message='" + message + "'}";
        assertEquals(expected, text, "toString() should be formatted as " + expected);

        assertTrue(text.contains(message), "toString() should include the task string");
    }
}
