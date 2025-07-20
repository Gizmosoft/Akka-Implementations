package edu.northeastern.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForwardCommandTest {

    @Test
    void toStringShouldNotBeNull() {
        ForwardCommand cmd = new ForwardCommand();
        String text = cmd.toString();
        assertNotNull(text, "toString() should never return null");
    }

    @Test
    void toStringShouldReturnExactClassName() {
        ForwardCommand cmd = new ForwardCommand();
        String text = cmd.toString();
        assertEquals("ForwardCommand", text,
                "toString() should return the simple class name");
    }
}
