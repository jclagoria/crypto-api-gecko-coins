package ar.com.api.gecko.coins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class MarketChargeRangeDTO implements IFilterDTO {

    @NotBlank(message = "Coin ID Coin cannot be blanc.")
    @NotEmpty(message = "Coin ID Coin cannot be empty.")
    private String idCoin;
    @NotBlank(message = "Currency cannot be blanc.")
    @NotEmpty(message = "Currency cannot be empty.")
    private String vsCurrency;
    @NotBlank(message = "From Date cannot be blanc.")
    @NotEmpty(message = "From Date cannot be empty.")
    private String fromDate;
    @NotBlank(message = "To Date cannot be blanc.")
    @NotEmpty(message = "To Date cannot be empty.")
    private String toDate;
    private Optional<String> precision;

    @Override
    public String getUrlFilterString() {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("?vs_currency=").append(vsCurrency)
                .append("&from=").append(fromDate)
                .append("&to=").append(toDate)
                .append("&precision=").append(this.getPrecision().orElse("18"));

        return urlBuilder.toString();
    }

}
