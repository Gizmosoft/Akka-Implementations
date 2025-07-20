package edu.northeastern.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TellReminderCommandTest {

    @Test
    void toStringShouldNotBeNull() {
        TellReminderCommand cmd = new TellReminderCommand();
        String text = cmd.toString();
        assertNotNull(text, "toString() should never return null");
    }

    @Test
    void toStringShouldReturnExactClassName() {
        TellReminderCommand cmd = new TellReminderCommand();
        String text = cmd.toString();
        assertEquals("TellReminderCommand", text,
                "toString() should return the simple class name");
    }
}
