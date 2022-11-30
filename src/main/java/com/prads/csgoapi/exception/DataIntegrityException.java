package com.prads.csgoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.extern.log4j.Log4j2;

@ResponseStatus(value = HttpStatus.CONFLICT)
@Log4j2
public class DataIntegrityException extends RuntimeException {

  private static final long serialVersionUID = 444578106186483796L;
  private final static String DATA_INTEGRITY_VIOLATION = "There was a data integrity violation.";

  public DataIntegrityException() {
    super(DATA_INTEGRITY_VIOLATION);
    log.error(DATA_INTEGRITY_VIOLATION);
  }

  public DataIntegrityException(String message, Throwable cause) {
    super(message, cause);
    log.error(message, cause);
  }

  public DataIntegrityException(String message) {
    super(message);
    log.error(message);
  }

  public DataIntegrityException(Throwable cause) {
    super(cause);
    log.error(cause);
  }
}
