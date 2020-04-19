package com.tyba.handler;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationExceptionHandler {

  /**
   * Handle all the defined {@link ApplicationException}
   *
   * @param e ApplicationException
   *
   * @return An {@link Error} with the status of the ApplicationException
   */
  @ExceptionHandler({ApplicationException.class})
  public ResponseEntity<Error> handleHashException(final ApplicationException e) {
    HttpStatus status = e.getStatus();
    return ResponseEntity
        .status(status)
        .body(new Error(status, e.getReason(), e.getCode()));
  }

  /**
   * Handle all the constraint validations, such as {@link javax.validation.constraints.NotNull}
   *
   * @param e {@link MethodArgumentNotValidException}
   *
   * @return List of missing or not valid fields
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public Map<String, String> handleMandatoryField(final MethodArgumentNotValidException e) {
    return e.getBindingResult().getAllErrors().stream()
        .collect(Collectors.toMap(
            error -> ((FieldError) error).getField(),
            DefaultMessageSourceResolvable::getDefaultMessage
        ));
  }

}