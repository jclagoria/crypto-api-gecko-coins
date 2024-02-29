package ar.com.api.gecko.coins.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MarketChargeRangeDTO implements IFilterDTO {

    private String idCurrency;
    private String vsCurrency;
    private String fromDate;
    private String toDate;

    @Override
    public String getUrlFilterString() {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("?vs_currency=").append(vsCurrency)
                .append("&from=").append(fromDate)
                .append("&to=").append(toDate);

        return urlBuilder.toString();
    }

}
