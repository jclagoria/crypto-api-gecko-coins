package ar.com.api.gecko.coins.handler;

import ar.com.api.gecko.coins.dto.MarketDTO;
import ar.com.api.gecko.coins.enums.ErrorTypesEnum;
import ar.com.api.gecko.coins.exception.ApiClientErrorException;
import ar.com.api.gecko.coins.exception.ApiCustomException;
import ar.com.api.gecko.coins.model.CoinBase;
import ar.com.api.gecko.coins.model.Market;
import ar.com.api.gecko.coins.services.CoinsGeckoService;
import ar.com.api.gecko.coins.validators.ValidatorOfDTOComponent;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collection;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

class CoinsApiHandlerTest {

    @Mock
    private CoinsGeckoService coinsGeckoServiceMock;

    @Mock
    private ServerRequest serverRequestMock;

    @Mock
    private ValidatorOfDTOComponent validatorOfDTOComponentMock;

    @InjectMocks
    private CoinsApiHandler coinsApiHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        reset(coinsGeckoServiceMock, serverRequestMock, validatorOfDTOComponentMock);
    }

    @Test
    @DisplayName("Ensure successful retrieval of list of Coins service status 200 form Handler")
    void whenGetListOfCoins_ThenItShouldCallDependenciesAndFetchSuccessfully(){
        Collection<CoinBase> expectedListOfCoin = Instancio.ofList(CoinBase.class)
                .size(3).create();
        given(coinsGeckoServiceMock.getListOfCoins())
                .willReturn(Flux.fromIterable(expectedListOfCoin));

        Mono<ServerResponse> actualListOfCoins = coinsApiHandler.getListOfCoins(serverRequestMock);

        StepVerifier
                .create(actualListOfCoins)
                .expectNextMatches(response ->
                        response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(coinsGeckoServiceMock, times(1)).getListOfCoins();
    }

    @Test
    @DisplayName("Ensure error handling in getListOfCoins returns INTERNAL_SERVER_ERROR")
    void whenGetListOfCoinsEncountersError_ThenItShouldHandleErrorAndReturnInternalServerError() {
        given(coinsGeckoServiceMock.getListOfCoins())
                .willReturn(Flux.error(new RuntimeException("Unexpected error")));

        Mono<ServerResponse> errorResponse = coinsApiHandler.getListOfCoins(serverRequestMock);

        StepVerifier.create(errorResponse)
                .expectErrorMatches(throwable -> throwable instanceof ApiCustomException &&
                        throwable.getMessage()
                                .contains("An expected error occurred in getListOfCoins") &&
                        ((ApiCustomException) throwable).getHttpStatus()
                                == HttpStatus.INTERNAL_SERVER_ERROR)
                .verify();

        verify(coinsGeckoServiceMock, times(1)).getListOfCoins();
    }

    @Test
    @DisplayName("Ensure successful retrieval of list of Markets service status 200 form Handler")
    void whenGetMarkets_ThenItShouldCallDependenciesAndFetchSuccessfully() {
        Collection<Market> expectedListOfMarkets = Instancio.ofList(Market.class)
                .size(7).create();
        MarketDTO filterDTO = Instancio.create(MarketDTO.class);
        given(serverRequestMock.queryParam(anyString())).willReturn(Optional.of("vsCurrency"));
        given(validatorOfDTOComponentMock.validation(any())).willReturn(Mono.just(filterDTO));
        given(coinsGeckoServiceMock.getListOfMarkets(any(MarketDTO.class)))
                .willReturn(Flux.fromIterable(expectedListOfMarkets));

        Mono<ServerResponse> expectedObject = coinsApiHandler.getMarkets(serverRequestMock);

        StepVerifier
                .create(expectedObject)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(coinsGeckoServiceMock, times(1)).getListOfMarkets(filterDTO);
    }

    @Test
    @DisplayName("Ensure error handling in getMarkets returns INTERNAL_SERVER_ERROR")
    void whenGetMarketsEncountersError_ThenItShouldHandleErrorAndReturnInternalServerError() {
        MarketDTO filterDTO = Instancio.create(MarketDTO.class);
        given(coinsGeckoServiceMock.getListOfCoins())
                .willReturn(Flux.error(new RuntimeException("Unexpected error")));

        Mono<ServerResponse> errorResponse = coinsApiHandler.getMarkets(serverRequestMock);

        StepVerifier.create(errorResponse)
                .expectErrorMatches(throwable ->
                        throwable instanceof ApiClientErrorException &&
                        ((ApiClientErrorException) throwable).getErrorTypesEnum()
                                .equals(ErrorTypesEnum.API_SERVER_ERROR) &&
                        throwable.getLocalizedMessage().equals("An expected error occurred in getMarkets"))
                .verify();

    }
}