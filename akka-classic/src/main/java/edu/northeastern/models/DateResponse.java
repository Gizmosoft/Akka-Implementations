package edu.northeastern.models;

import java.time.LocalDate;

public class DateResponse {
    private final String date;

    public DateResponse() {
        this.date = LocalDate.now().toString();
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Today's date is " + getDate();
    }
} 