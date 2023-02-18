package ar.com.api.gecko.coins.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinBase {
 
 @JsonProperty("id")
 private String id;

 @JsonProperty("symbol")
 private String symbol;

 @JsonProperty("name")
 private String name;

}
