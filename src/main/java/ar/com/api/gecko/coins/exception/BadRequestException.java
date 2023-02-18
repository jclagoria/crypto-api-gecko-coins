package ar.com.api.gecko.coins.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadRequestException extends Exception {
 
 public BadRequestException(String message) {
  super(message);
 }

}
