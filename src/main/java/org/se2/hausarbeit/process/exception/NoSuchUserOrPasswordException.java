package org.se2.hausarbeit.process.exception;

public class NoSuchUserOrPasswordException extends Exception {
    public NoSuchUserOrPasswordException(String msg) {
        super(msg);
    }
}
