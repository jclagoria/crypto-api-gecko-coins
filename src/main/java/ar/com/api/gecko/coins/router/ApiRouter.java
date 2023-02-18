package ar.com.api.gecko.coins.router;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

 @Value("${coins.coinList}")
 private String URL_COINS_LIST_API;
 
 
 @Bean
 public RouterFunction<ServerResponse> route(CoinsApiHandler handler) {

  return RouterFunctions
            .route()
            .GET(URL_SERVICE_API + URL_HEALTH_GECKO_API, 
                        handler::getStatusServiceCoinGecko)            
            .GET(URL_SERVICE_API + URL_COINS_LIST_API, handler::getListOfCoins)            
            .build();

 }

}
