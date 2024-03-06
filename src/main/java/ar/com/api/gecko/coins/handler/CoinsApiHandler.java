package ar.com.api.gecko.coins.handler;

import ar.com.api.gecko.coins.enums.ErrorTypesEnum;
import ar.com.api.gecko.coins.exception.ApiClientErrorException;
import ar.com.api.gecko.coins.handler.utilities.MapperHandler;
import ar.com.api.gecko.coins.services.CoinsGeckoService;
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
                        new ApiClientErrorException("An expected error occurred in getListOfCoins",
                                HttpStatus.INTERNAL_SERVER_ERROR, ErrorTypesEnum.API_SERVER_ERROR)
                ));
    }

    public Mono<ServerResponse> getMarkets(ServerRequest sRequest) {
        log.info("Fetching List of Market by Currency from CoinGecko API");

        return Mono.just(sRequest)
                .flatMap(MapperHandler::createMarketDTOFromRequest)
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
        log.info("Fetching Coin by ID from CoinGecko API");

        return Mono.just(sRequest)
                .flatMap(MapperHandler::createCoinFilterDTOFromRequest)
                .flatMap(validatorComponent::validation)
                .flatMap(coinsGeckoService::getCoinById)
                .flatMap(coinInf -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(coinInf))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSubscribe(subscription -> log.info("Retrieving info of Coin by ID"))
                .onErrorResume(error -> Mono
                        .error(new ApiClientErrorException("An expected error occurred in getCoinById",
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorTypesEnum.API_SERVER_ERROR))
                );
    }

    public Mono<ServerResponse> getTickersById(ServerRequest sRequest) {
        log.info("Fetching Tickers by ID from In CoinGecko API");

        return Mono.just(sRequest)
                .flatMap(MapperHandler::createTickersByIdDTOFromRequest)
                .flatMap(validatorComponent::validation)
                .flatMap(coinsGeckoService::getTickerById)
                .flatMap(tickerById -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tickerById))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSubscribe(subscription -> log.info("Retrieving info of Tickers by ID"))
                .onErrorResume(error -> Mono
                        .error(new ApiClientErrorException("An expected error occurred in getTickersById",
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorTypesEnum.API_SERVER_ERROR))
                );
    }

    public Mono<ServerResponse> getHistoryOfCoin(ServerRequest sRequest) {
        log.info("Fetching History by ID Coin and Date from CoinGecko API");

        return Mono.just(sRequest)
                .flatMap(MapperHandler::createHistoryCoinDTOFromRequest)
                .flatMap(validatorComponent::validation)
                .flatMap(coinsGeckoService::getCoinHistoryByIdAndDate)
                .flatMap(historyCoinID -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(historyCoinID))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSubscribe(subscription ->
                        log.info("Retrieving info history of Coin by ID and Date"))
                .onErrorResume(error -> Mono
                        .error(new ApiClientErrorException("An expected error occurred in getHistoryOfCoin",
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorTypesEnum.API_SERVER_ERROR))
                );
    }

    public Mono<ServerResponse> getMarketChartById(ServerRequest sRequest) {
        log.info("Fetching Market Chart by ID Coin and Currency and Date from CoinGecko API");

        return Mono.just(sRequest)
                .flatMap(MapperHandler::createMarketChatBiIdDTOFromRequest)
                .flatMap(validatorComponent::validation)
                .flatMap(coinsGeckoService::getMarketChartById)
                .flatMap(marketChartById -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(marketChartById))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSubscribe(subscription -> log.info("Retrieving info Market Chart by Ic and Currency and Days"))
                .onErrorResume(error -> Mono
                        .error(new ApiClientErrorException("An expected error occurred in getMarketChartById",
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorTypesEnum.API_SERVER_ERROR))
                );
    }

    public Mono<ServerResponse> getMarketChartRangeById(ServerRequest sRequest) {
        log.info("Fetching Market Chart Range by ID Coin and Currency and Range Dates from CoinGecko API");

        return Mono.just(sRequest)
                .flatMap(MapperHandler::createMarketChargeRangeDTOFromRequest)
                .flatMap(validatorComponent::validation)
                .flatMap(coinsGeckoService::getMarketChargeRangeById)
                .flatMap(marketChargeRangeById -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(marketChargeRangeById))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSubscribe(subscription ->
                        log.info("Market Chart Range by ID Coin and Currency and Range Dates"))
                .onErrorResume(error -> Mono
                        .error(new ApiClientErrorException("An expected error occurred in getMarketChartRangeById",
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorTypesEnum.API_SERVER_ERROR))
                );
    }

    public Mono<ServerResponse> getOHLCById(ServerRequest sRequest) {
        log.info("Fetching List of Strings by Coin ID and Currency and days");

        return Mono.just(sRequest)
                .flatMap(MapperHandler::createOHLCByIdDTOFromRequest)
                .flatMap(validatorComponent::validation)
                .flatMapMany(coinsGeckoService::getOHLCById)
                .collectList()
                .flatMap(listOHLC -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(listOHLC.get(0)))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSubscribe(subscription ->
                        log.info("Retrieving list of OHLC by Coin ID and Currency and days"))
                .onErrorResume(error -> Mono
                        .error(new ApiClientErrorException("An expected error occurred in getOHLCById",
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorTypesEnum.API_SERVER_ERROR))
                );
    }

}
