package ar.com.api.gecko.coins.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class HistoryCoinDTO implements IFilterDTO {

    private String idCoin;
    private String date;
    private Optional<String> location;

    @Override
    public String getUrlFilterString() {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("?date=").append(date);

        if (location.isPresent())
            urlBuilder.append("&location=").append(location.get());

        return urlBuilder.toString();
    }

}
