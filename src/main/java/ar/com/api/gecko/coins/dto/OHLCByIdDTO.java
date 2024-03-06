package ar.com.api.gecko.coins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class OHLCByIdDTO implements IFilterDTO {

    @NotBlank(message = "Coin Id cannot be blanc.")
    @NotEmpty(message = "Coin Id cannot be empty.")
    private String idCoin;
    @NotBlank(message = "Currency cannot be blanc.")
    @NotEmpty(message = "Currency cannot be empty.")
    private String vsCurrency;
    @NotBlank(message = "Days cannot be blanc.")
    @NotEmpty(message = "Days cannot be empty.")
    private String days;
    private Optional<String> precision;

    @Override
    public String getUrlFilterString() {

        StringBuilder uBuilder = new StringBuilder();
        uBuilder.append("?vs_currency=").append(vsCurrency)
                .append("&days=").append(days)
                .append("&precision=").append(precision.orElse("18"));

        return uBuilder.toString();
    }

}
