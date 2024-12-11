package com.mdd.back.controllers;

import com.mdd.back.exceptions.MissingParameterException;
import com.mdd.back.security.dto.ErrorDto;
import org.springframework.dao.DataIntegrityViolationException;
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

@ControllerAdvice
public class AdviceController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({MissingParameterException.class})
    public @ResponseBody ErrorDto handleException(MissingParameterException ex) {
        return new ErrorDto(BAD_REQUEST.toString(), ex.getMessage());
    }

    //FIXME A voir si je garde car ça expose les règles de la BDD au client,
    // qui pourrait connaitre des email déjà présent en BDD par exemple
    // Mettre une erreur générique SI erreur dans register ou login (Invalid Credentials)
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public @ResponseBody ErrorDto handleDatabaseException(DataIntegrityViolationException ex) {
        return new ErrorDto(BAD_REQUEST.toString(), ex.getMessage());
    }

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

}
