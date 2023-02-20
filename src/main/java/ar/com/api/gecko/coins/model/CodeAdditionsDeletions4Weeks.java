package ar.com.api.gecko.coins.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeAdditionsDeletions4Weeks implements Serializable { 
 
 @JsonProperty("additions")
 private double additions;

 @JsonProperty("deletions")
 private double deletions;

}
