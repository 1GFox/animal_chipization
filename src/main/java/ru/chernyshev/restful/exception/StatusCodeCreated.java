package ru.chernyshev.restful.exception;

public class StatusCodeCreated extends RuntimeException {
    public StatusCodeCreated(String message) {
        super(message);
    }
}
