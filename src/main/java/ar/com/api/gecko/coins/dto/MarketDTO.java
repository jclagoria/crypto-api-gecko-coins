package ar.com.api.gecko.coins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class MarketDTO implements IFilterDTO {

    @NotBlank(message = "Currency cannot be blanc.")
    @NotEmpty(message = "Currency cannot be empty.")
    private String vsCurrency;
    private Optional<String> order;
    private Optional<String> perPage;
    private Optional<String> numPage;
    private Optional<String> sparkline;
    private Optional<String> priceChangePercentage;
    private Optional<String> idsCurrency;
    private Optional<String> category;
    private Optional<String> locale;
    private Optional<String> precision;

    @Override
    public String getUrlFilterString() {

        StringBuilder filterQuery = new StringBuilder();
        filterQuery.append("?vs_currency=").append(this.getVsCurrency())
                .append("&order=").append(this.getOrder().orElse("market_cap_desc"))
                .append("&per_page=").append(this.getPerPage().orElse("100"))
                .append("&page=").append(this.getNumPage().orElse("1"))
                .append("&sparkline=").append(this.getSparkline().orElse("false"))
                .append("&locale=").append(this.getLocale().orElse("en"))
                .append("&precision=").append(this.getPrecision().orElse("18"));

        this.getIdsCurrency().ifPresent(ids -> filterQuery.append("&ids=").append(ids));
        this.getCategory().ifPresent(category -> filterQuery.append("&category=").append(category));
        this.getPriceChangePercentage().ifPresent(price ->
                filterQuery.append("&price_change_percentage=").append(price));

        return filterQuery.toString();
    }


}
