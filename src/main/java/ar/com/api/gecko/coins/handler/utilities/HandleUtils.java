package ar.com.api.gecko.coins.handler.utilities;

import ar.com.api.gecko.coins.dto.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class HandleUtils {

    public static Mono<MarketDTO> createMarketDTOFromRequest(ServerRequest request) {
        return Mono.just(MarketDTO.builder()
                .vsCurrency(request.queryParam("vsCurrency").get())
                .order(request.queryParam("order"))
                .perPage(request.queryParam("perPage"))
                .numPage(request.queryParam("numPage"))
                .sparkline(request.queryParam("sparkline"))
                .priceChangePercentage(request.queryParam("priceChangePercentage"))
                .idsCurrency(request.queryParam("idsCurrency"))
                .category(request.queryParam("category"))
                .locale(request.queryParam("locale"))
                .precision(request.queryParam("precision"))
                .build());
    }

    public static Mono<CoinFilterDTO> createCoinFilterDTOFromRequest(ServerRequest sRequest) {
        return Mono.just(CoinFilterDTO
                .builder()
                .idCoin(sRequest.pathVariable("idCoin"))
                .localization(sRequest.queryParam("localization"))
                .tickers(sRequest.queryParam("tickers"))
                .marketData(sRequest.queryParam("marketData"))
                .communityData(sRequest.queryParam("communityData"))
                .developerData(sRequest.queryParam("developerData"))
                .sparkline(sRequest.queryParam("sparkline"))
                .build());
    }

    public static Mono<TickerByIdDTO> createTickersByIdDTOFromRequest(ServerRequest sRequest) {
        return Mono.just(TickerByIdDTO
                .builder()
                .idCoin(sRequest.pathVariable("idCoin"))
                .order(sRequest.queryParam("order"))
                .page(sRequest.queryParam("page"))
                .exchangeIds(sRequest.queryParam("exchangeIds"))
                .includeExchangeLogo(sRequest.queryParam("includeExchangeLogo"))
                .depth(sRequest.queryParam("depth"))
                .build());
    }

    public static Mono<HistoryCoinDTO> createHistoryCoinDTOFromRequest(ServerRequest sRequest) {

        return Mono.just(HistoryCoinDTO
                .builder()
                .idCoin(sRequest.pathVariable("idCoin"))
                .date(sRequest.queryParam("date").get())
                .location(sRequest.queryParam("location"))
                .build());
    }

    public static Mono<MarketChatBiIdDTO> createMarketChatBiIdDTOFromRequest(ServerRequest sRequest) {
        return Mono.just(MarketChatBiIdDTO
                .builder()
                .idCoin(sRequest.pathVariable("idCoin"))
                .vsCurrency(sRequest.queryParam("vsCurrency").get())
                .days(sRequest.queryParam("days").get())
                .interval(sRequest.queryParam("interval"))
                .build());
    }

}
