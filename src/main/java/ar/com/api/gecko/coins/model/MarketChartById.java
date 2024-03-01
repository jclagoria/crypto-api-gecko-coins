package ar.com.api.gecko.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketChartById {

    @JsonProperty("prices")
    private List<List<String>> prices;

    @JsonProperty("market_caps")
    private List<List<String>> marketCaps;

    @JsonProperty("total_volumes")
    private List<List<String>> totalVolumes;

}
