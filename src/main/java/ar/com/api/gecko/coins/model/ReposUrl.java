package ar.com.api.gecko.coins.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReposUrl {
 
 @ToString.Exclude
 @JsonProperty("github")
 private List<String> github;

 @ToString.Exclude
 @JsonProperty("bitbucket")
 private List<Object> bitbucket;

}
