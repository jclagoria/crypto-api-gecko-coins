package ar.com.api.gecko.coins.router;

import ar.com.api.gecko.coins.configuration.ApiServiceConfig;
import ar.com.api.gecko.coins.handler.HealthCoinGeckoApiHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class HealthApiRouter {

    private final ApiServiceConfig apiServiceConfig;

    public HealthApiRouter(ApiServiceConfig serverConfig) {
        this.apiServiceConfig = serverConfig;
    }

    @Bean
    public RouterFunction<ServerResponse> routeHealth(HealthCoinGeckoApiHandler handler) {

        log.info("Router for fetching Health Api Gecko");

        return RouterFunctions
                .route()
                .GET(apiServiceConfig.getBaseURL() +
                                apiServiceConfig.getHealthAPI(),
                        handler::getStatusServiceCoinGecko)
                .build();

    }

}
