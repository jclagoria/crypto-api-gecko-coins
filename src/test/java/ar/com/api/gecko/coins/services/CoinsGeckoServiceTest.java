package ar.com.api.gecko.coins.services;

import ar.com.api.gecko.coins.configuration.ExternalServerConfig;
import ar.com.api.gecko.coins.configuration.HttpServiceCall;
import ar.com.api.gecko.coins.dto.*;
import ar.com.api.gecko.coins.enums.ErrorTypesEnum;
import ar.com.api.gecko.coins.exception.ApiServerErrorException;
import ar.com.api.gecko.coins.model.*;
import ar.com.api.gecko.coins.utils.CoinsTestUtils;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        given(externalServerConfigMock.getCoinById()).willReturn("getCoinByIdUrlMock");
        given(externalServerConfigMock.getTickersById()).willReturn("getTickersByIdUrlMock");
        given(externalServerConfigMock.getHistoryCoin()).willReturn("getHistoryOfCoinByIdMock");
        given(externalServerConfigMock.getMarketChartCoin()).willReturn("getMarketChartByIdCurrencyDaysMock");
        given(externalServerConfigMock.getMarketChartRange()).willReturn("getMarketChartRangeByIdCurrencyAndRangeDate");
        given(externalServerConfigMock.getOhlcById()).willReturn("getOHLCUrlMock");
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

        CoinsTestUtils.assertFluxSuccess(actualObject, listOfCoins -> {
            Assertions.assertEquals(expectedListOfCoins.size(), listOfCoins.size());
            Assertions.assertTrue(listOfCoins.containsAll(expectedListOfCoins));
        });

        verify(externalServerConfigMock, times(2)).getCoinList();
        verify(httpServiceCallMock).getFluxObject("listOfCoinsGeckoMock", CoinBase.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko List of Coins")
    void whenGetListOfCoinsGeckoServiceCalled_ThenItShouldCallAndFetchAndHandleOnStatus4xx() {
        ApiServerErrorException expectedException =
                new ApiServerErrorException("Failed to retrieve list of Coins", "Upgrade Required",
                        ErrorTypesEnum.GECKO_CLIENT_ERROR, HttpStatus.UPGRADE_REQUIRED);
        given(httpServiceCallMock.getFluxObject(eq("listOfCoinsGeckoMock"), eq(CoinBase.class)))
                .willReturn(Flux.error(expectedException));

        Flux<CoinBase> actualObject = coinsGeckoService.getListOfCoins();

        CoinsTestUtils.assertService4xxClientError(actualObject,
                "Failed to retrieve list of Coins",
                ErrorTypesEnum.GECKO_CLIENT_ERROR);

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

        CoinsTestUtils.assertService5xxServerError(actualObject, "An expected error occurred",
                ErrorTypesEnum.GECKO_SERVER_ERROR);

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

        CoinsTestUtils.assertFluxSuccess(actualObject, listOfMarket ->{
            Assertions.assertEquals(expectedListOfMarkets.size(), listOfMarket.size());
            Assertions.assertTrue(listOfMarket.containsAll(expectedListOfMarkets));
        });

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

        CoinsTestUtils.assertService4xxClientError(actualObject,
                "Failed to retrieve info of Markets",
                ErrorTypesEnum.GECKO_CLIENT_ERROR);

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

        CoinsTestUtils.assertService5xxServerError(actualObject, "An expected error occurred",
                ErrorTypesEnum.GECKO_SERVER_ERROR);

        verify(externalServerConfigMock, times(2)).getMarkets();
        verify(httpServiceCallMock).getFluxObject(
                "listOfMarketsUrlMock" + filterDTO.getUrlFilterString(), Market.class);
    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service of Coin By ID")
    void whenGetCoinById_ThenItShouldCallAndFetchSuccessfully() {
        CoinInfo expectedCoinInfo = Instancio.create(CoinInfo.class);
        CoinFilterDTO filterDTO = Instancio.create(CoinFilterDTO.class);
        given(httpServiceCallMock.getMonoObject(eq("getCoinByIdUrlMock"
                + filterDTO.getUrlFilterString()), eq(CoinInfo.class))).willReturn(Mono.just(expectedCoinInfo));

        Mono<CoinInfo> actualObject = coinsGeckoService.getCoinById(filterDTO);

        CoinsTestUtils.assertMonoSuccess(actualObject, coinInfoExpected -> {
            Optional.ofNullable(coinInfoExpected.getAssetPlatformId()).ifPresentOrElse(
                    name -> {},
                    () -> fail("Assert Platform not be null"));

            Optional.of(coinInfoExpected.getSentimentVotesDownPercentage()).ifPresentOrElse(
                    sentimentVotesDown -> {},
                    () -> fail("Sentiment Vote Down not be null")
            );
        });

        verify(externalServerConfigMock, times(1)).getCoinById();
        verify(httpServiceCallMock).getMonoObject("getCoinByIdUrlMock"
                + filterDTO.getUrlFilterString(), CoinInfo.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko of Coin by ID")
    void whenGetCoinById_ThenItShouldCallAndFetchAndHandleOnStatus4xx() {
        CoinFilterDTO filterDTO = Instancio.create(CoinFilterDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException("Failed to retrieve info of Coin By ID",
                "Not Acceptable",
                ErrorTypesEnum.GECKO_CLIENT_ERROR,
                HttpStatus.NOT_ACCEPTABLE);
        given(httpServiceCallMock.getMonoObject(eq("getCoinByIdUrlMock"
                + filterDTO.getUrlFilterString()), eq(CoinInfo.class))).willReturn(Mono.error(expectedError));

        Mono<CoinInfo> actualObject = coinsGeckoService.getCoinById(filterDTO);

        CoinsTestUtils.assertService4xxClientError(actualObject,
                "Failed to retrieve info of Coin By ID",
                ErrorTypesEnum.GECKO_CLIENT_ERROR);

        verify(externalServerConfigMock, times(1)).getCoinById();
        verify(httpServiceCallMock).getMonoObject("getCoinByIdUrlMock"
                + filterDTO.getUrlFilterString(), CoinInfo.class);

    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko of Coin by ID")
    void whenGetCoinById_ThenItShouldCallAndFetchAndHandleOnStatus5xx() {
        CoinFilterDTO filterDTO = Instancio.create(CoinFilterDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException("An expected error occurred",
                "Bandwidth Limit Exceeded",
                ErrorTypesEnum.GECKO_SERVER_ERROR,
                HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
        given(httpServiceCallMock.getMonoObject(eq("getCoinByIdUrlMock"
                + filterDTO.getUrlFilterString()), eq(CoinInfo.class))).willReturn(Mono.error(expectedError));

        Mono<CoinInfo> actualObject = coinsGeckoService.getCoinById(filterDTO);

        CoinsTestUtils.assertService5xxServerError(actualObject, "An expected error occurred",
                ErrorTypesEnum.GECKO_SERVER_ERROR);

        verify(externalServerConfigMock, times(1)).getCoinById();
        verify(httpServiceCallMock).getMonoObject("getCoinByIdUrlMock"
                + filterDTO.getUrlFilterString(), CoinInfo.class);

    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service of Tickers By Coin ID")
    void whenGetTickerById_ThenItShouldCallAndFetchSuccessfully() {
        CoinTickerById expectedObject = Instancio.create(CoinTickerById.class);
        TickerByIdDTO filterDTO = Instancio.create(TickerByIdDTO.class);
        given(httpServiceCallMock.getMonoObject(eq("getTickersByIdUrlMock"
                + filterDTO.getUrlFilterString()), eq(CoinTickerById.class))).willReturn(Mono.just(expectedObject));

        Mono<CoinTickerById> actualObject = coinsGeckoService.getTickerById(filterDTO);

        CoinsTestUtils.assertMonoSuccess(actualObject, coinTickerById -> {
            Optional.ofNullable(coinTickerById.getName()).ifPresentOrElse(
                    name -> {},
                    () -> fail("Name should not be null"));
            Optional.ofNullable(coinTickerById.getTickers()).ifPresentOrElse(
                    tickers -> {
                        assertFalse(tickers.isEmpty(), "Tickers should not be empty");
                        tickers.forEach(ticker -> {
                            assertNotNull(ticker.getBase(), "Base should not be null");
                            assertNotNull(ticker.getTarget(), "Target should not be null");
                        });
                    },
                    () -> fail("Tickers should not be null"));
        });

        verify(externalServerConfigMock, times(1)).getTickersById();
        verify(httpServiceCallMock).getMonoObject("getTickersByIdUrlMock"
                +filterDTO.getUrlFilterString(), CoinTickerById.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko of Tickers by Coin ID")
    void whenGetTickerById_ThenItShouldCallAndFetchAndHandleOnStatus4xx() {
        TickerByIdDTO filterDTO = Instancio.create(TickerByIdDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "Failed to retrieve info of Ticker by Coin ID", "Bad Request",
                ErrorTypesEnum.GECKO_CLIENT_ERROR, HttpStatus.BAD_REQUEST);
        given(httpServiceCallMock.getMonoObject(eq("getTickersByIdUrlMock"
                + filterDTO.getUrlFilterString()), eq(CoinTickerById.class))).willReturn(Mono.error(expectedError));

        Mono<CoinTickerById> actualObject = coinsGeckoService.getTickerById(filterDTO);

        CoinsTestUtils.assertService4xxClientError(actualObject,
                "Failed to retrieve info of Ticker by Coin ID",
                ErrorTypesEnum.GECKO_CLIENT_ERROR);

        verify(externalServerConfigMock, times(1)).getTickersById();
        verify(httpServiceCallMock).getMonoObject("getTickersByIdUrlMock"
                + filterDTO.getUrlFilterString(), CoinTickerById.class);
    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko of Tickers by Coin ID")
    void whenGetTickerById_ThenItShouldCallAndFetchAndHandleOnStatus5xx() {
        TickerByIdDTO filterDTO = Instancio.create(TickerByIdDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "An expected error occurred", "Internal Server Error",
                ErrorTypesEnum.GECKO_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        given(httpServiceCallMock.getMonoObject(eq("getTickersByIdUrlMock"
                + filterDTO.getUrlFilterString()), eq(CoinTickerById.class))).willReturn(Mono.error(expectedError));

        Mono<CoinTickerById> actualObject = coinsGeckoService.getTickerById(filterDTO);

        CoinsTestUtils.assertService5xxServerError(actualObject,
                "An expected error occurred",
                ErrorTypesEnum.GECKO_SERVER_ERROR);

        verify(externalServerConfigMock, times(1)).getTickersById();
        verify(httpServiceCallMock).getMonoObject("getTickersByIdUrlMock"
                + filterDTO.getUrlFilterString(), CoinTickerById.class);
    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service of History of Coin by ID and specific Date")
    void whenGetCoinHistoryByIdAndDate_ThenItShouldCallAndFetchSuccessfully() {
        CoinHistoryById expectedObject = Instancio.create(CoinHistoryById.class);
        HistoryCoinDTO filterDTO = Instancio.create(HistoryCoinDTO.class);
        given(httpServiceCallMock.getMonoObject(eq("getHistoryOfCoinByIdMock"
                + filterDTO.getUrlFilterString()), eq(CoinHistoryById.class))).willReturn(Mono.just(expectedObject));

        Mono<CoinHistoryById> actualObject = coinsGeckoService.getCoinHistoryByIdAndDate(filterDTO);

        CoinsTestUtils.assertMonoSuccess(actualObject, coinHistoryById -> {
            assertTrue(Optional.ofNullable(coinHistoryById.getId()).isPresent(), "Coin ID should not be null");
            assertTrue(Optional.ofNullable(coinHistoryById.getImage()).isPresent(), "Name should not be null");
            assertEquals(coinHistoryById.getId(), expectedObject.getId());
            assertTrue(Optional.ofNullable(coinHistoryById.getLocalization())
                    .map(localization -> !localization.isEmpty())
                    .orElse(false), "Localization should not be empty");
        });

        verify(externalServerConfigMock, times(1)).getHistoryCoin();
        verify(httpServiceCallMock).getMonoObject("getHistoryOfCoinByIdMock"
                + filterDTO.getUrlFilterString(), CoinHistoryById.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko of of History of Coin by ID and specific Date")
    void whenGetCoinHistoryByIdAndDate_ThenItShouldCallAndFetchAndHandleOnStatus4xx() {
        HistoryCoinDTO filterDTO = Instancio.create(HistoryCoinDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "Failed to retrieve info of Historic of coin by ID and Date",
                "Precondition Failed", ErrorTypesEnum.GECKO_CLIENT_ERROR, HttpStatus.PRECONDITION_FAILED);
        given(httpServiceCallMock.getMonoObject(eq("getHistoryOfCoinByIdMock"
                + filterDTO.getUrlFilterString()), eq(CoinHistoryById.class))).willReturn(Mono.error(expectedError));

        Mono<CoinHistoryById> actualErrorException = coinsGeckoService.getCoinHistoryByIdAndDate(filterDTO);

        CoinsTestUtils.assertService4xxClientError(actualErrorException,
                "Failed to retrieve info of Historic of coin by ID and Date",
                ErrorTypesEnum.GECKO_CLIENT_ERROR);

        verify(externalServerConfigMock, times(1)).getHistoryCoin();
        verify(httpServiceCallMock).getMonoObject("getHistoryOfCoinByIdMock"
                + filterDTO.getUrlFilterString(), CoinHistoryById.class);
    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko of of History of Coin by ID and specific Date")
    void whenGetCoinHistoryByIdAndDate_ThenItShouldCallAndFetchAndHandleOnStatus5xx() {
        HistoryCoinDTO filterDTO = Instancio.create(HistoryCoinDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "An expected error occurred",
                HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.getReasonPhrase(),
                ErrorTypesEnum.GECKO_SERVER_ERROR,
                HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        given(httpServiceCallMock.getMonoObject(eq("getHistoryOfCoinByIdMock"
                + filterDTO.getUrlFilterString()), eq(CoinHistoryById.class))).willReturn(Mono.error(expectedError));

        Mono<CoinHistoryById> actualErrorException = coinsGeckoService.getCoinHistoryByIdAndDate(filterDTO);

        CoinsTestUtils.assertService5xxServerError(actualErrorException,
                "An expected error occurred",
                ErrorTypesEnum.GECKO_SERVER_ERROR);

        verify(externalServerConfigMock, times(1)).getHistoryCoin();
        verify(httpServiceCallMock).getMonoObject("getHistoryOfCoinByIdMock"
                + filterDTO.getUrlFilterString(), CoinHistoryById.class);
    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service of Market Chart By Coin ID and Currency and cant of days")
    void whenGetMarketChartById_ThenItShouldCallAndFetchSuccessfully() {
        MarketChartById expectedObject = Instancio.create(MarketChartById.class);
        MarketChatBiIdDTO filterDTO = Instancio.create(MarketChatBiIdDTO.class);
        given(httpServiceCallMock.getMonoObject(eq("getMarketChartByIdCurrencyDaysMock" +
                filterDTO.getUrlFilterString()) , eq(MarketChartById.class))).willReturn(Mono.just(expectedObject));

        Mono<MarketChartById> actualObject = coinsGeckoService.getMarketChartById(filterDTO);

        CoinsTestUtils.assertMonoSuccess(actualObject, marketChartById -> {
            assertTrue(Optional.ofNullable(marketChartById.getMarketCaps()).isPresent(),
                    "Market Caps should not be null");
            assertTrue(Optional.of(marketChartById.getMarketCaps())
                    .map(marketChart -> !marketChart
                            .isEmpty()).orElse(false), "List of MarketChar should not be empty");
            assertTrue(Optional.of(marketChartById.getMarketCaps()).map(marketChart ->
                            marketChart.containsAll(expectedObject.getMarketCaps())).orElse(false),
                    "The list of received objects is not equal to the expected one");
            assertTrue(Optional.of(marketChartById.getPrices())
                    .map(prices -> !prices
                            .isEmpty()).orElse(false), "List of Prices should not be empty");
            assertTrue(Optional.of(marketChartById.getTotalVolumes())
                    .map(totalVolumes -> !totalVolumes
                            .isEmpty()).orElse(false), "List of Total Volumes should not be empty");
        });

        verify(externalServerConfigMock, times(1)).getMarketChartCoin();
        verify(httpServiceCallMock).getMonoObject("getMarketChartByIdCurrencyDaysMock" +
                filterDTO.getUrlFilterString(), MarketChartById.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko of Market Chart By Coin ID and Currency and cant of days")
    void whenGetMarketChartById_ThenItShouldCallAndFetchAndHandleOnStatus4xx() {
        MarketChatBiIdDTO filterDTO = Instancio.create(MarketChatBiIdDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "Failed to retrieve info of market Chart of coin by ID and Currency and Days",
                "Bad Request", ErrorTypesEnum.GECKO_CLIENT_ERROR,
                HttpStatus.BAD_REQUEST);
        given(httpServiceCallMock.getMonoObject(eq("getMarketChartByIdCurrencyDaysMock"
                + filterDTO.getUrlFilterString()), eq(MarketChartById.class))).willReturn(Mono.error(expectedError));

        Mono<MarketChartById> actualErrorException = coinsGeckoService.getMarketChartById(filterDTO);

        CoinsTestUtils.assertService4xxClientError(actualErrorException,
                "Failed to retrieve info of market Chart of coin by ID and Currency and Days",
                ErrorTypesEnum.GECKO_CLIENT_ERROR);

        verify(externalServerConfigMock, times(1)).getMarketChartCoin();
        verify(httpServiceCallMock).getMonoObject("getMarketChartByIdCurrencyDaysMock" +
                filterDTO.getUrlFilterString(), MarketChartById.class);
    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko of Market Chart By Coin ID and Currency and cant of days")
    void whenGetMarketChartById_ThenItShouldCallAndFetchAndHandleOnStatus5xx() {
        MarketChatBiIdDTO filterDTO = Instancio.create(MarketChatBiIdDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "An expected error occurred",
                "Internal Server Error", ErrorTypesEnum.GECKO_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR);
        given(httpServiceCallMock.getMonoObject(eq("getMarketChartByIdCurrencyDaysMock"
                + filterDTO.getUrlFilterString()), eq(MarketChartById.class))).willReturn(Mono.error(expectedError));

        Mono<MarketChartById> actualErrorException = coinsGeckoService.getMarketChartById(filterDTO);

        CoinsTestUtils.assertService5xxServerError(actualErrorException,
                "An expected error occurred",
                ErrorTypesEnum.GECKO_SERVER_ERROR);

        verify(externalServerConfigMock, times(1)).getMarketChartCoin();
        verify(httpServiceCallMock).getMonoObject("getMarketChartByIdCurrencyDaysMock" +
                filterDTO.getUrlFilterString(), MarketChartById.class);
    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service of Market Chart Range By Coin ID and Currency and range of dates")
    void whenGetMarketChargeRangeById_ThenItShouldCallAndFetchSuccessfully() {
        MarketChargeRangeById expectedObject = Instancio.create(MarketChargeRangeById.class);
        MarketChargeRangeDTO filterDTO = Instancio.create(MarketChargeRangeDTO.class);
        given(httpServiceCallMock.getMonoObject(eq("getMarketChartRangeByIdCurrencyAndRangeDate" +
                filterDTO.getUrlFilterString()), eq(MarketChargeRangeById.class)))
                .willReturn(Mono.just(expectedObject));

        Mono<MarketChargeRangeById> actualObject = coinsGeckoService.getMarketChargeRangeById(filterDTO);

        CoinsTestUtils.assertMonoSuccess(actualObject, marketChargeRangeById -> {
            assertTrue(Optional.ofNullable(marketChargeRangeById.getMarketCaps()).isPresent(),
                    "Market Caps should not be null");
            assertTrue(Optional.of(marketChargeRangeById.getTotalVolumes())
                            .map(listTotalVolumes -> listTotalVolumes.size()
                                    == expectedObject.getTotalVolumes().size())
                            .orElse(false),
                    "The number of objects in a received list is not equal to the expected one.");
            assertTrue(Optional.ofNullable(marketChargeRangeById.getPrices()).isPresent(),
                    "List of Prices should not be null");
            assertTrue(Optional.of(marketChargeRangeById.getPrices())
                            .map(listPrices -> listPrices.size() == expectedObject.getPrices().size())
                            .orElse(false),
                    "The number of objects in a received list is not equal to the expected one.");
        });

        verify(externalServerConfigMock, times(1)).getMarketChartRange();
        verify(httpServiceCallMock).getMonoObject("getMarketChartRangeByIdCurrencyAndRangeDate" +
                filterDTO.getUrlFilterString(), MarketChargeRangeById.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko of Market Chart Range By Coin ID and Currency and range of dates")
    void whenGetMarketChargeRangeById_ThenItShouldCallAndFetchAndHandleOnStatus4xx() {
        MarketChargeRangeDTO filterDTO = Instancio.create(MarketChargeRangeDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "Failed to retrieve info of market Chart of coin by ID and Currency and ranges of dates",
                "Not Acceptable", ErrorTypesEnum.GECKO_CLIENT_ERROR,
                HttpStatus.NOT_ACCEPTABLE);
        given(httpServiceCallMock.getMonoObject(eq("getMarketChartRangeByIdCurrencyAndRangeDate" +
                filterDTO.getUrlFilterString()), eq(MarketChargeRangeById.class)))
                .willReturn(Mono.error(expectedError));

        Mono<MarketChargeRangeById> actualErrorException = coinsGeckoService.getMarketChargeRangeById(filterDTO);

        CoinsTestUtils.assertService4xxClientError(actualErrorException,
                "Failed to retrieve info of market Chart of coin by ID and Currency and ranges of dates",
                ErrorTypesEnum.GECKO_CLIENT_ERROR);

        verify(externalServerConfigMock, times(1)).getMarketChartRange();
        verify(httpServiceCallMock).getMonoObject("getMarketChartRangeByIdCurrencyAndRangeDate" +
                filterDTO.getUrlFilterString(), MarketChargeRangeById.class);
    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko of Market Chart Range By Coin ID and Currency and range of dates")
    void whenGetMarketChargeRangeById_ThenItShouldCallAndFetchAndHandleOnStatus5xx() {
        MarketChargeRangeDTO filterDTO = Instancio.create(MarketChargeRangeDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "An expected error occurred",
                "Bad Gateway",
                ErrorTypesEnum.GECKO_SERVER_ERROR,
                HttpStatus.BAD_GATEWAY);
        given(httpServiceCallMock.getMonoObject(eq("getMarketChartRangeByIdCurrencyAndRangeDate" +
                filterDTO.getUrlFilterString()), eq(MarketChargeRangeById.class)))
                .willReturn(Mono.error(expectedError));

        Mono<MarketChargeRangeById> actualErrorException = coinsGeckoService.getMarketChargeRangeById(filterDTO);

        CoinsTestUtils.assertService5xxServerError(actualErrorException,
                "An expected error occurred",
                ErrorTypesEnum.GECKO_SERVER_ERROR);

        verify(externalServerConfigMock, times(1)).getMarketChartRange();
        verify(httpServiceCallMock).getMonoObject("getMarketChartRangeByIdCurrencyAndRangeDate" +
                filterDTO.getUrlFilterString(), MarketChargeRangeById.class);
    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service of OHLCB By Coin ID and Currency and range and days")
    void whenGetOHLCById_ThenItShouldCallAndFetchSuccessfully() {
        Collection<String> expectedCollection = Instancio.ofList(String.class).size(7).create();
        OHLCByIdDTO filterDTO = Instancio.create(OHLCByIdDTO.class);
        given(httpServiceCallMock.getFluxObject(eq("getOHLCUrlMock" +
                filterDTO.getUrlFilterString()), eq(String.class)))
                .willReturn(Flux.fromIterable(expectedCollection));

        Flux<String> actualObject = coinsGeckoService.getOHLCById(filterDTO);

        CoinsTestUtils.assertFluxSuccess( actualObject, listOfOHLC -> {
            Assertions.assertEquals(expectedCollection.size(), listOfOHLC.size());
            Assertions.assertTrue(listOfOHLC.containsAll(expectedCollection));
        });

        verify(externalServerConfigMock, times(1)).getOhlcById();
        verify(httpServiceCallMock).getFluxObject("getOHLCUrlMock" +
                filterDTO.getUrlFilterString(), String.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko of OHLC By Coin ID and Currency and Days")
    void whenGetOHLCById_ThenItShouldCallAndFetchAndHandleOnStatus4xx() {
        OHLCByIdDTO filterDTO = Instancio.create(OHLCByIdDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "Failed to retrieve info of OHLC By Coin ID and Currency and Days",
                "Bad Request",
                ErrorTypesEnum.GECKO_CLIENT_ERROR,
                HttpStatus.BAD_REQUEST);
        given(httpServiceCallMock.getFluxObject(eq("getOHLCUrlMock" +
                filterDTO.getUrlFilterString()), eq(String.class))).willReturn(Flux.error(expectedError));

        Flux<String> actualErrorException = coinsGeckoService.getOHLCById(filterDTO);

        CoinsTestUtils.assertService4xxClientError(actualErrorException,
                "Failed to retrieve info of OHLC By Coin ID and Currency and Days",
                ErrorTypesEnum.GECKO_CLIENT_ERROR);

        verify(externalServerConfigMock, times(1)).getOhlcById();
        verify(httpServiceCallMock).getFluxObject("getOHLCUrlMock" +
                filterDTO.getUrlFilterString(), String.class);
    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko of OHLC By Coin ID and Currency and Days")
    void whenGetOHLCById_ThenItShouldCallAndFetchAndHandleOnStatus5xx() {
        OHLCByIdDTO filterDTO = Instancio.create(OHLCByIdDTO.class);
        ApiServerErrorException expectedError = new ApiServerErrorException(
                "An expected error occurred",
                "Internal Server Error",
                ErrorTypesEnum.GECKO_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR);
        given(httpServiceCallMock.getFluxObject(eq("getOHLCUrlMock" +
                filterDTO.getUrlFilterString()), eq(String.class))).willReturn(Flux.error(expectedError));

        Flux<String> actualErrorException = coinsGeckoService.getOHLCById(filterDTO);

        CoinsTestUtils.assertService5xxServerError(actualErrorException,
                "An expected error occurred",
                ErrorTypesEnum.GECKO_SERVER_ERROR);

        verify(externalServerConfigMock, times(1)).getOhlcById();
        verify(httpServiceCallMock).getFluxObject("getOHLCUrlMock" +
                filterDTO.getUrlFilterString(), String.class);
    }

}