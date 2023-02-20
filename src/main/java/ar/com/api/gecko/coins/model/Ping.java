package ar.com.api.gecko.coins.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ping implements Serializable {
 
 @JsonProperty("gecko_says")
 private String geckoSays;

}
