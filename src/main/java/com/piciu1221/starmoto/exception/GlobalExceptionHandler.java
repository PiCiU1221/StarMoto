package com.piciu1221.starmoto.exception;

import com.piciu1221.starmoto.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // The same logic as in the UserService
        // Creating a List to always return the same errors first, not in a random order as it is with the set data type
        List<Map<String, String>> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> Map.of(((FieldError) error).getField(), Objects.requireNonNull(error.getDefaultMessage())))
                .sorted(Comparator.comparing(entry -> entry.keySet().iterator().next()))
                .toList();

        Map<String, String> firstError = errors.isEmpty() ? null : errors.get(0);

        return ApiResponse.error(HttpStatus.BAD_REQUEST, getMessageFromError(firstError));
    }

    // Helper method to get the message from the first error
    private String getMessageFromError(Map<String, String> error) {
        return error == null ? "Validation failed. Check your request parameters." : error.values().iterator().next();
    }
}
