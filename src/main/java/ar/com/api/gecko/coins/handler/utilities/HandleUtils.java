package ar.com.api.gecko.coins.handler.utilities;

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

}
