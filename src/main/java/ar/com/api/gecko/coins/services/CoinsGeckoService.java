package ar.com.api.gecko.coins.services;

import ar.com.api.gecko.coins.dto.*;
import ar.com.api.gecko.coins.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class CoinsGeckoService {

    @Value("${api.coin}")
    private String URL_COIN;

    @Value("${api.coinList}")
    private String URL_COINS_LIST;

    @Value("${api.markets}")
    private String URL_MARKETS_LIST;

    @Value("${api.tickersById}")
    private String URL_TICKERS_BY_ID;

    @Value("${api.historyCoin}")
    private String URL_HISTORY_COIN;

    @Value("${api.marketChartCoin}")
    private String URL_MARKET_CHART;

    @Value("${api.marketChartRange}")
    private String URL_MARKET_CHART_RANGE;

    @Value("${api.ohlcById}")
    private String URL_OHLC_BY_ID;

    private WebClient webClient;

    public CoinsGeckoService(WebClient wClient) {
        this.webClient = wClient;
    }

    public Flux<CoinBase> getListOfCoins() {

        log.info("Service -> getListOfCoins");

        return webClient
                .get()
                .uri(URL_COINS_LIST)
                .retrieve()
                .bodyToFlux(CoinBase.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<Market> getListOfMarkets(MarketDTO marketFilter) {

        log.info("Service -> getListOfMarkets");

        return webClient
                .get()
                .uri(URL_MARKETS_LIST + marketFilter.getUrlFilterString())
                .retrieve()
                .bodyToFlux(Market.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<CoinInfo> getCoinById(CoinFilterDTO idFilter) {

        log.info("Service -> getCoinById");

        return webClient
                .get()
                .uri(URL_COIN + idFilter.getUrlFilterString())
                .retrieve()
                .bodyToFlux(CoinInfo.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<CoinTickerById> getTickerById(TickerByIdDTO tickerByIdDTO) {

        log.info("Service -> getTickerById");

        String idCoin = String.format(URL_TICKERS_BY_ID, tickerByIdDTO.getIdCoin());

        return webClient
                .get()
                .uri(idCoin
                        + tickerByIdDTO.getUrlFilterString())
                .retrieve()
                .bodyToFlux(CoinTickerById.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<CoinHistoryById> getCoinHistoryByIdAndDate(HistoryCoinDTO coinFilter) {

        log.info("Service -> getCoinHistoryByIdAndDate");

        String urlHistoryCoin = String.format(URL_HISTORY_COIN, coinFilter.getIdCoin());

        return webClient
                .get()
                .uri(urlHistoryCoin + coinFilter.getUrlFilterString())
                .retrieve()
                .bodyToFlux(CoinHistoryById.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<MarketChartById> getMarketChartById(MarketChatBiIdDTO marketChartByIdDTO) {

        log.info("Service -> getMarketChartById");

        String urlMarcketChart = String.format(URL_MARKET_CHART, marketChartByIdDTO.getIdCoin());

        return webClient
                .get()
                .uri(urlMarcketChart + marketChartByIdDTO.getUrlFilterString())
                .retrieve()
                .bodyToFlux(MarketChartById.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<MarketChargeRangeById> getMarketChargeRangeById(MarketChargeRangeDTO marketRangeDTO) {

        log.info("Service -> getMarketChargeRangeById");

        String urlMarketChartRange = String.format(
                URL_MARKET_CHART_RANGE,
                marketRangeDTO.getIdCurrency());

        return webClient
                .get()
                .uri(urlMarketChartRange + marketRangeDTO.getUrlFilterString())
                .retrieve()
                .bodyToFlux(MarketChargeRangeById.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<String> getOHLCById(OHLCByIdDTO ohlcByIdDTO) {

        log.info("Service -> getOHLCById");

        String urlOHCL = String.format(
                URL_OHLC_BY_ID,
                ohlcByIdDTO.getIdCoin());

        return webClient
                .get()
                .uri(urlOHCL + ohlcByIdDTO.getUrlFilterString())
                .retrieve()
                .bodyToFlux(String.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

}