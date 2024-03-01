package ar.com.api.gecko.coins.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class MarketDTO implements IFilterDTO {

    private Optional<String> vsCurrency;
    private Optional<String> order;
    private Optional<String> perPage;
    private Optional<String> numPage;
    private Optional<String> sparkline;
    private Optional<String> priceChangePercentage;
    private Optional<String> idsCurrency;
    private Optional<String> category;

    @Override
    public String getUrlFilterString() {

        StringBuilder filterQuery = new StringBuilder();
        filterQuery.append("?vs_currency=").append(this.getVsCurrency().get())
                .append("&order=").append(this.getOrder().orElse("market_cap_desc"))
                .append("&per_page=").append(this.getPerPage().orElse("100"))
                .append("&page=").append(this.getNumPage().orElse("1"))
                .append("&sparkline=").append(this.getSparkline().orElse("false"));

        if (this.getIdsCurrency().isPresent())
            filterQuery
                    .append("&ids=")
                    .append(this.getIdsCurrency().get());

        if (this.getCategory().isPresent())
            filterQuery
                    .append("&category=")
                    .append(this.getCategory().get());

        if (this.getPriceChangePercentage().isPresent())
            filterQuery
                    .append("&price_change_percentage")
                    .append(this.getPriceChangePercentage().get());

        return filterQuery.toString();
    }


}
