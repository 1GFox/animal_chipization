package ru.chernyshev.restful.api;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.chernyshev.restful.exception.DataConflictException;
import ru.chernyshev.restful.exception.InaccessibleEntityException;
import ru.chernyshev.restful.exception.InvalidDataException;
import ru.chernyshev.restful.exception.NotFoundException;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataConflictException.class})
    public ResponseEntity<String> handlerDataConflictException(DataConflictException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> handlerConstrainException(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InaccessibleEntityException.class})
    public ResponseEntity<String> handInaccessibleException(InaccessibleEntityException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({InvalidDataException.class})
    public ResponseEntity<String> handDataConflictException(InvalidDataException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({Exception.class})
//    public ResponseEntity ExceptionErrorMessage(Exception e) {
//        log.error(e.getMessage());
//        e.printStackTrace();
//        return new ResponseEntity<>(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
//    }

}
