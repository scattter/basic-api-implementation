package com.thoughtworks.rslist.component;

import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.exception.InvlidIndexException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InvlidIndexException.class,
            MethodArgumentNotValidException.class,
            InvalidRequestParamException.class})
    public ResponseEntity exceptionHandler(Exception error) {
        String message;
        CommenError commenError = new CommenError();
        if (error instanceof MethodArgumentNotValidException) {
            message = "invalid param";
        } else {
            message = error.getMessage();
        }
        commenError.setError(message);
        System.out.println(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
