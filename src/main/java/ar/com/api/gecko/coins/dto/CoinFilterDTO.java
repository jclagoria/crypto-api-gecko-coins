package ar.com.api.gecko.coins.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class CoinFilterDTO implements IFilterDTO {

    private String idCoin;
    private Optional<String> localization;
    private Optional<String> tickers;
    private Optional<String> marketData;
    private Optional<String> communityData;
    private Optional<String> developerData;
    private Optional<String> sparkline;

    @Override
    public String getUrlFilterString() {
        StringBuilder urlService = new StringBuilder();

        urlService
                .append("?localization=").append(localization.orElse("true"))
                .append("&tickers=").append(tickers.orElse("true"))
                .append("&market_data=").append(marketData.orElse("true"))
                .append("&community_data=").append(communityData.orElse("true"))
                .append("&developer_data=").append(developerData.orElse("true"))
                .append("&sparkline=").append(sparkline.orElse("true"));

        return urlService.toString();
    }

}
