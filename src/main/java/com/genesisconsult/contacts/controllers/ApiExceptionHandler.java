package com.genesisconsult.contacts.controllers;

import com.genesisconsult.contacts.errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
/**
 * Exception Interceptor Controller
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    /** triggered when a contact is not found*/
    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleApiExceptions(ContactNotFoundException ex, WebRequest request){
        ErrorDetails details = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    /** triggered when a entreprise is not found*/
    @ExceptionHandler(EntrepriseNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleApiExceptions(EntrepriseNotFoundException ex, WebRequest request){
        ErrorDetails details = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    /** triggered when the type is not correct*/
    @ExceptionHandler(ContactTypeException.class)
    public ResponseEntity<ErrorDetails> handleApiExceptions(ContactTypeException ex, WebRequest request){
        ErrorDetails details = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    /** triggered when a field is empty*/
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ErrorDetails> handleApiExceptions(EmptyFieldException ex, WebRequest request){
        ErrorDetails details = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }
    /** triggered when a numTVA is repeated*/
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleApiExceptions(org.springframework.dao.DataIntegrityViolationException ex, WebRequest request){
        ErrorDetails details = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }
}
