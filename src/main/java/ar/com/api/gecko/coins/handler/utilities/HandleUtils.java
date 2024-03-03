package ar.com.api.gecko.coins.handler.utilities;

import ar.com.api.gecko.coins.dto.CoinFilterDTO;
import ar.com.api.gecko.coins.dto.MarketDTO;
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

}