package ar.com.api.gecko.coins.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.com.api.gecko.coins.dto.CoinFilterDTO;
import ar.com.api.gecko.coins.dto.MarketDTO;
import ar.com.api.gecko.coins.model.CoinBase;
import ar.com.api.gecko.coins.model.CoinInfo;
import ar.com.api.gecko.coins.model.Market;
import reactor.core.publisher.Flux;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CoinsGeckoService {

 @Value("${api.coin}")     
 private String URL_COIN;
      
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

 public Flux<Market> getListOfMarkets(MarketDTO marketFilter) {  
  
  return webClient
          .get()
          .uri(URL_MARKETS_LIST + marketFilter.getUrlFilterString())
          .retrieve()
          .bodyToFlux(Market.class)
          .doOnError(throwable -> log.error("The service is unavailable!", throwable))
          .onErrorComplete();
 }

 public Flux<CoinInfo> getCoinById(CoinFilterDTO idFilter) {

      return webClient
                  .get()
                  .uri(URL_COIN + idFilter.getUrlFilterString())
                  .retrieve()
                  .bodyToFlux(CoinInfo.class)
                  .doOnError(throwable -> log.error("The service is unavailable!", throwable))
                  .onErrorComplete();
 }


}
