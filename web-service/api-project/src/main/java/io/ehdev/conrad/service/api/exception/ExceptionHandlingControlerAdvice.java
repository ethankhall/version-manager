package io.ehdev.conrad.service.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlingControlerAdvice {


    @ExceptionHandler(BaseHttpException.class)
    ResponseEntity<Map> handleControllerException(BaseHttpException e) throws IOException {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", e.getHttpStatus().value());
        modelMap.addAttribute("timestamp", e.getTimestamp());
        modelMap.addAttribute("errorCode", e.getErrorCode());
        modelMap.addAttribute("message", e.getMessage());

        return new ResponseEntity<Map>(modelMap, e.getHttpStatus());
    }
}
