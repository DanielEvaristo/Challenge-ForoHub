package com.daniel.ForoHub.infra.errores;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validacion(MethodArgumentNotValidException ex) {
        var errores = ex.getBindingResult().getFieldErrors()
                .stream().map(e -> Map.of("campo", e.getField(), "error", e.getDefaultMessage())).toList();
        return ResponseEntity.badRequest().body(Map.of("errores", errores));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> negocio(IllegalArgumentException ex) {
        String msg = ex.getMessage();
        HttpStatus st = "No encontrado".equals(msg) ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(st).body(Map.of("mensaje", msg));
    }
}

