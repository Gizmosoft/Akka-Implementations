package edu.northeastern.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateRequestTest {

    @Test
    void toStringShouldNotBeNull() {
        DateRequest cmd = new DateRequest();
        String text = cmd.toString();
        assertNotNull(text, "toString() should never return null");
    }

    @Test
    void toStringShouldReturnExactClassName() {
        DateRequest cmd = new DateRequest();
        String text = cmd.toString();
        assertEquals("DateRequest", text,
                "toString() should return the simple class name");
    }
}
