package com.vinicius.backend.configuration;

import com.vinicius.backend.exception.BusinessException;
import com.vinicius.backend.exception.ExceptionResolver;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestControllerAdvice
public class ControlExceptionHandler {


    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleConflict(BusinessException ex) {
        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(ex.getBody());

    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(Throwable eThrowable) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(Optional.ofNullable(eThrowable.getMessage())
                                 .orElse(eThrowable.toString()))
                .details(ExceptionResolver.getRootException(eThrowable))
                .build();

        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(ex.getBody());

    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exMethod,
                                                                   WebRequest request) {

        String error = exMethod.getName() +
                       " deveria ser " +
                       Objects.requireNonNull(exMethod.getRequiredType())
                               .getName();

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message("Tipo de argumento inválido")
                .details(error)
                .build();

        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(ex.getBody());
    }


    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exMethod,
                                                            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : exMethod.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass()
                               .getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message("Violação de regra de negócio")
                .details(errors.toString())
                .build();


        return ResponseEntity.status(ex.getHttpStatusCode())

                .body(ex.getBody());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> validationError(MethodArgumentNotValidException exMethod) {

        BindingResult bindingResult = exMethod.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<String> fieldErrorDtos = fieldErrors.stream()
                .map(f -> f.getField()
                        .concat(":")
                        .concat(Objects.requireNonNull(f.getDefaultMessage())))
                .map(String::new)
                .toList();

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message("Alguns campos não foram preenchidos corretamente.")
                .details(fieldErrorDtos.toString())
                .build();

        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(ex.getBody());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> validationError(HttpMessageNotReadableException exMethod) {

        Throwable mostSpecificCause = exMethod.getMostSpecificCause();
        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message("Formato de corpo de requisição inválido")
                .details(Optional.ofNullable(mostSpecificCause.getMessage())
                                 .orElse("Formato de corpo de requisição inválido"))
                .build();

        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(ex.getBody());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(MissingServletRequestParameterException e) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(Optional.of(e.getMessage())
                                 .orElse(e.toString()))
                .details(ExceptionResolver.getRootException(e))
                .build();


        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(ex.getBody());

    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException e) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.METHOD_NOT_ALLOWED)
                .message(Optional.ofNullable(e.getMessage())
                                 .orElse(e.toString()))
                .details(ExceptionResolver.getRootException(e))
                .build();


        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(ex.getBody());
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Object> handleException(HttpClientErrorException e) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(e.getStatusCode())
                .message(Optional.of(e.getStatusText())
                                 .orElse(e.toString()))
                .details(ExceptionResolver.getRootException(e))
                .build();


        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(ex.getBody());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(IllegalArgumentException e) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(Optional.ofNullable(e.getMessage())
                                 .orElse(e.toString()))
                .details(ExceptionResolver.getRootException(e))
                .build();

        return ResponseEntity.status(ex.getHttpStatusCode())

                .body(ex.getBody());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(Exception e) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(Optional.ofNullable(e.getMessage())
                                 .orElse(e.toString()))
                .details(ExceptionResolver.getRootException(e))
                .build();

        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(ex.getBody());
    }
}
