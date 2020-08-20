package org.se2.hausarbeit.process.exception;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
