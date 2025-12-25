package com.waterfilter.water.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.waterfilter.water.apiResponse.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Handle custom ResourceNotFoundException (your business 404)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>>handleResourcepNotFoundException(
    ResourceNotFoundException ex, HttpServletRequest request
  ){

    ApiResponse<Void> response = ApiResponse.<Void>builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.NOT_FOUND.value())
        .message(ex.getMessage())
        .data(null)
        .path(request.getRequestURI())
        .build();

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  // Handle custom DublicateResourceException (your business 409) -> conflict
  @ExceptionHandler(DublicateResourceException.class)
  public ResponseEntity<ApiResponse<Void>>handleDuplicateRecordException(
    DublicateResourceException ex, HttpServletRequest request
  ){

    ApiResponse<Void> response = ApiResponse.<Void>builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.CONFLICT.value())
        .message(ex.getMessage())
        .data(null)
        .path(request.getRequestURI())
        .build();

    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

      // Handle 404 - No handler found for endpoint
      // endpoint resource not found (url of endpoint is incorrect)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFoundException(NoHandlerFoundException ex,
                  HttpServletRequest request){

                    ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("API endpoint not found: " + ex.getRequestURL())
                    .data(null)
                    .path(request.getRequestURI())
                    .build();

          return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);   
    }

        // Handle 405 - Wrong HTTP method
        // EX. HTTP method is get and you use the endpoint as it have a post endpoint
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex, HttpServletRequest request){
        String message = String.format("Method %s is not support for this endpoint. Supported method: %s ",
         ex.getMessage(), 
         ex.getSupportedHttpMethods() != null ? String.join(", ", ex.getSupportedMethods()) : "N/A");

         ApiResponse<Void> response = ApiResponse.<Void>builder()
         .timestamp(LocalDateTime.now())
         .status(HttpStatus.METHOD_NOT_ALLOWED.value())
         .message(message)
         .data(null)
         .path(request.getRequestURI())
         .build();

         return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
      }

      // Catch-all for any other unexpected exceptions (500 Internal Server Error)
          @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(
      Exception ex, HttpServletRequest request){

        ApiResponse<Void> response = ApiResponse.<Void>builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("Internal server error")
        .data(null)
        .path(request.getRequestURI())
        .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
