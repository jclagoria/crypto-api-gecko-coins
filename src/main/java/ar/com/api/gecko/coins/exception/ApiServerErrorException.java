package ar.com.api.gecko.coins.exception;

import ar.com.api.gecko.coins.enums.ErrorTypesEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiServerErrorException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String originalMessage;
    private final ErrorTypesEnum errorTypesEnum;

    public ApiServerErrorException(String message, String originalMessage,
                                   ErrorTypesEnum typesEnum, HttpStatus status) {
        super(message);
        this.httpStatus = status;
        this.originalMessage = originalMessage;
        this.errorTypesEnum = typesEnum;
    }

}
