package edu.northeastern.models;

public final class ForwardEmail {
    private final String recipient;

    public ForwardEmail(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    @Override
    public String toString() {
        return "ForwardEmail{" +
                "recipient='" + recipient + '\'' +
                '}';
    }
}
