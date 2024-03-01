package ar.com.api.gecko.coins.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class ExternalServerConfig {

    private String urlCoinGecko;
    private String ping;
    private String coinById;
    private String coinList;
    private String markets;
    private String tickersById;
    private String historyCoin;
    private String marketChartCoin;
    private String marketChartRange;
    private String ohlcById;

}
