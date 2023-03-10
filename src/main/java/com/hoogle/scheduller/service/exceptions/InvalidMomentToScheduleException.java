package com.hoogle.scheduller.service.exceptions;

public class InvalidMomentToScheduleException extends Exception {

    public InvalidMomentToScheduleException() {
        super("Cannot schedule you appointment :(");
    }

    public InvalidMomentToScheduleException(String message) {
        super(message);
    }
}
