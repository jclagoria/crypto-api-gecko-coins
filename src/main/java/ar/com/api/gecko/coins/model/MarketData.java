package ar.com.api.gecko.coins.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketData implements Serializable {
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("current_price")
 private Map<String, Double> currentPrice;
 
 @JsonProperty("total_value_locked")
 private String totalValueLocked;
 
 @JsonProperty("mcap_to_tvl_ratio") 
 private String mcapToTvlRatio;
 
 @JsonProperty("fdv_to_tvl_ratio") 
 private String fdvToTvlRatio;

 @JsonProperty("roi") 
 private Roi roi;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("ath") 
 private Map<String, Double> ath;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("ath_change_percentage") 
 private Map<String, Double> athChangePercentage;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("ath_date") 
 private Map<String, String> athDate;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("atl") 
 private Map<String, Double> atl;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("atl_change_percentage") 
 private Map<String, Double> atlChangePercentage;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("atl_date")
 private Map<String, String> atlDate;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("market_cap") 
 private Map<String, Double> marketCap;
 
 @JsonProperty("market_cap_rank") 
 private long marketCapRank;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("fully_diluted_valuation") 
 private Map<String, Double> fullyDilutedValuation;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("total_volume") 
 private Map<String, Double> totalVolume;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("high_24h") 
 private Map<String, Double> high24h;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("low_24h") 
 private Map<String, Double> low24h;
 
 @JsonProperty("price_change_24h") 
 private double priceChange24h;
 
 @JsonProperty("price_change_percentage_24h") 
 private double priceChangePercentage24h;
 
 @JsonProperty("price_change_percentage_7d") 
 private double priceChangePercentage7d;
 
 @JsonProperty("price_change_percentage_14d") 
 private double priceChangePercentage14d;
 
 @JsonProperty("price_change_percentage_30d") 
 private double priceChangePercentage30d;

 @JsonProperty("price_change_percentage_60d") 
 private double priceChangePercentage60d;
 
 @JsonProperty("price_change_percentage_200d") 
 private double priceChangePercentage200d;
 
 @JsonProperty("price_change_percentage_1y") 
 private double priceChangePercentage1y;

 @JsonProperty("market_cap_change_24h") 
 private double marketCapChange24h;
 
 @JsonProperty("market_cap_change_percentage_24h") 
 private double marketCapChangePercentage24h;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_24h_in_currency") 
 private Map<String, Double> priceChange24hInCurrency;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_percentage_1h_in_currency") 
 private Map<String, Double> priceChangePercentage1hInCurrency;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_percentage_24h_in_currency") 
 private Map<String, Double> priceChangePercentage24hInCurrency;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_percentage_7d_in_currency") 
 private Map<String, Double> priceChangePercentage7dInCurrency;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_percentage_14d_in_currency") 
 private Map<String, Double> priceChangePercentage14dInCurrency;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_percentage_30d_in_currency") 
 private Map<String, Double> priceChangePercentage30dInCurrency;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_percentage_60d_in_currency") 
 private Map<String, Double> priceChangePercentage60dInCurrency;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_percentage_200d_in_currency") 
 private Map<String, Double> priceChangePercentage200dInCurrency;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_percentage_1y_in_currency") 
 private Map<String, Double> priceChangePercentage1yInCurrency;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("market_cap_change_24h_in_currency") 
 private Map<String, Double> marketCapChange24hInCurrency;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("market_cap_change_percentage_24h_in_currency") 
 private Map<String, Double> marketCapChangePercentage24hInCurrency;

 @JsonProperty("total_supply") 
 private double totalSupply;

 @JsonProperty("max_supply") 
 private double maxSupply;

 @JsonProperty("circulating_supply") 
 private double circulatingSupply;

 @JsonProperty("last_updated") 
 private String lastUpdated;

}
