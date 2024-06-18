package ru.chernyshev.restful.exception;

public class InaccessibleEntityException extends RuntimeException {

    public InaccessibleEntityException(String message) {
        super(message);
    }

}
