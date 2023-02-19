package ar.com.api.gecko.coins.dto;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MarketDTO {
 
 private Optional<String> vsCurrecy;
 private Optional<String> order;
 private Optional<String> perPage;
 private Optional<String> numPage;
 private Optional<String> sparkline;
 private Optional<String> priceChangePercentage;
 private Optional<String> idsCurrency;
 private Optional<String> category;

}
