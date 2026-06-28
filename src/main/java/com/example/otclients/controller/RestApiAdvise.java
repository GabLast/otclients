package com.example.otclients.controller;

import com.example.otclients.dto.response.GenericResponse;
import com.example.otclients.dto.response.ResponseInfo;
import com.example.otclients.exceptions.InvalidActionException;
import com.example.otclients.exceptions.InvalidDataFormat;
import com.example.otclients.exceptions.ResourceExistsException;
import com.example.otclients.exceptions.ResourceNotFoundException;
import com.example.otclients.exceptions.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class RestApiAdvise {

    @ExceptionHandler(
            exception = ResourceNotFoundException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected GenericResponse badRequest(ResourceNotFoundException ex,
                                         WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = ResourceExistsException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    protected GenericResponse badRequest(ResourceExistsException ex, WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = InvalidDataFormat.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected GenericResponse badRequest(InvalidDataFormat ex, WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = RuntimeException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    protected GenericResponse badRequest(RuntimeException ex, WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message("Unknown Error: " + ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = NoSuchElementException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected GenericResponse badRequest(NoSuchElementException ex, WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = DateTimeParseException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected GenericResponse badRequest(DateTimeParseException ex, WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = NoSuchAlgorithmException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    protected GenericResponse badRequest(NoSuchAlgorithmException ex,
            WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message("generateBase64String Algorithm Error: " + ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = ServerException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    protected GenericResponse badRequest(ServerException ex, WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.SERVICE_UNAVAILABLE.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = IllegalArgumentException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    protected GenericResponse badRequest(IllegalArgumentException ex, WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_ACCEPTABLE.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = InvalidActionException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected GenericResponse badRequest(InvalidActionException ex, WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value()).path(request.getDescription(false))
                        .build())
                .build();
    }

    @ExceptionHandler(
            exception = NullPointerException.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected GenericResponse badRequest(NullPointerException ex, WebRequest request) {
        return GenericResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value()).path(request.getDescription(false))
                        .build())
                .build();
    }
}
