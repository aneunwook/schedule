package org.example.schedule.common;

public class PasswordDoesNotMatch extends RuntimeException {
    public PasswordDoesNotMatch(String message) {
        super(message);
    }
}
