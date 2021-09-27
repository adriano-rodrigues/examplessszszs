package com.monitoratec.tokenservice.vtswalletservice.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.error.ErrorObject;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.error.ErrorResponse;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.error.VisaErrorResponse;
import com.monitoratec.tokenservice.vtswalletservice.domain.payload.input.error.VisaError;
import com.monitoratec.tokenservice.vtswalletservice.exception.GenericSecurityException;
import com.monitoratec.tokenservice.vtswalletservice.exception.XPayTokenException;
import com.nimbusds.jose.JOSEException;
import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {
    private ObjectMapper MAPPER = new ObjectMapper();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException e) {
        e.printStackTrace();

        List<ErrorObject> errors = getErrors(e);
        ErrorResponse visaErrorResponse = getErrorResponse(e, errors);
        return new ResponseEntity<Object>(visaErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponse(MethodArgumentNotValidException ex, List<ErrorObject> errors) {
        return new ErrorResponse("Request have invalid fields", HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getBindingResult().getObjectName(), errors);
    }

    private List<ErrorObject> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorObject(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }


    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<Object> handleFeignException(FeignException e, WebRequest request) {
        e.printStackTrace();
        VisaError errorResponse = new VisaError();
        String responseBody = e.contentUTF8();

        if(responseBody.equals("")) {
            responseBody = "{\"errorMessage\" : \"" + e.getMessage() + "\"}";
            errorResponse.setVisaErrorResponse(VisaErrorResponse.builder()
                    .status(String.valueOf(e.status()))
                    .reason("Error while calling API using Feign.")
                    .message(e.getMessage())
                    .build());
        }else{
            try {
                errorResponse = MAPPER.readValue(responseBody, VisaError.class);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
                errorResponse.setVisaErrorResponse(VisaErrorResponse.builder()
                        .status(String.valueOf(e.status()))
                        .reason("Error while calling API using Feign and issues with deserialization of message.")
                        .message(e.getMessage())
                        .build());
            }
        }
        return new ResponseEntity<Object>(responseBody, HttpStatus.resolve(e.status()));
    }

    @ExceptionHandler(XPayTokenException.class)
    protected ResponseEntity<Object> handleXPayTokenException(XPayTokenException e, WebRequest request){
        e.printStackTrace();
        String responseBody = e.getMessage();

        if(responseBody.equals("")) {
            responseBody = "{\"errorMessage\" : \"" + "There was an error on Generating x-pay-token with detailed message: "+ e.getMessage() + "\"}";
        }

        return new ResponseEntity<Object>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException e, WebRequest request){
        e.printStackTrace();
        String responseBody = e.getMessage();

        if(responseBody.equals("")) {
            responseBody = "{\"errorMessage\" : \"" + "There was an error on using ObjectMapper with detailed message: "+ e.getMessage() + "\"}";
        }

        return  new ResponseEntity<Object>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleIoException(IOException e, WebRequest request){
        e.printStackTrace();
        String responseBody = e.getMessage();

        if(responseBody.equals("")) {
            responseBody = "{\"errorMessage\" : \"" + "There was an error on read or write files, with detailed message: "+ e.getMessage() + "\"}";
        }

        return  new ResponseEntity<Object>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GenericSecurityException.class)
    protected ResponseEntity<Object> handleGenericSecurityException(GenericSecurityException e, WebRequest request){
        e.printStackTrace();
        String responseBody = e.getMessage();

        if(responseBody.equals("")) {
            responseBody = "{\"errorMessage\" : \"" + "There was an error on data encryption with detailed message: "+ e.getMessage() + "\"}";
        }

        return  new ResponseEntity<Object>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JOSEException.class)
    protected ResponseEntity<Object> handleJOSEException(JOSEException e, WebRequest request){
        e.printStackTrace();
        String responseBody = e.getMessage();

        if(responseBody.equals("")) {
            responseBody = "{\"errorMessage\" : \"" + "There was an error on JOSE encryption lib with detailed message: "+ e.getMessage() + "\"}";
        }

        return  new ResponseEntity<Object>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    protected ResponseEntity<Object> handleNoSuchAlgorithmException(NoSuchAlgorithmException e, WebRequest request){
        e.printStackTrace();
        String responseBody = e.getMessage();

        if(responseBody.equals("")) {
            responseBody = "{\"errorMessage\" : \"" + "There was an error on encryption lib with detailed message: "+ e.getMessage() + "\"}";
        }

        return  new ResponseEntity<Object>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
