package ar.com.api.gecko.coins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class MarketChatBiIdDTO implements IFilterDTO {

    @NotBlank(message = "Id Coin cannot be blanc.")
    @NotEmpty(message = "Id Coin cannot be empty.")
    private String idCoin;
    @NotBlank(message = "Currency cannot be blanc.")
    @NotEmpty(message = "Currency cannot be empty.")
    private String vsCurrency;
    @NotBlank(message = "Day cannot be blanc.")
    @NotEmpty(message = "Day cannot be empty.")
    private String days;
    private Optional<String> interval;

    @Override
    public String getUrlFilterString() {

        StringBuilder filterQuery = new StringBuilder();
        filterQuery.append("?vs_currency=").append(vsCurrency)
                .append("&days=").append(days);

        this.getInterval().ifPresent(interval ->
                filterQuery.append("&interval=").append(interval));

        return filterQuery.toString();
    }

}
