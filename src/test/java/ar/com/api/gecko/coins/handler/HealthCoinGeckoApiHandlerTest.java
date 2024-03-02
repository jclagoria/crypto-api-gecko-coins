package ar.com.api.gecko.coins.handler;

import ar.com.api.gecko.coins.exception.ApiClientErrorException;
import ar.com.api.gecko.coins.model.Ping;
import ar.com.api.gecko.coins.services.HealthCoinGeckoApiStatus;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.*;

class HealthCoinGeckoApiHandlerTest {

    @Mock
    private HealthCoinGeckoApiStatus healthCoinGeckoApiStatusMock;

    @Mock
    private ServerRequest serverRequestMock;

    @InjectMocks
    private HealthCoinGeckoApiHandler apiHandlerMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        reset(serverRequestMock, healthCoinGeckoApiStatusMock);
    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service status 200 form Handler")
    void whenGetStatusServiceCoinGecko_ThenItShouldCallDependenciesAndFetchSuccessfully() {
        Ping expectedObject = Instancio.create(Ping.class);
        given(healthCoinGeckoApiStatusMock.getStatusCoinGeckoService())
                .willReturn(Mono.just(expectedObject));

        Mono<ServerResponse> actualResponseMono = apiHandlerMock.getStatusServiceCoinGecko(serverRequestMock);

        StepVerifier
                .create(actualResponseMono)
                .expectNextMatches(response ->
                        response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(healthCoinGeckoApiStatusMock, times(1)).getStatusCoinGeckoService();
    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko service status form Handler")
    void whenGetStatusServiceCoinGecko_ThenItShouldCallAndHandlesOnStatus5xx() {
        given(healthCoinGeckoApiStatusMock.getStatusCoinGeckoService())
                .willReturn(Mono.error(new RuntimeException("Error occurred")));

        Mono<ServerResponse> responseActual = apiHandlerMock.getStatusServiceCoinGecko(serverRequestMock);

        StepVerifier
                .create(responseActual)
                .expectErrorMatches(response ->
                        response instanceof ApiClientErrorException &&
                        response.getMessage().equals("An expected error occurred in getStatusServiceCoinGecko"))
                .verify();
    }

}