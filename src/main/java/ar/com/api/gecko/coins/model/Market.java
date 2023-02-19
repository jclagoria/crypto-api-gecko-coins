package ar.com.api.gecko.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Market extends CoinBase {
 
 @EqualsAndHashCode.Exclude
 @JsonProperty("image") 
 private String image; 

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @JsonProperty("current_price")
 private double currentPrice;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("market_cap")
 private double marketCap;
 
 @JsonProperty("market_cap_rank")
 private long marketCapRank;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("fully_diluted_valuation")
 private double fullyDilutedValuation;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("total_volume")
 private double totalVolume;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("high_24h")
 private double high24h;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("low_24h")
 private double low24h;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_24h")
 private double priceChange24h;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("price_change_percentage_24h")
 private double priceChangePercentage24h;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("market_cap_change_24h")
 private double marketCapChange24h;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("market_cap_change_percentage_24h")
 private double marketCapChangePercentage24h;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("circulating_supply")
 private double circulatingSupply;

 @JsonProperty("total_supply")
 private double totalSupply;

 @JsonProperty("max_supply")
 private double maxSupply;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("ath")
 private double ath;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("ath_change_percentage")
 private double athChangePercentage;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("ath_date")
 private String athDate;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("atl")
 private double atl;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("atl_change_percentage")
 private double atlChangePercentage;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("atl_date")
 private String atlDate;

 @JsonProperty("roi")
 private Roi roi;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("last_updated")
 private String lastUpdated;

}
