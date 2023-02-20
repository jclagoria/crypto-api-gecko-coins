package ar.com.api.gecko.coins.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinBase implements Serializable {
 
 private static final long serialVersionUID = 1L;
 
 @JsonProperty("id")
 private String id;

 @JsonProperty("symbol")
 private String symbol;

 @JsonProperty("name")
 private String name;

}
