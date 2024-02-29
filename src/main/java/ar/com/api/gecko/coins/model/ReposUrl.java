package ar.com.api.gecko.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReposUrl implements Serializable {

    @ToString.Exclude
    @JsonProperty("github")
    private List<String> github;

    @ToString.Exclude
    @JsonProperty("bitbucket")
    private List<Object> bitbucket;

}
