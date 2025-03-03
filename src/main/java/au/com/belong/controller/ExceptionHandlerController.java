package au.com.belong.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler({MissingServletRequestParameterException.class, IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Map<String,Object>> handleBadRequestException(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errMsg(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class, AuthenticationException.class})
    public ResponseEntity<Map<String,Object>> handleForbiddenException(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errMsg(ex), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Map<String,Object>> handleNotFoundException(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errMsg(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errMsg(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map errMsg(Exception ex) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("Cause", ex.getMessage());
        map.put("Class", ex.getClass());
        map.put("Timestamp", LocalDateTime.now());
        return map;
    }
}
