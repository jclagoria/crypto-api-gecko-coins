package ar.com.api.gecko.coins.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.com.api.gecko.coins.exception.ServiceException;
import ar.com.api.gecko.coins.model.CoinBase;
import ar.com.api.gecko.coins.model.Market;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;


@Service
@Slf4j
public class CoinsGeckoService {

 @Value("${api.coinList}")
 private String URL_COINS_LIST; 

 @Value("${api.markets}")
 private String URL_MARKETS_LIST;

 private WebClient webClient;

 public CoinsGeckoService(WebClient wClient) {
  this.webClient = wClient;
 }

 public Flux<CoinBase> getListOfCoins(){

  return webClient
            .get()
            .uri(URL_COINS_LIST)
            .retrieve()            
            .bodyToFlux(CoinBase.class)            
            .doOnError(throwable -> log.error("The service is unavailable!", throwable))
            .onErrorComplete();
 }

 /**
  * vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false
  * @return
  */


 public Flux<Market> getListOfMarkets(
                String vsCurrency) {

  return webClient
          .get()
          .uri(URL_MARKETS_LIST + "?vs_currency="+vsCurrency+"&order=market_cap_desc&per_page=250&page=1&sparkline=false")
          .retrieve()
          .bodyToFlux(Market.class)
          .doOnError(throwable -> log.error("The service is unavailable!", throwable))
          .onErrorComplete();
 }


}
