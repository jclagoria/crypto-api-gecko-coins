package ar.com.api.gecko.coins.handler;

import ar.com.api.gecko.coins.dto.*;
import ar.com.api.gecko.coins.enums.ErrorTypesEnum;
import ar.com.api.gecko.coins.exception.ApiClientErrorException;
import ar.com.api.gecko.coins.exception.ApiCustomException;
import ar.com.api.gecko.coins.exception.ApiValidatorException;
import ar.com.api.gecko.coins.model.*;
import ar.com.api.gecko.coins.services.CoinsGeckoService;
import ar.com.api.gecko.coins.handler.utilities.HandleUtils;
import ar.com.api.gecko.coins.validators.ValidatorOfDTOComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class CoinsApiHandler {

    private final CoinsGeckoService coinsGeckoService;
    private final ValidatorOfDTOComponent validatorComponent;

    public Mono<ServerResponse> getListOfCoins(ServerRequest sRequest) {

        log.info("In getListOfCoins {}", sRequest.path());

        return coinsGeckoService.getListOfCoins()
                .collectList()
                .flatMap(listCoins -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(listCoins))
                .doOnSubscribe(subscription -> log.info("Retrieving list of Coins"))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(error -> Mono.error(
                        new ApiCustomException("An expected error occurred in getListOfCoins",
                                HttpStatus.INTERNAL_SERVER_ERROR)
                ));
    }

    public Mono<ServerResponse> getMarkets(ServerRequest sRequest) {
        log.info("Fetching List of Market by Currency from CoinGecko API");

        return Mono.just(sRequest)
                .flatMap(HandleUtils::createMarketDTOFromRequest)
                .flatMap(validatorComponent::validation)
                .flatMapMany(coinsGeckoService::getListOfMarkets)
                .collectList()
                .flatMap(markets -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(markets))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSubscribe(subscription -> log.info("Retrieving list of Markets by Currency"))
                .onErrorResume(error -> Mono
                        .error(new ApiClientErrorException("An expected error occurred in getMarkets",
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorTypesEnum.API_SERVER_ERROR))
                );
    }

    public Mono<ServerResponse> getCoinById(ServerRequest sRequest) {

        log.info("Fetching Coin by ID from  CoinGecko API");

        return Mono.just(sRequest)
                .flatMap(HandleUtils::createCoinFilterDTOFromRequest)
                .flatMap(validatorComponent::validation)
                .flatMap(coinsGeckoService::getCoinById)
                .flatMap(coinInf -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(coinInf))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSubscribe(subscription -> log.info("Retrieving info of Coin by ID"))
                .onErrorResume(error -> Mono
                        .error(new ApiClientErrorException("An expected error occurred in getMarkets",
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorTypesEnum.API_SERVER_ERROR))
                );
    }

    public Mono<ServerResponse> getTickersById(ServerRequest sRequest) {

        log.info("In getTickersById");

        TickerByIdDTO tickerbyIdDto = TickerByIdDTO
                .builder()
                .idCoin(sRequest.pathVariable("idCoin"))
                .order(sRequest.queryParam("order"))
                .page(sRequest.queryParam("page"))
                .exchangeIds(sRequest.queryParam("exchangeIds"))
                .includeExchangeLogo(sRequest.queryParam("includeExchangeLogo"))
                .depth(sRequest.queryParam("depth"))
                .build();

        return ServerResponse
                .ok()
                .body(coinsGeckoService
                                .getTickerById(tickerbyIdDto),
                        Tickers.class);
    }

    public Mono<ServerResponse> getHistoryOfCoin(ServerRequest sRequest) {

        log.info("In getHistoryOfCoin");

        HistoryCoinDTO historyCoinDTO = HistoryCoinDTO
                .builder()
                .idCoin(sRequest.pathVariable("idCoin"))
                .date(sRequest.queryParam("date").get())
                .location(sRequest.queryParam("location"))
                .build();

        return ServerResponse
                .ok()
                .body(coinsGeckoService
                                .getCoinHistoryByIdAndDate(historyCoinDTO),
                        CoinHistoryById.class);
    }

    public Mono<ServerResponse> getMarketChartById(ServerRequest sRequest) {

        log.info("In getMarketChartById");

        MarketChatBiIdDTO marketChartDTO = MarketChatBiIdDTO
                .builder()
                .idCoin(sRequest.pathVariable("idCoin"))
                .vsCurrency(sRequest.queryParam("vsCurrency").get())
                .days(Long.valueOf(sRequest.queryParam("days").get()))
                .interval(sRequest.queryParam("interval"))
                .build();

        return ServerResponse
                .ok()
                .body(
                        coinsGeckoService.getMarketChartById(marketChartDTO),
                        MarketChartById.class);
    }

    public Mono<ServerResponse> getMarketChartRangeById(ServerRequest sRequest) {

        log.info("In getMarketChartRangeById");

        MarketChargeRangeDTO marketChargeRangeDTO = MarketChargeRangeDTO
                .builder()
                .idCurrency(sRequest.pathVariable("idCoin"))
                .vsCurrency(sRequest.queryParam("vsCurrency").get())
                .fromDate(sRequest.queryParam("fromDate").get())
                .toDate(sRequest.queryParam("toDate").get())
                .build();

        return ServerResponse
                .ok()
                .body(
                        coinsGeckoService.getMarketChargeRangeById(marketChargeRangeDTO), MarketChargeRangeById.class);
    }

    public Mono<ServerResponse> getOHLCById(ServerRequest sRequest) {

        log.info("In getOHLCById");

        OHLCByIdDTO ohlcByIdDTO = OHLCByIdDTO
                .builder()
                .idCoin(sRequest.pathVariable("idCoin"))
                .vsCurrency(sRequest.queryParam("vsCurrency").get())
                .days(sRequest.queryParam("days").get())
                .build();

        return ServerResponse
                .ok()
                .body(
                        coinsGeckoService.getOHLCById(ohlcByIdDTO),
                        String.class
                );
    }

}
