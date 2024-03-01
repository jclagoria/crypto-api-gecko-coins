package ar.com.api.gecko.coins.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class TickerByIdDTO implements IFilterDTO {

    private String idCoin;
    private Optional<String> exchangeIds;
    private Optional<String> includeExchangeLogo;
    private Optional<String> page;
    private Optional<String> order;
    private Optional<String> depth;

    @Override
    public String getUrlFilterString() {

        StringBuilder urlStringBuilder = new StringBuilder();
        urlStringBuilder
                .append("?order=").append(order.orElse("trust_score_desc"))
                .append("&page").append(page.orElse("1"));

        if (exchangeIds.isPresent())
            urlStringBuilder
                    .append("&exchange_ids=").append(exchangeIds.get());

        if (includeExchangeLogo.isPresent())
            urlStringBuilder
                    .append("&include_exchange_logo=").append(includeExchangeLogo.get());

        if (depth.isPresent())
            urlStringBuilder.append("&depth=").append(depth.get());

        return urlStringBuilder.toString();
    }


}
