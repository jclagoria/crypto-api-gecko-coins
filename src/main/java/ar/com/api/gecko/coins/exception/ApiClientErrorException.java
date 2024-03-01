package ar.com.api.gecko.coins.exception;

import ar.com.api.gecko.coins.enums.ErrorTypesEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiClientErrorException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ErrorTypesEnum errorTypesEnum;

    public ApiClientErrorException(String message, HttpStatus status, ErrorTypesEnum typesEnum) {
        super(message);
        this.httpStatus = status;
        this.errorTypesEnum = typesEnum;
    }
}
