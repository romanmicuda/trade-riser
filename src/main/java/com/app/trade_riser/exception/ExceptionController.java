package com.app.trade_riser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException() {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalOperationException.class)
    public void handleIllegalOperationException() {}
}