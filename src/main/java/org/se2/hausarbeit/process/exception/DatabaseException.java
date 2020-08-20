package org.se2.hausarbeit.process.exception;

public class DatabaseException extends Exception {
    private String reason = "";
    public DatabaseException(String reason) {
        super(reason);
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
    }
}

