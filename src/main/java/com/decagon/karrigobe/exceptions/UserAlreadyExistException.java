package com.decagon.karrigobe.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class UserAlreadyExistException extends RuntimeException{

    private String errorMessage;
    private HttpStatus httpStatus;

    public UserAlreadyExistException(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }


}
