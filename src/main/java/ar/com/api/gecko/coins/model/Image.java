package ar.com.api.gecko.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {

 @JsonProperty("thumb") 
 private String thumb;

 @JsonProperty("small")
 private String small;

 @JsonProperty("large")
 private String large;
 
}
