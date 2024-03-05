package ar.com.api.gecko.coins.services;

import ar.com.api.gecko.coins.configuration.ExternalServerConfig;
import ar.com.api.gecko.coins.configuration.HttpServiceCall;
import ar.com.api.gecko.coins.enums.ErrorTypesEnum;
import ar.com.api.gecko.coins.exception.ApiServerErrorException;
import ar.com.api.gecko.coins.model.Ping;
import ar.com.api.gecko.coins.utils.CoinsTestUtils;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

class HealthCoinGeckoApiStatusTest {

    @Mock
    private HttpServiceCall httpServiceCallMock;

    @Mock
    private ExternalServerConfig externalServerConfigMock;

    @InjectMocks
    private HealthCoinGeckoApiStatus healthCoinGeckoApiStatus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        given(externalServerConfigMock.getPing()).willReturn("pingUrlGeckoMock");
    }

    @AfterEach
    void tearDown() {
        reset(httpServiceCallMock, externalServerConfigMock);
    }

    @Test
    @DisplayName("Ensure successful retrieval of CoinGecko service status")
    void whenGetStatusCoinGeckoServiceCalled_ThenItShouldCallDependenciesAndFetchSuccessfully() {
        Ping expectedObjectMock = Instancio.create(Ping.class);
        given(httpServiceCallMock.getMonoObject(eq("pingUrlGeckoMock"), eq(Ping.class)))
                .willReturn(Mono.just(expectedObjectMock));

        Mono<Ping> actualObject = healthCoinGeckoApiStatus.getStatusCoinGeckoService();

        CoinsTestUtils.assertMonoSuccess(actualObject, pingObject -> {
            Optional.ofNullable(pingObject.getGeckoSays()).ifPresentOrElse(
                    name -> {},
                    () -> fail("Ping not be null")
            );
            Assertions.assertEquals(expectedObjectMock.getGeckoSays(),
                    pingObject.getGeckoSays());
        });

        then(externalServerConfigMock).should(times(2)).getPing();
        then(httpServiceCallMock).should(times(1))
                .getMonoObject("pingUrlGeckoMock", Ping.class);
    }

    @Test
    @DisplayName("Handle 4xx errors when retrieving CoinGecko service status")
    void whenGetStatusCoinGeckoServiceCalled_ThenItShouldCallDependenciesAndHandlesOnStatus4xx() {
        ApiServerErrorException expectedException =
                new ApiServerErrorException("Client error occurred", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        ErrorTypesEnum.GECKO_CLIENT_ERROR, HttpStatus.BAD_REQUEST);
        given(httpServiceCallMock.getMonoObject(eq("pingUrlGeckoMock"), eq(Ping.class)))
                .willReturn(Mono.error(expectedException));

        Mono<Ping> actualObject = healthCoinGeckoApiStatus.getStatusCoinGeckoService();

        CoinsTestUtils.assertService4xxClientError(actualObject,
                expectedException.getMessage(),
                ErrorTypesEnum.GECKO_CLIENT_ERROR);

        then(externalServerConfigMock).should(times(2)).getPing();
        then(httpServiceCallMock).should(times(1))
                .getMonoObject("pingUrlGeckoMock", Ping.class);
    }

    @Test
    @DisplayName("Handle 5xx errors when retrieving CoinGecko service status")
    void whenGetStatusCoinGeckoServiceCalled_ThenItShouldCallDependenciesAndHandlesOnStatus5xx() {
        ApiServerErrorException expectedException =
                new ApiServerErrorException("Server error occurred", HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                        ErrorTypesEnum.GECKO_SERVER_ERROR, HttpStatus.BAD_GATEWAY);
        given(httpServiceCallMock.getMonoObject(eq("pingUrlGeckoMock"), eq(Ping.class)))
                .willReturn(Mono.error(expectedException));

        Mono<Ping> actualObject = healthCoinGeckoApiStatus.getStatusCoinGeckoService();

        CoinsTestUtils.assertService5xxServerError(actualObject,
                expectedException.getMessage(),
                ErrorTypesEnum.GECKO_SERVER_ERROR);

        then(externalServerConfigMock).should(times(2)).getPing();
        then(httpServiceCallMock).should(times(1))
                .getMonoObject("pingUrlGeckoMock", Ping.class);

    }

}