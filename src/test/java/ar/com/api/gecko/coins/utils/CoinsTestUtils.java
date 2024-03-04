package ar.com.api.gecko.coins.utils;

import ar.com.api.gecko.coins.enums.ErrorTypesEnum;
import ar.com.api.gecko.coins.exception.ApiClientErrorException;
import ar.com.api.gecko.coins.exception.ApiServerErrorException;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class CoinsTestUtils {

    public static void assertService4xxClientError(Publisher<?> publisher,
                                                   String expectedMessage,
                                                   ErrorTypesEnum expectedErrorType) {
        StepVerifier
                .create(publisher)
                .expectErrorMatches(throwable ->
                        throwable instanceof ApiServerErrorException &&
                                throwable.getMessage().equals(expectedMessage) &&
                                ((ApiServerErrorException) throwable).getHttpStatus().is4xxClientError() &&
                                ((ApiServerErrorException) throwable).getErrorTypesEnum().equals(expectedErrorType))
                .verify();
    }

    public static void assertService5xxServerError(Publisher<?> publisher,
                                                   String expectedMessage,
                                                   ErrorTypesEnum expectedErrorType) {
        StepVerifier
                .create(publisher)
                .expectErrorMatches(throwable ->
                        throwable instanceof ApiServerErrorException &&
                                throwable.getMessage().equals(expectedMessage) &&
                                ((ApiServerErrorException) throwable).getHttpStatus().is5xxServerError() &&
                                ((ApiServerErrorException) throwable).getErrorTypesEnum().equals(expectedErrorType))
                .verify();
    }

    public static void assertClient5xxServerError(Publisher<?> publisher,
                                                   String expectedMessage,
                                                  HttpStatus status) {

        StepVerifier.create(publisher)
                .expectErrorMatches(throwable -> throwable instanceof ApiClientErrorException &&
                        throwable.getMessage().equals(expectedMessage) &&
                        ((ApiClientErrorException) throwable).getHttpStatus() == status &&
                        ((ApiClientErrorException) throwable).getErrorTypesEnum()
                                .equals(ErrorTypesEnum.API_SERVER_ERROR))
                .verify();
    }

    public static  <T> void assertFluxSuccess(Flux<T> flux, Consumer<Collection<T>> assertions) {
        StepVerifier.create(flux)
                .recordWith(ArrayList::new)
                .thenConsumeWhile(item -> true)
                .consumeRecordedWith(assertions)
                .verifyComplete();
    }

    public static  <T> void assertMonoSuccess(Mono<T> mono, Consumer<T> assertions) {
        StepVerifier.create(mono)
                .assertNext(assertions)
                .verifyComplete();
    }

}
