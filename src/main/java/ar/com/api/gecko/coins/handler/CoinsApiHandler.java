package ar.com.api.gecko.coins.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ar.com.api.gecko.coins.dto.CoinBase;
import ar.com.api.gecko.coins.dto.Ping;
import ar.com.api.gecko.coins.services.CoinGeckoServiceStatus;
import ar.com.api.gecko.coins.services.CoinsGeckoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class CoinsApiHandler {
     
     private CoinGeckoServiceStatus serviceStatus;

     private CoinsGeckoService coinsGeckoService;

     public Mono<ServerResponse> getStatusServiceCoinGecko(ServerRequest serverRequest) {

     log.info("In getStatusServiceCoinGecko");

     return ServerResponse
                    .ok()
                    .body(
                         serviceStatus.getStatusCoinGeckoService(), 
                         Ping.class);
     }

     public Mono<ServerResponse> getListOfCoins(ServerRequest sRequest) {
     
          return ServerResponse
               .ok().body(coinsGeckoService.getListOfCoins(), CoinBase.class);
     }
}
