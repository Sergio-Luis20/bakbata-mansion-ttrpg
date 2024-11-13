package br.sergio.bakbata_mansion.exception;

import br.sergio.bakbata_mansion.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<InvalidField> invalidFields = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new InvalidField(error.getField(), String.valueOf(error.getRejectedValue()), error.getDefaultMessage()))
                .toList();
        return ResponseEntity.status(ex.getStatusCode()).body(invalidFields);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {
        ex.printStackTrace();
        return ResponseEntity
                .status(statusCode)
                .headers(headers)
                .body(new Message(statusCode.value(),
                        statusCode.is5xxServerError() ? "Internal Error" : ex.getMessage()));
    }

    public record InvalidField(String field, String rejectedValue, String message) {
    }

}
