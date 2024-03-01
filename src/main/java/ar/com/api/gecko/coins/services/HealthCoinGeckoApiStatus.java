package ar.com.api.gecko.coins.services;

import ar.com.api.gecko.coins.configuration.ExternalServerConfig;
import ar.com.api.gecko.coins.configuration.HttpServiceCall;
import ar.com.api.gecko.coins.model.Ping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class HealthCoinGeckoApiStatus {

    private final HttpServiceCall httpServiceCall;

    private final ExternalServerConfig externalServerConfig;

    public HealthCoinGeckoApiStatus(HttpServiceCall serviceCall, ExternalServerConfig serviceConfig) {
        this.httpServiceCall = serviceCall;
        this.externalServerConfig = serviceConfig;
    }

    public Mono<Ping> getStatusCoinGeckoService() {
        log.info("Calling method: {}", externalServerConfig.getPing());

        return httpServiceCall.getMonoObject(externalServerConfig.getPing(), Ping.class);
    }

}
