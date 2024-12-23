package com.mdd.back.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class MissingParameterException extends RuntimeException {
    public MissingParameterException(String message) {
        super(message);
    }
}