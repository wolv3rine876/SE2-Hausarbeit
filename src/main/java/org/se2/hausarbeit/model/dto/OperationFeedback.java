package org.se2.hausarbeit.model.dto;

public class OperationFeedback {
    public final boolean success;
    public final String message;

    public OperationFeedback(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
