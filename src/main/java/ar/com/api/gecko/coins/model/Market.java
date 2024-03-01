package ar.com.api.gecko.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Market extends CoinBase {

    @JsonProperty("image")
    private String image;

    @ToString.Exclude
    @JsonProperty("current_price")
    private double currentPrice;

    @ToString.Exclude
    @JsonProperty("market_cap")
    private double marketCap;

    @JsonProperty("market_cap_rank")
    private long marketCapRank;

    @ToString.Exclude
    @JsonProperty("fully_diluted_valuation")
    private double fullyDilutedValuation;

    @ToString.Exclude
    @JsonProperty("total_volume")
    private double totalVolume;

    @ToString.Exclude
    @JsonProperty("high_24h")
    private double high24h;

    @ToString.Exclude
    @JsonProperty("low_24h")
    private double low24h;

    @ToString.Exclude
    @JsonProperty("price_change_24h")
    private double priceChange24h;

    @ToString.Exclude
    @JsonProperty("price_change_percentage_24h")
    private double priceChangePercentage24h;

    @ToString.Exclude
    @JsonProperty("market_cap_change_24h")
    private double marketCapChange24h;

    @ToString.Exclude
    @JsonProperty("market_cap_change_percentage_24h")
    private double marketCapChangePercentage24h;

    @ToString.Exclude
    @JsonProperty("circulating_supply")
    private double circulatingSupply;

    @JsonProperty("total_supply")
    private double totalSupply;

    @JsonProperty("max_supply")
    private double maxSupply;

    @ToString.Exclude
    @JsonProperty("ath")
    private double ath;

    @ToString.Exclude
    @JsonProperty("ath_change_percentage")
    private double athChangePercentage;

    @ToString.Exclude
    @JsonProperty("ath_date")
    private String athDate;

    @ToString.Exclude
    @JsonProperty("atl")
    private double atl;

    @ToString.Exclude
    @JsonProperty("atl_change_percentage")
    private double atlChangePercentage;

    @ToString.Exclude
    @JsonProperty("atl_date")
    private String atlDate;

    @JsonProperty("roi")
    private Roi roi;

    @ToString.Exclude
    @JsonProperty("last_updated")
    private String lastUpdated;

}
