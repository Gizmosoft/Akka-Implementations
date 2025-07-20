package edu.northeastern.models;

public class DateResponse {
    private final String date;

    public DateResponse(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "DateResponse{date='" + date + "'}";
    }
} 