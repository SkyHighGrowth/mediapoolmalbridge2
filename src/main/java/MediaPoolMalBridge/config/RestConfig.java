package MediaPoolMalBridge.config;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    private static final int READ_TIME_OUT_MS = 30000;

    private static final int CONNECTION_REQUEST_TIME_OUT = 50000;

    private static final int CONNECT_TIMEOUT = 50000;

    private static final int MAX_CONN_TOTAL = 500;

    private static final int MAX_CONN_PER_ROUTE = 50;

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create()
                        .setMaxConnTotal(MAX_CONN_TOTAL)
                        .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                        .build());
        clientHttpRequestFactory.setReadTimeout(READ_TIME_OUT_MS);
        clientHttpRequestFactory.setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
        clientHttpRequestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        return new RestTemplate(clientHttpRequestFactory);
    }

    @Bean("DownloadRestTemplate")
    public RestTemplate downloadRestTemplate() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create()
                        .setMaxConnTotal(MAX_CONN_TOTAL)
                        .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                        .build());
        clientHttpRequestFactory.setReadTimeout(READ_TIME_OUT_MS);
        clientHttpRequestFactory.setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
        clientHttpRequestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        return new RestTemplate(clientHttpRequestFactory);
    }
}
