package ar.com.api.gecko.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketShared {
 
 @JsonProperty("name")
 private String name;
 
 @JsonProperty("identifier")
 private String identifier;
 
 @JsonProperty("has_trading_incentive")
 private boolean hasTradingIncentive;

}
