package ar.com.api.gecko.coins.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.com.api.gecko.coins.dto.MarketDTO;
import ar.com.api.gecko.coins.model.CoinBase;
import ar.com.api.gecko.coins.model.Market;
import reactor.core.publisher.Flux;

import lombok.extern.slf4j.Slf4j;

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

 public Flux<Market> getListOfMarkets(MarketDTO marketFilter) {  
  
  StringBuilder filterQuery = new StringBuilder();
  filterQuery.append("?vs_currency=").append(marketFilter.getVsCurrecy().get())
        .append("&order=").append(marketFilter.getOrder().orElse("market_cap_desc"))
        .append("&per_page=").append(marketFilter.getPerPage().orElse("100"))
        .append("&page=").append(marketFilter.getNumPage().orElse("1"))
        .append("&sparkline=").append(marketFilter.getSparkline().orElse("false"));

  if(marketFilter.getIdsCurrency().isPresent())
        filterQuery
                .append("&ids=")
                .append(marketFilter.getIdsCurrency().get());
  
  if(marketFilter.getCategory().isPresent())
        filterQuery
                .append("&category=")
                .append(marketFilter.getCategory().get());

  if(marketFilter.getPriceChangePercentage().isPresent())
        filterQuery
                .append("&price_change_percentage")
                .append(marketFilter.getPriceChangePercentage().get());

  return webClient
          .get()
          .uri(URL_MARKETS_LIST + filterQuery.toString())
          .retrieve()
          .bodyToFlux(Market.class)
          .doOnError(throwable -> log.error("The service is unavailable!", throwable))
          .onErrorComplete();
 }

}
