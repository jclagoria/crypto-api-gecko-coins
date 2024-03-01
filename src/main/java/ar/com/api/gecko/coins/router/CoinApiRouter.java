package ar.com.api.gecko.coins.router;

import ar.com.api.gecko.coins.configuration.ApiServiceConfig;
import ar.com.api.gecko.coins.configuration.ExternalServerConfig;
import ar.com.api.gecko.coins.handler.CoinsApiHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CoinApiRouter {

    private final ApiServiceConfig apiServiceConfig;

    public CoinApiRouter(ApiServiceConfig serverConfig) {
        this.apiServiceConfig = serverConfig;
    }

    @Bean
    public RouterFunction<ServerResponse> routeCoin(CoinsApiHandler handler) {

        return RouterFunctions
                .route()
                .GET(apiServiceConfig.getBaseURL() + apiServiceConfig.getCoinById(),
                        handler::getCoinById)
                .GET(apiServiceConfig.getBaseURL() + apiServiceConfig.getCoinList(),
                        handler::getListOfCoins)
                .GET(apiServiceConfig.getBaseURL() + apiServiceConfig.getMarkets(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getMarkets)
                .GET(apiServiceConfig.getBaseURL() + apiServiceConfig.getTickersById(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getTickersById)
                .GET(apiServiceConfig.getBaseURL() + apiServiceConfig.getHistoryCoin(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getHistoryOfCoin)
                .GET(apiServiceConfig.getBaseURL() + apiServiceConfig.getMarketChartCoin(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getMarketChartById)
                .GET(apiServiceConfig.getBaseURL() + apiServiceConfig.getMarketChartRange(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getMarketChartRangeById)
                .GET(apiServiceConfig.getBaseURL() + apiServiceConfig.getOhlcById(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getOHLCById)
                .build();
    }

}
