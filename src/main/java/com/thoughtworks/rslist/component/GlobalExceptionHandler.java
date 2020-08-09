package com.thoughtworks.rslist.component;

import com.thoughtworks.rslist.exception.CommenError;
<<<<<<< HEAD
=======
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
>>>>>>> jpa-2
import com.thoughtworks.rslist.exception.InvlidIndexException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
<<<<<<< HEAD
    @ExceptionHandler({InvlidIndexException.class, MethodArgumentNotValidException.class})
=======
    @ExceptionHandler({InvlidIndexException.class,
            MethodArgumentNotValidException.class,
            InvalidRequestParamException.class})
>>>>>>> jpa-2
    public ResponseEntity exceptionHandler(Exception error) {
        String message;
        CommenError commenError = new CommenError();
        if (error instanceof MethodArgumentNotValidException) {
            message = "invalid param";
        } else {
<<<<<<< HEAD
            message = "invalid index";
        }
        commenError.setError(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commenError);
=======
            message = error.getMessage();
        }
        commenError.setError(message);
        System.out.println(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
>>>>>>> jpa-2
    }
}
