package ar.com.api.gecko.coins.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.com.api.gecko.coins.dto.CoinBase;
import ar.com.api.gecko.coins.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CoinsGeckoService {

 @Value("${api.coinList}")
 private String URL_COINS_LIST; 

 private WebClient webClient;

 public CoinsGeckoService(WebClient wClient) {
  this.webClient = wClient;
 }

 public Flux<CoinBase> getListOfCoins(){

  return webClient
            .get()
            .uri(URL_COINS_LIST)
            .retrieve()
            /*.onStatus(
                 status -> status.value() == HttpStatus.METHOD_NOT_ALLOWED.value(), 
                 response -> 
                   Mono.error(
                     new ServiceException("Method not allowed. Please check the URL.", 
                                           response.statusCode().value()))) */
            .bodyToFlux(CoinBase.class)            
            /* .doOnError(throwable -> log.error("The service is unavailable!", throwable))
            .onErrorResume(error -> Flux.empty())*/;
 }


}
