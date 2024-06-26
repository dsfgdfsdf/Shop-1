package com.barda_petrenco.shop_electronic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception exeption, Model model) {
        String errorMessage = (exeption != null) ? exeption.getMessage() : "Unknown Error";
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
