package com.kantor.kantor.controller;

import com.kantor.kantor.model.response.WebResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e){
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.valueOf(e.getStatusCode().value()).getReasonPhrase())
                .message(e.getReason())
                .build();

        return ResponseEntity.status(e.getStatusCode()).body(response);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        String status = null;
        HttpStatus httpStatus = null;
        String message = e.getMostSpecificCause().getMessage();
        String reason = null;

        if (message.contains("foreign key constraint")){
            httpStatus = HttpStatus.BAD_REQUEST;
            status = HttpStatus.BAD_REQUEST.getReasonPhrase();
            reason = "cannot remove data";
        } else if (message.contains("unique constraint") || message.contains("Duplicate entry")) {
            httpStatus = HttpStatus.CONFLICT;
            status = HttpStatus.CONFLICT.getReasonPhrase();
            reason = "Data duplicate";
        }else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            reason = "Internal Serve Error";
        }

        WebResponse<String> response = WebResponse.<String>builder()
                .status(status)
                .message(reason)
                .build();;

        return ResponseEntity.status(httpStatus).body(response);
    }
}
