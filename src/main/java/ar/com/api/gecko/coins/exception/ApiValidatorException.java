package ar.com.api.gecko.coins.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiValidatorException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorMessage;

    public ApiValidatorException(String message, String errorMessage, HttpStatus status) {
        super(message);
        this.errorMessage = errorMessage;
        this.httpStatus = status;
    }
}
