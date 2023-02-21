package ar.com.api.gecko.coins.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinHistoryById extends CoinBase {
 
 @JsonProperty("localization")
 private Map<String, String> localization;

 @JsonProperty("image") 
 private Image image;

 @JsonProperty("market_data") 
 private MarketData marketData;

 @JsonProperty("ommunity_data") 
 private CommunityData communityData;

 @JsonProperty("developer_data") 
 private DeveloperData developerData;

 @JsonProperty("public_interest_stats") 
 private PublicInterestStats publicInterestStats;

}
