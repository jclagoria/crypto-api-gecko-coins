package ar.com.api.gecko.coins.router;

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

    private final ExternalServerConfig externalServerConfig;

    public CoinApiRouter(ExternalServerConfig serverConfig) {
        this.externalServerConfig = serverConfig;
    }

    @Bean
    public RouterFunction<ServerResponse> routeCoin(CoinsApiHandler handler) {

        return RouterFunctions
                .route()
                .GET(externalServerConfig.getBaseURL() + externalServerConfig.getCoinById(),
                        handler::getCoinById)
                .GET(externalServerConfig.getBaseURL() + externalServerConfig.getList(),
                        handler::getListOfCoins)
                .GET(externalServerConfig.getBaseURL() + externalServerConfig.getMarkets(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getMarkets)
                .GET(externalServerConfig.getBaseURL() + externalServerConfig.getTickers(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getTickersById)
                .GET(externalServerConfig.getBaseURL() + externalServerConfig.getHistory(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getHistoryOfCoin)
                .GET(externalServerConfig.getBaseURL() + externalServerConfig.getMarketChart(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getMarketChartById)
                .GET(externalServerConfig.getBaseURL() + externalServerConfig.getRangeById(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getMarketChartRangeById)
                .GET(externalServerConfig.getBaseURL() + externalServerConfig.getOhlcById(),
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        handler::getOHLCById)
                .build();
    }

}
