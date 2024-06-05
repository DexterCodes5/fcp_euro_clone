package dev.dex.fcpeuro.exc;

import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.validation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.*;

import java.time.*;
import java.util.*;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleException(BadRequestException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                errors.toString(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleException(NoResourceFoundException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleException(ResourceNotFoundException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    // This exception is thrown in JwtAuthentication filter, Spring doesn't handle filter exceptions in ControllerAdvice
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<?> handleException(ExpiredJwtException e, HttpServletRequest request) {
//        ApiError apiError = new ApiError(
//                request.getRequestURI(),
//                e.getMessage(),
//                HttpStatus.FORBIDDEN.value(),
//                LocalDateTime.now()
//        );
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
//    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleException(BadCredentialsException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
