package ar.com.api.gecko.coins.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.com.api.gecko.coins.dto.Ping;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class CoinGeckoServiceStatus {
 
 private static String URL_PING_SERVICE = "/ping";

 private WebClient webClient;

 public Mono<Ping> getStatusCoinGeckoService() {
  
  log.info("Calling method: ", URL_PING_SERVICE); 

  return webClient
         .get()
         .uri(URL_PING_SERVICE)
         .retrieve()
         .bodyToMono(Ping.class)
         .doOnError(throwable -> log.error("The service is unavailable!", throwable));
 }

}
