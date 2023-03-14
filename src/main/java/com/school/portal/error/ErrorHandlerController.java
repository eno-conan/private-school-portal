package com.school.portal.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerController {

  // @ExceptionHandler({AccessDeniedException.class})
  // @ResponseStatus(HttpStatus.FORBIDDEN)
  // public ErrorResponse handleException(AccessDeniedException e) {
  // log.error("Error:", e.getMessage());
  // return new ErrorResponse("notAuthorized", "The request was not authorized.");
  // }

  // @ExceptionHandler({EmployeeNotFoundException.class})
  // @ResponseStatus(HttpStatus.NOT_FOUND)
  // public ErrorResponse handleEmployeeNotFoundException(EmployeeNotFoundException e) {
  // log.error("Error:", e.getMessage());
  // return new ErrorResponse("notFound", "The Employee was not found.");
  // }

  @ExceptionHandler({Exception.class})
  // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleException(Exception e) {
    log.error("Error:", e.getMessage());
    return new ErrorResponse("systemError", "System error occurred.");
  }
}
