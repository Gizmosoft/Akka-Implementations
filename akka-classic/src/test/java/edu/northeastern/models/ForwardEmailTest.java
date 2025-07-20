package edu.northeastern.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForwardEmailTest {

    @Test
    void getRecipientReturnsConstructorValue() {
        String recipient = "user@example.com";
        ForwardEmail email = new ForwardEmail(recipient);
        String result = email.getRecipient();

        assertEquals(recipient, result, "getRecipient() should return the value passed into the constructor");
    }

    @Test
    void toStringIsFormattedCorrectly() {
        String recipient = "team@office.com";
        ForwardEmail email = new ForwardEmail(recipient);
        String text = email.toString();

        String expected = "ForwardEmail{recipient='" + recipient + "'}";
        assertEquals(expected, text, "toString() should match the exact format " + expected);

        assertTrue(text.contains(recipient), "toString() should include the recipient value");
    }
}
