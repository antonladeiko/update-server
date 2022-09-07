package ru.clevertec.app.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class FileApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {FIleUploadException.class})
    public ResponseEntity<Object> handleFileUploadException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(exception.getMessage());
    }

    @ExceptionHandler(value = ApkFileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(value = {DuplicateVersionException.class})
    public ResponseEntity<Object> handleDuplicateFileException(Exception exception){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
}
