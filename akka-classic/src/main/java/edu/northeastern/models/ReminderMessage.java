package edu.northeastern.models;

public final class ReminderMessage {
    private final String message;

    public ReminderMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ReminderMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
