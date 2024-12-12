package com.mdd.back.controllers;

import com.mdd.back.exceptions.MissingParameterException;
import com.mdd.back.security.dto.ErrorDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class AdviceController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    public @ResponseBody void handleException(Exception ex) {}

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({MissingParameterException.class})
    public @ResponseBody ErrorDto handleException(MissingParameterException ex) {
        return new ErrorDto(BAD_REQUEST.toString(), ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public @ResponseBody void handleDatabaseException(DataIntegrityViolationException ex) {}

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public @ResponseBody Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", BAD_REQUEST.toString());
        response.put("message", "Erreur de validation");

        // Construire une liste des messages d'erreurs uniquement
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        response.put("errors", errors);

        return response;
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class})
    public @ResponseBody ErrorDto handleBadCredentialsException(BadCredentialsException ex) {
        return new ErrorDto(UNAUTHORIZED.toString(), ex.getMessage());
    }

}
