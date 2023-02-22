package ar.com.api.gecko.coins.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ar.com.api.gecko.coins.dto.CoinFilterDTO;
import ar.com.api.gecko.coins.dto.HistoryCoinDTO;
import ar.com.api.gecko.coins.dto.MarketChargeRangeDTO;
import ar.com.api.gecko.coins.dto.MarketChatBiIdDTO;
import ar.com.api.gecko.coins.dto.MarketDTO;
import ar.com.api.gecko.coins.dto.TickerByIdDTO;
import ar.com.api.gecko.coins.model.CoinBase;
import ar.com.api.gecko.coins.model.CoinHistoryById;
import ar.com.api.gecko.coins.model.CoinInfo;
import ar.com.api.gecko.coins.model.Market;
import ar.com.api.gecko.coins.model.MarketChargeRangeById;
import ar.com.api.gecko.coins.model.MarketChartById;
import ar.com.api.gecko.coins.model.Ping;
import ar.com.api.gecko.coins.model.Tickers;
import ar.com.api.gecko.coins.services.CoinGeckoServiceStatus;
import ar.com.api.gecko.coins.services.CoinsGeckoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

          log.info("In getListOfCoins");
     
          return ServerResponse
               .ok()
               .body(
                    coinsGeckoService.getListOfCoins(), 
                    CoinBase.class);
     }

     public Mono<ServerResponse> getMarkets(ServerRequest sRequest) {

          log.info("In getMarkets");

          MarketDTO marketFilter = MarketDTO
                    .builder()
                    .vsCurrecy(sRequest.queryParam("vsCurrency"))
                    .order(sRequest.queryParam("order"))
                    .perPage(sRequest.queryParam("perPage"))
                    .numPage(sRequest.queryParam("numPage"))
                    .sparkline(sRequest.queryParam("sparkline"))
                    .priceChangePercentage(sRequest.queryParam("priceChangePercentage"))
                    .idsCurrency(sRequest.queryParam("idsCurrency"))
                    .category(sRequest.queryParam("category"))
                    .build();
                    
          return ServerResponse
               .ok()
               .body(
                     coinsGeckoService.getListOfMarkets(marketFilter),
                     Market.class);
     }

     public Mono<ServerResponse> getCoinById(ServerRequest sRequest) {
          
          log.info("In getCoinById");

          CoinFilterDTO coinFilter = CoinFilterDTO
                              .builder()
                              .idCoin(sRequest.pathVariable("idCoin"))
                              .localization(sRequest.queryParam("localization")) 
                              .tickers(sRequest.queryParam("tickers"))
                              .marketData(sRequest.queryParam("marketData"))
                              .communityData(sRequest.queryParam("communityData"))
                              .developerData(sRequest.queryParam("developerData"))
                              .sparkline(sRequest.queryParam("sparkline"))
                              .build();
                              

          return ServerResponse
                    .ok()
                    .body(
                         coinsGeckoService.getCoinById(coinFilter), 
                         CoinInfo.class);
     }


     public Mono<ServerResponse> getTickersById(ServerRequest sRequest) {

          log.info("In getTickersById");

          TickerByIdDTO tickerbyIdDto = TickerByIdDTO
                              .builder()
                              .idCoin(sRequest.pathVariable("idCoin"))
                              .order(sRequest.queryParam("order"))
                              .page(sRequest.queryParam("page"))
                              .exchangeIds(sRequest.queryParam("exchangeIds"))
                              .includeExchangeLogo(sRequest.queryParam("includeExchangeLogo"))
                              .depth(sRequest.queryParam("depth"))
                              .build();

          return ServerResponse
                    .ok()
                    .body(coinsGeckoService
                              .getTickerById(tickerbyIdDto), 
                         Tickers.class);
     }

     public Mono<ServerResponse> getHistoryOfCoin(ServerRequest sRequest) {

          log.info("In getHistoryOfCoin");

          HistoryCoinDTO historyCoinDTO = HistoryCoinDTO
                              .builder()
                              .idCoin(sRequest.pathVariable("idCoin"))
                              .date(sRequest.queryParam("date").get())
                              .location(sRequest.queryParam("location"))
                              .build();

          return ServerResponse
                    .ok()
                    .body(coinsGeckoService
                              .getCoinHistoryByIdAndDate(historyCoinDTO), 
                         CoinHistoryById.class);
     }

     public Mono<ServerResponse> getMarketChartById(ServerRequest sRequest) {

          log.info("In getMarketChartById");

          MarketChatBiIdDTO marketChartDTO = MarketChatBiIdDTO
                              .builder()
                              .idCoin(sRequest.pathVariable("idCoin"))
                              .vsCurrency(sRequest.queryParam("vsCurrency").get())
                              .days(Long.valueOf(sRequest.queryParam("days").get()))
                              .interval(sRequest.queryParam("interval"))
                              .build();
          
          return ServerResponse
                    .ok()
                    .body(
                         coinsGeckoService.getMarketChartById(marketChartDTO), 
                         MarketChartById.class);
     }

     public Mono<ServerResponse> getMarketChartRangeById(ServerRequest sRequest) {

          log.info("In getMarketChartRangeById");

          MarketChargeRangeDTO marketChargeRangeDTO = MarketChargeRangeDTO
                                   .builder()
                                   .idCurrency(sRequest.pathVariable("idCoin"))
                                   .vsCurrency(sRequest.queryParam("vsCurrency").get())
                                   .fromDate(sRequest.queryParam("fromDate").get())
                                   .toDate(sRequest.queryParam("toDate").get())
                                   .build();

          return ServerResponse
                    .ok()
                    .body(
                         coinsGeckoService.getMarketChargeRangeById(marketChargeRangeDTO), MarketChargeRangeById.class);

     }

}
