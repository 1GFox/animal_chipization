package ru.chernyshev.restful.exception;

public class DataConflictException extends RuntimeException{

    public DataConflictException(String message) {
        super(message);
    }

}
