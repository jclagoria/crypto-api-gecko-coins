package ar.com.api.gecko.coins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class TickerByIdDTO implements IFilterDTO {

    @NotBlank(message = "Coin cannot be blanc.")
    @NotEmpty(message = "Coin cannot be empty.")
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
                .append("&page=").append(page.orElse("1"));

        this.exchangeIds.ifPresent(exchangeIds ->
                urlStringBuilder.append("&exchange_ids=")
                        .append(exchangeIds));
        this.includeExchangeLogo.ifPresent(includeExchangeLogo ->
                urlStringBuilder.append("&exchange_ids=")
                        .append(includeExchangeLogo));
        this.depth.ifPresent(depth ->
                urlStringBuilder.append("&depth=").append(depth));

        return urlStringBuilder.toString();
    }


}
