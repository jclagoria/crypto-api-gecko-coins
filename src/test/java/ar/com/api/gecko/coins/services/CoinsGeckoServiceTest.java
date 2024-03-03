package ar.com.api.gecko.coins.services;

import ar.com.api.gecko.coins.configuration.ExternalServerConfig;
import ar.com.api.gecko.coins.configuration.HttpServiceCall;
import ar.com.api.gecko.coins.dto.MarketDTO;
import ar.com.api.gecko.coins.enums.ErrorTypesEnum;
import ar.com.api.gecko.coins.exception.ApiServerErrorException;
import ar.com.api.gecko.coins.model.CoinBase;
import ar.com.api.gecko.coins.model.Market;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.BDDMockito.*;

class CoinsGeckoServiceTest {

    @Mock
    private HttpServiceCall httpServiceCallMock;

    @Mock
    private ExternalServerConfig externalServerConfigMock;

    @InjectMocks
    private CoinsGeckoService coinsGeckoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        given(externalServerConfigMock.getCoinList()).willReturn("listOfCoinsGeckoMock");
        given(externalServerConfigMock.getMarkets()).willReturn("listOfMarketsUrlMock");
    }

    @AfterEach
    void tearDown() {
        reset(httpServiceCallMock, externalServerConfigMock);
    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service List Of Coins")
    void whenGetListOfCoinsGeckoServiceCalled_ThenItShouldCallAndFetchSuccessfully() {
        Collection<CoinBase> expectedListOfCoins = Instancio
                .ofList(CoinBase.class).size(3).create();

        given(httpServiceCallMock.getFluxObject(eq("listOfCoinsGeckoMock"), eq(CoinBase.class)))
                .willReturn(Flux.fromIterable(expectedListOfCoins));

        Flux<CoinBase> actualObject = coinsGeckoService.getListOfCoins();

        StepVerifier.create(actualObject)
                .recordWith(ArrayList::new)
                .thenConsumeWhile(item -> true)
                .consumeRecordedWith(listOfCoins -> {
                    Assertions.assertEquals(expectedListOfCoins.size(), listOfCoins.size());
                    Assertions.assertTrue(listOfCoins.containsAll(expectedListOfCoins));
                })
                .verifyComplete();

        verify(externalServerConfigMock, times(2)).getCoinList();
        verify(httpServiceCallMock).getFluxObject("listOfCoinsGeckoMock", CoinBase.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko List of Coins")
    void whenGetListOfCoinsGeckoServiceCalled_ThenItShouldCallAndFetchAndHandleOnStatus4xx() {
        ApiServerErrorException expectedException =
                new ApiServerErrorException("Failed to retrieve info", "Upgrade Required",
                        ErrorTypesEnum.GECKO_CLIENT_ERROR, HttpStatus.UPGRADE_REQUIRED);
        given(httpServiceCallMock.getFluxObject(eq("listOfCoinsGeckoMock"), eq(CoinBase.class)))
                .willReturn(Flux.error(expectedException));

        Flux<CoinBase> actualObject = coinsGeckoService.getListOfCoins();

        StepVerifier
                .create(actualObject)
                .expectErrorMatches(throwable ->
                    throwable instanceof ApiServerErrorException &&
                            throwable.getMessage().contains("Failed to retrieve info") &&
                            ((ApiServerErrorException) throwable).getHttpStatus().is4xxClientError() &&
                            ((ApiServerErrorException) throwable).getErrorTypesEnum()
                                    .equals(ErrorTypesEnum.GECKO_CLIENT_ERROR)
                )
                .verify();

        verify(externalServerConfigMock, times(2)).getCoinList();
        verify(httpServiceCallMock).getFluxObject("listOfCoinsGeckoMock", CoinBase.class);
    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko List of Coins")
    void whenGetListOfCoinsGeckoServiceCalled_ThenItShouldCallAndFetchAndHandleOnStatus5xx() {
        ApiServerErrorException expectedException =
                new ApiServerErrorException("An expected error occurred", "Internal Server Error",
                        ErrorTypesEnum.GECKO_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        given(httpServiceCallMock.getFluxObject(eq("listOfCoinsGeckoMock"), eq(CoinBase.class)))
                .willReturn(Flux.error(expectedException));

        Flux<CoinBase> actualObject = coinsGeckoService.getListOfCoins();

        StepVerifier
                .create(actualObject)
                .expectErrorMatches(throwable ->
                        throwable instanceof ApiServerErrorException &&
                                throwable.getMessage().contains("An expected error occurred") &&
                                ((ApiServerErrorException) throwable).getHttpStatus().is5xxServerError() &&
                                ((ApiServerErrorException) throwable).getErrorTypesEnum()
                                        .equals(ErrorTypesEnum.GECKO_SERVER_ERROR)
                )
                .verify();

        verify(externalServerConfigMock, times(2)).getCoinList();
        verify(httpServiceCallMock).getFluxObject("listOfCoinsGeckoMock", CoinBase.class);
    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service List Of Markets By Currency")
    void whenGetListOfMarketsServiceCalled_ThenItShouldCallAndFetchSuccessfully() {
        Collection<Market> expectedListOfMarkets = Instancio
                .ofList(Market.class).size(5).create();
        MarketDTO filterDTO = Instancio.create(MarketDTO.class);
        given(httpServiceCallMock.getFluxObject(
                "listOfMarketsUrlMock" + filterDTO.getUrlFilterString(), Market.class))
                .willReturn(Flux.fromIterable(expectedListOfMarkets));

        Flux<Market> actualObject = coinsGeckoService.getListOfMarkets(filterDTO);

        StepVerifier.create(actualObject)
                .recordWith(ArrayList::new)
                .thenConsumeWhile(item -> true)
                .consumeRecordedWith(listOfMarket ->{
                    Assertions.assertEquals(expectedListOfMarkets.size(), listOfMarket.size());
                    Assertions.assertTrue(listOfMarket.containsAll(expectedListOfMarkets));
                })
                .verifyComplete();

        verify(externalServerConfigMock, atLeast(2)).getMarkets();
        verify(httpServiceCallMock).getFluxObject("listOfMarketsUrlMock"
                + filterDTO.getUrlFilterString(), Market.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko List of Markets")
    void whenGetListOfMarketsServiceCalled_ThenItShouldCallAndFetchAndHandleOnStatus4xx() {
        ApiServerErrorException expectedException =
                new ApiServerErrorException("Failed to retrieve info of Markets",
                        "Not Acceptable", ErrorTypesEnum.GECKO_CLIENT_ERROR,
                        HttpStatus.NOT_ACCEPTABLE);
        MarketDTO filterDTO = Instancio.create(MarketDTO.class);
        given(httpServiceCallMock.getFluxObject("listOfMarketsUrlMock" + filterDTO.getUrlFilterString(),
                Market.class)).willReturn(Flux.error(expectedException));

        Flux<Market> actualObject = coinsGeckoService.getListOfMarkets(filterDTO);

        StepVerifier
                .create(actualObject)
                .expectErrorMatches(throwable ->
                        throwable instanceof ApiServerErrorException &&
                                throwable.getMessage().contains("Failed to retrieve info of Markets") &&
                                ((ApiServerErrorException) throwable).getHttpStatus().is4xxClientError() &&
                                ((ApiServerErrorException) throwable).getErrorTypesEnum()
                                        .equals(ErrorTypesEnum.GECKO_CLIENT_ERROR))
                .verify();

        verify(externalServerConfigMock, times(2)).getMarkets();
        verify(httpServiceCallMock).getFluxObject(
                "listOfMarketsUrlMock" + filterDTO.getUrlFilterString(), Market.class);
    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko List of Markets")
    void whenGetListOfMarketsServiceCalled_ThenItShouldCallAndFetchAndHandleOnStatus5xx() {
        ApiServerErrorException expectedException =
                new ApiServerErrorException("An expected error occurred",
                        "Gateway Timeout", ErrorTypesEnum.GECKO_SERVER_ERROR,
                        HttpStatus.GATEWAY_TIMEOUT);
        MarketDTO filterDTO = Instancio.create(MarketDTO.class);
        given(httpServiceCallMock.getFluxObject("listOfMarketsUrlMock" + filterDTO.getUrlFilterString(),
                Market.class)).willReturn(Flux.error(expectedException));

        Flux<Market> actualObject = coinsGeckoService.getListOfMarkets(filterDTO);

        StepVerifier
                .create(actualObject)
                .expectErrorMatches(throwable ->
                        throwable instanceof ApiServerErrorException &&
                                throwable.getMessage().contains("An expected error occurred") &&
                                ((ApiServerErrorException) throwable).getHttpStatus().is5xxServerError() &&
                                ((ApiServerErrorException) throwable).getErrorTypesEnum()
                                        .equals(ErrorTypesEnum.GECKO_SERVER_ERROR))
                .verify();

        verify(externalServerConfigMock, times(2)).getMarkets();
        verify(httpServiceCallMock).getFluxObject(
                "listOfMarketsUrlMock" + filterDTO.getUrlFilterString(), Market.class);
    }
}