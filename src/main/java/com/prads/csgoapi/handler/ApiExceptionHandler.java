package com.prads.csgoapi.handler;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<ApiError> handle(
      ConstraintViolationException constraintViolationException) {
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    Set<SubApiError> subErrors = violations.stream().map(violation -> SubApiError.builder()
        .error(violation.getMessageTemplate()).message(violation.getMessage()).build())
        .collect(Collectors.toSet());

    ApiError errors =
        ApiError.builder().timestamp(LocalDateTime.now()).error("Constraint Violation Exception")
            .status(HttpStatus.BAD_REQUEST).subErrors(subErrors).build();

    return ResponseEntity.badRequest().body(errors);
  }

}
