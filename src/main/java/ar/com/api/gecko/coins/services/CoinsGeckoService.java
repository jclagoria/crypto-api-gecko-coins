package ar.com.api.gecko.coins.services;

import ar.com.api.gecko.coins.configuration.ExternalServerConfig;
import ar.com.api.gecko.coins.configuration.HttpServiceCall;
import ar.com.api.gecko.coins.dto.*;
import ar.com.api.gecko.coins.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CoinsGeckoService {

    private final ExternalServerConfig externalServerConfig;

    private final HttpServiceCall httpServiceCall;

    public CoinsGeckoService(HttpServiceCall serviceCall, ExternalServerConfig serverConfig) {
        this.httpServiceCall = serviceCall;
        this.externalServerConfig = serverConfig;
    }

    public Flux<CoinBase> getListOfCoins() {
        log.info("Fetch List of Coins Service: {}", externalServerConfig.getCoinList());

        return  httpServiceCall.getFluxObject(externalServerConfig.getCoinList(), CoinBase.class);
    }

    public Mono<CoinInfo> getCoinById(CoinFilterDTO idFilter) {

        log.info("Fetch Service: {}", externalServerConfig.getCoinById() + idFilter.getUrlFilterString());

        return null;
    }

    public Flux<Market> getListOfMarkets(MarketDTO marketFilter) {

        log.info("Fetch Service: {}", externalServerConfig.getMarkets() + marketFilter.getUrlFilterString());

        return null;
    }

    public Flux<CoinTickerById> getTickerById(TickerByIdDTO tickerByIdDTO) {

        String idCoin = String.format(externalServerConfig.getTickersById(), tickerByIdDTO.getIdCoin());

        log.info("Fetch Service: {}", idCoin + tickerByIdDTO.getUrlFilterString());

        return null;
    }

    public Flux<CoinHistoryById> getCoinHistoryByIdAndDate(HistoryCoinDTO coinFilter) {

        String urlHistoryCoin = String.format(externalServerConfig.getHistoryCoin(), coinFilter.getIdCoin());

        log.info("Fetch Service: {}", urlHistoryCoin + coinFilter.getUrlFilterString());

        return null;
    }

    public Flux<MarketChartById> getMarketChartById(MarketChatBiIdDTO marketChartByIdDTO) {

        String urlMarketChart = String.format(externalServerConfig.getMarketChartCoin(),
                marketChartByIdDTO.getIdCoin());

        log.info("Fetch Service: {}", urlMarketChart + marketChartByIdDTO.getUrlFilterString());

        return null;
    }

    public Flux<MarketChargeRangeById> getMarketChargeRangeById(MarketChargeRangeDTO marketRangeDTO) {

        String urlMarketChartRange = String.format(
                externalServerConfig.getMarketChartRange(),
                marketRangeDTO.getIdCurrency());

        log.info("Fetch Service: {}", urlMarketChartRange + marketRangeDTO.getUrlFilterString());

        return null;
    }

    public Flux<String> getOHLCById(OHLCByIdDTO ohlcByIdDTO) {

        String urlOHCL = String.format(
                externalServerConfig.getOhlcById(),
                ohlcByIdDTO.getIdCoin());

        log.info("Fetch Service: {}", urlOHCL + ohlcByIdDTO.getUrlFilterString());

        return null;
    }

}