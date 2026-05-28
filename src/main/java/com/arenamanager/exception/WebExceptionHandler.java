package com.arenamanager.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = "com.arenamanager.controller.web")
public class WebExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, BusinessRuleException.class, TournamentFullException.class, RosterFullException.class})
    ModelAndView handleWebException(RuntimeException exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
