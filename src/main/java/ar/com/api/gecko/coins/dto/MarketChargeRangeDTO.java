package ar.com.api.gecko.coins.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class MarketChargeRangeDTO implements IFilterDTO {

    private String idCurrency;
    private String vsCurrency;
    private String fromDate;
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
