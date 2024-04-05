package dev.ikhtiyor.springrocksdb.exception;

import dev.ikhtiyor.springrocksdb.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlers {

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<?> handle(Throwable ex, HttpServletRequest httpServletRequest) {

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                httpServletRequest.getRequestURI()
        );
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<?> handle(CustomException ex, HttpServletRequest httpServletRequest) {

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                httpServletRequest.getRequestURI()
        );
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus httpStatus, String message, String endpoint) {
        ErrorResponseDTO errorResponseDTO = getErrorResponseDTO(httpStatus, message, endpoint);
        return ResponseEntity.status(httpStatus).body(errorResponseDTO);
    }

    private static ErrorResponseDTO getErrorResponseDTO(HttpStatus httpStatus, String message, String endpoint) {
        return new ErrorResponseDTO(
                message,
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                endpoint
        );

    }
}