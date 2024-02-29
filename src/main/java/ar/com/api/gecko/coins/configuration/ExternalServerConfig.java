package ar.com.api.gecko.coins.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "coins")
@Getter
@Setter
public class ExternalServerConfig {

    private String baseURL;
    private String healthAPI;
    private String coinById;
    private String list;
    private String markets;
    private String tickers;
    private String history;
    private String marketChart;
    private String rangeById;
    private String ohlcById;

}
