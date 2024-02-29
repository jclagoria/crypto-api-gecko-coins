package ar.com.api.gecko.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicInterestStats implements Serializable {

    @JsonProperty("alexa_rank")
    private double alexaRank;

    @JsonProperty("bing_matches")
    private double bingMatches;

}
