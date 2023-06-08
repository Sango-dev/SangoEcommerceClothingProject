package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ua.khpi.diploma.sangoecommerceclothingproject.exception.CustomException;

@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(CustomException.class)
    public String handleException(CustomException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(Model model) {
        model.addAttribute("message", "Сторінку не знайдено!");
        return "exception";
    }
}
