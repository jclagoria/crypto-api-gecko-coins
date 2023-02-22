package ar.com.api.gecko.coins.router;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ar.com.api.gecko.coins.handler.CoinsApiHandler;

@Configuration
public class ApiRouter {

 @Value("${coins.baseURL}") 
 private String URL_SERVICE_API;

 @Value("${coins.healthAPI}") 
 private String URL_HEALTH_GECKO_API;

 @Value("${coins.list}")
 private String URL_COINS_LIST_API;
 
 @Value("${coins.market}")
 private String URL_COINS_MARKETS_API;
 
 @Value("${coins.tickers}")
 private String URL_COINS_TICKERS_BY_ID;

 @Value("${coins.history}")
 private String ULR_COINS_HISTORY_BY_ID;

 @Value("${coins.marketChart}")
 private String ULR_MARKET_CHART_BY_ID;

 @Value("${coins.rangeById}")
 private String URL_MARKET_CHART_RANGE_BY_ID;

 @Value("${coins.ohlcById}")
 private String URL_OHLC_BY_ID; 

 @Bean
 public RouterFunction<ServerResponse> route(CoinsApiHandler handler) {

  return RouterFunctions
            .route()
            .GET(URL_SERVICE_API + URL_HEALTH_GECKO_API, 
                         handler::getStatusServiceCoinGecko)            
            .GET(URL_SERVICE_API + URL_COINS_LIST_API, 
                         handler::getListOfCoins)            
            .GET(URL_SERVICE_API + URL_COINS_MARKETS_API, 
                         RequestPredicates.accept(MediaType.APPLICATION_JSON),                 
                         handler::getMarkets)
            .GET(URL_SERVICE_API + "/{idCoin}", 
                         RequestPredicates.accept(MediaType.APPLICATION_JSON),
                         handler::getCoinById)
            .GET(URL_SERVICE_API + URL_COINS_TICKERS_BY_ID, 
                         RequestPredicates.accept(MediaType.APPLICATION_JSON),
                         handler::getTickersById)
            .GET(URL_SERVICE_API + ULR_COINS_HISTORY_BY_ID, 
                         RequestPredicates.accept(MediaType.APPLICATION_JSON),
                         handler::getHistoryOfCoin)
            .GET(URL_SERVICE_API + ULR_MARKET_CHART_BY_ID,
                         RequestPredicates.accept(MediaType.APPLICATION_JSON),
                         handler::getMarketChartById)
            .GET(URL_SERVICE_API + URL_MARKET_CHART_RANGE_BY_ID, 
                         RequestPredicates.accept(MediaType.APPLICATION_JSON),
                         handler::getMarketChartRangeById)
            .GET(URL_SERVICE_API + URL_OHLC_BY_ID, 
                         RequestPredicates.accept(MediaType.APPLICATION_JSON),
                         handler::getOHLCById)
            .build();
 }

}
