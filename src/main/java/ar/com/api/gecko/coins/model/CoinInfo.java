package ar.com.api.gecko.coins.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinInfo extends CoinBase {

 @JsonProperty("asset_platform_id") 
 private String asset_platform_id;
 
 @JsonProperty("platforms") 
 private Map<String, Object> platforms;
 
 @JsonProperty("block_time_in_minutes") 
 private long block_time_in_minutes;
 
 @JsonProperty("hashing_algorithm") 
 private String hashing_algorithm;
 
 @JsonProperty("categories") 
 private List<Object> categories;
 
 @JsonProperty("public_notice") 
 private String public_notice;
 
 @JsonProperty("additional_notices") 
 private List<String> additional_notices;
 
 @JsonProperty("localization") 
 private Map<String, String> localization;
 
 @JsonProperty("description") 
 private Map<String, String> description;
 
 @JsonProperty("links") 
 private Links links;
 
 @JsonProperty("image") 
 private Image image;
 
 @JsonProperty("country_origin") 
 private String country_origin;
 
 @JsonProperty("genesis_date") 
 private String genesis_date;
 
 @JsonProperty("sentiment_votes_up_percentage") 
 private double sentiment_votes_up_percentage;
 
 @JsonProperty("sentiment_votes_down_percentage") 
 private double sentiment_votes_down_percentage;
 
 @JsonProperty("market_cap_rank") 
 private long market_cap_rank;
 
 @JsonProperty("coingecko_rank") 
 private long coingecko_rank;
 
 @JsonProperty("coingecko_score") 
 private double coingecko_score;
 
 @JsonProperty("developer_score") 
 private double developer_score;
 
 @JsonProperty("community_score") 
 private double community_score;
 
 @JsonProperty("liquidity_score") 
 private double liquidity_score;
 
 @JsonProperty("public_interest_score;") 
 private double public_interest_score;
 
 @JsonProperty("market_data") 
 private MarketData market_data;
 
 @JsonProperty("communityData") 
 private CommunityData communityData;

 @JsonProperty("developer_data") 
 private DeveloperData developerData;

 @JsonProperty("public_interest_stats") 
 private PublicInterestStats publicInterestStats;
 
 @JsonProperty("status_updates") 
 private List<Object> status_updates;
 
 @JsonProperty("last_updated") 
 private String last_updated;
 
 @JsonProperty("tickers") 
 private List<Tickers> tickers;

}
