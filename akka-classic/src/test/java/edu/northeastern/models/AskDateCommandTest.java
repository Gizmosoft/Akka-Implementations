package edu.northeastern.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AskDateCommandTest {

    @Test
    void toStringShouldNotBeNull() {
        AskDateCommand cmd = new AskDateCommand();
        String text = cmd.toString();
        assertNotNull(text, "toString() should never return null");
    }

    @Test
    void toStringShouldReturnExactClassName() {
        AskDateCommand cmd = new AskDateCommand();
        String text = cmd.toString();
        assertEquals("AskDateCommand", text,
                "toString() should return the simple class name");
    }
}
