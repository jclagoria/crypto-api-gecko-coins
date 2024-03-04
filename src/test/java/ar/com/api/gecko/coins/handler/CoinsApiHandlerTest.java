package ar.com.api.gecko.coins.handler;

import ar.com.api.gecko.coins.dto.CoinFilterDTO;
import ar.com.api.gecko.coins.dto.MarketDTO;
import ar.com.api.gecko.coins.dto.TickerByIdDTO;
import ar.com.api.gecko.coins.model.CoinBase;
import ar.com.api.gecko.coins.model.CoinInfo;
import ar.com.api.gecko.coins.model.CoinTickerById;
import ar.com.api.gecko.coins.model.Market;
import ar.com.api.gecko.coins.services.CoinsGeckoService;
import ar.com.api.gecko.coins.utils.CoinsTestUtils;
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

        CoinsTestUtils.assertMonoSuccess(actualListOfCoins, serverResponse ->
                serverResponse.statusCode().is2xxSuccessful());

        verify(coinsGeckoServiceMock, times(1)).getListOfCoins();
    }

    @Test
    @DisplayName("Ensure error handling in getListOfCoins returns INTERNAL_SERVER_ERROR")
    void whenGetListOfCoinsEncountersError_ThenItShouldHandleErrorAndReturnInternalServerError() {
        given(coinsGeckoServiceMock.getListOfCoins())
                .willReturn(Flux.error(new RuntimeException("Unexpected error")));

        Mono<ServerResponse> errorResponse = coinsApiHandler.getListOfCoins(serverRequestMock);

        CoinsTestUtils.assertClient5xxServerError(errorResponse,
                "An expected error occurred in getListOfCoins",
                HttpStatus.INTERNAL_SERVER_ERROR);

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

        Mono<ServerResponse> expectedResponse = coinsApiHandler.getMarkets(serverRequestMock);

        CoinsTestUtils.assertMonoSuccess(expectedResponse, serverResponse ->
                serverResponse.statusCode().is2xxSuccessful());

        verify(coinsGeckoServiceMock, times(1)).getListOfMarkets(filterDTO);
    }

    @Test
    @DisplayName("Ensure error handling in getMarkets returns INTERNAL_SERVER_ERROR")
    void whenGetMarketsEncountersError_ThenItShouldHandleErrorAndReturnInternalServerError() {
        given(coinsGeckoServiceMock.getListOfCoins())
                .willReturn(Flux.error(new RuntimeException("Unexpected error")));

        Mono<ServerResponse> errorResponse = coinsApiHandler.getMarkets(serverRequestMock);

        CoinsTestUtils.assertClient5xxServerError(errorResponse,
                "An expected error occurred in getMarkets",
                HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Test
    @DisplayName("Ensure successful retrieval a Coin by ID from service status 200 form Handler")
    void whenGetCoinById_ThenItShouldCallDependenciesAndFetchSuccessfully() {
        CoinInfo expectedObject = Instancio.create(CoinInfo.class);
        CoinFilterDTO filterDTO = Instancio.create(CoinFilterDTO.class);
        given(serverRequestMock.pathVariable(anyString())).willReturn("bitcoin");
        given(validatorOfDTOComponentMock.validation(any())).willReturn(Mono.just(filterDTO));
        given(coinsGeckoServiceMock.getCoinById(any(CoinFilterDTO.class))).willReturn(Mono.just(expectedObject));

        Mono<ServerResponse> expectedResponse = coinsApiHandler.getCoinById(serverRequestMock);

        CoinsTestUtils.assertMonoSuccess(expectedResponse, serverResponse ->
                serverResponse.statusCode().is2xxSuccessful());

        verify(coinsGeckoServiceMock, times(1)).getCoinById(filterDTO);
    }

    @Test
    @DisplayName("Ensure error handling in getCoinById returns INTERNAL_SERVER_ERROR")
    void whenGetCoinById_ThenItShouldHandleErrorAndReturnInternalServerError() {
        given(coinsGeckoServiceMock.getCoinById(any(CoinFilterDTO.class)))
                .willReturn(Mono.error(new RuntimeException("Unexpected error")));

        Mono<ServerResponse> errorResponse = coinsApiHandler.getCoinById(serverRequestMock);

        CoinsTestUtils.assertClient5xxServerError(errorResponse,
                "An expected error occurred in getCoinById",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Ensure successful retrieval a Tickers by ID from service status 200 form Handler")
    void whenGetTickersById_ThenItShouldCallDependenciesAndFetchSuccessfully() {
        CoinTickerById expectedObject = Instancio.create(CoinTickerById.class);
        TickerByIdDTO filterDTO = Instancio.create(TickerByIdDTO.class);
        given(serverRequestMock.pathVariable(anyString())).willReturn("ethereum");
        given(validatorOfDTOComponentMock.validation(any())).willReturn(Mono.just(filterDTO));
        given(coinsGeckoServiceMock.getTickerById(any(TickerByIdDTO.class)))
                .willReturn(Mono.just(expectedObject));

        Mono<ServerResponse> expectedResponse = coinsApiHandler.getTickersById(serverRequestMock);

        CoinsTestUtils.assertMonoSuccess(expectedResponse, serverResponse ->
                serverResponse.statusCode().is2xxSuccessful());

        verify(coinsGeckoServiceMock, times(1)).getTickerById(filterDTO);
    }

    @Test
    @DisplayName("Ensure error handling in getTickersByI returns INTERNAL_SERVER_ERROR")
    void whenGetTickersById_ThenItShouldHandleErrorAndReturnInternalServerError() {
        given(coinsGeckoServiceMock.getTickerById(any(TickerByIdDTO.class)))
                .willReturn(Mono.error(new RuntimeException("Unexpected error")));

        Mono<ServerResponse> errorResponse = coinsApiHandler.getTickersById(serverRequestMock);

        CoinsTestUtils.assertClient5xxServerError(errorResponse,
                "An expected error occurred in getTickersById",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}