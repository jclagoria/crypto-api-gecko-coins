package ar.com.api.gecko.coins.services;

import ar.com.api.gecko.coins.configuration.ExternalServerConfig;
import ar.com.api.gecko.coins.dto.*;
import ar.com.api.gecko.coins.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CoinsGeckoService {

    private final ExternalServerConfig externalServerConfig;

    private WebClient webClient;

    public CoinsGeckoService(WebClient wClient, ExternalServerConfig serverConfig) {
        this.webClient = wClient;
        this.externalServerConfig = serverConfig;
    }

    public Mono<CoinInfo> getCoinById(CoinFilterDTO idFilter) {

        log.info("Fetch Service: {}", externalServerConfig.getCoinById());

        return webClient
                .get()
                .uri(externalServerConfig.getCoinById() + idFilter.getUrlFilterString())
                .retrieve()
                .bodyToMono(CoinInfo.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }


    public Flux<CoinBase> getListOfCoins() {

        log.info("Fetch Service: {}", externalServerConfig.getCoinList());

        return webClient
                .get()
                .uri(externalServerConfig.getCoinList())
                .retrieve()
                .bodyToFlux(CoinBase.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<Market> getListOfMarkets(MarketDTO marketFilter) {

        log.info("Fetch Service: {}", externalServerConfig.getMarkets());

        return webClient
                .get()
                .uri(externalServerConfig.getMarkets() + marketFilter.getUrlFilterString())
                .retrieve()
                .bodyToFlux(Market.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<CoinTickerById> getTickerById(TickerByIdDTO tickerByIdDTO) {

        log.info("Fetch Service: {}", externalServerConfig.getTickersById());

        String idCoin = String.format(externalServerConfig.getTickersById(), tickerByIdDTO.getIdCoin());

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

        log.info("Fetch Service: {}", externalServerConfig.getHistoryCoin());

        String urlHistoryCoin = String.format(externalServerConfig.getHistoryCoin(), coinFilter.getIdCoin());

        return webClient
                .get()
                .uri(urlHistoryCoin + coinFilter.getUrlFilterString())
                .retrieve()
                .bodyToFlux(CoinHistoryById.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<MarketChartById> getMarketChartById(MarketChatBiIdDTO marketChartByIdDTO) {

        log.info("Fetch Service: {}", externalServerConfig.getMarketChartCoin());

        String urlMarketChart = String.format(externalServerConfig.getMarketChartCoin(), marketChartByIdDTO.getIdCoin());

        return webClient
                .get()
                .uri(urlMarketChart + marketChartByIdDTO.getUrlFilterString())
                .retrieve()
                .bodyToFlux(MarketChartById.class)
                .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                .onErrorComplete();
    }

    public Flux<MarketChargeRangeById> getMarketChargeRangeById(MarketChargeRangeDTO marketRangeDTO) {

        log.info("Fetch Service: {}", externalServerConfig.getMarketChartRange());

        String urlMarketChartRange = String.format(
                externalServerConfig.getMarketChartRange(),
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

        log.info("Fetch Service: {}", externalServerConfig.getOhlcById());

        String urlOHCL = String.format(
                externalServerConfig.getOhlcById(),
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