package com.brandmaker.mediapoolmalbridge.config;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Rest temapltes bean configuration
 */
@Configuration
public class RestConfig {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int READ_TIME_OUT_MS = 60000;

    private static final int CONNECTION_REQUEST_TIME_OUT = 50000;

    private static final int CONNECT_TIMEOUT = 50000;

    private static final int MAX_CONN_TOTAL = 500;

    private static final int MAX_CONN_PER_ROUTE = 100;

    private static final int MAX_RETRY_INTERVAL = 1000 * 60 * 1;

    private static final int MAX_RETRIES = 2;


    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create()
                        .setMaxConnTotal(MAX_CONN_TOTAL)
                        .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                        .setServiceUnavailableRetryStrategy(new SalRetryStrategy(MAX_RETRIES, MAX_RETRY_INTERVAL))
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
                        .setServiceUnavailableRetryStrategy(new SalRetryStrategy(MAX_RETRIES, MAX_RETRY_INTERVAL))
                        .build());
        clientHttpRequestFactory.setReadTimeout(READ_TIME_OUT_MS);
        clientHttpRequestFactory.setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
        clientHttpRequestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        return new RestTemplate(clientHttpRequestFactory);
    }

	class SalRetryStrategy implements ServiceUnavailableRetryStrategy {

		private final int maxRetries;

		private final long retryInterval;

		public SalRetryStrategy(final int maxRetries, final int retryInterval) {
			super();
			Args.positive(maxRetries, "Max retries");
			Args.positive(retryInterval, "Retry interval");
			this.maxRetries = maxRetries;
			this.retryInterval = retryInterval;
		}

		public SalRetryStrategy() {
			this(5, 1000 * 60 * 3);
		}

		@Override
		public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
			return executionCount <= maxRetries && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK;
		}

		@Override
		public long getRetryInterval() {
			return retryInterval;
		}

	}
}
