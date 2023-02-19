package ar.com.api.gecko.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Roi {
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("times")
 private double times;

 @JsonProperty("currency")
 private String currency;

 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("percentage")
 private double percentage;

}
