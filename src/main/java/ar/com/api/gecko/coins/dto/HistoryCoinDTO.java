package ar.com.api.gecko.coins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class HistoryCoinDTO implements IFilterDTO {

    @NotBlank(message = "Coin ID cannot be blanc.")
    @NotEmpty(message = "Coin ID cannot be empty.")
    private String idCoin;
    @NotBlank(message = "Date cannot be blanc.")
    @NotEmpty(message = "Date cannot be empty.")
    private String date;
    private Optional<String> location;

    @Override
    public String getUrlFilterString() {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("?date=").append(date);

        this.getLocation().ifPresent(location ->
                urlBuilder.append("&location=").append(location));

        return urlBuilder.toString();
    }

}
