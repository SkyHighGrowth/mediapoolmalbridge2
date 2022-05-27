package com.brandmaker.mediapoolmalbridge.clients.mal.download.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.download.client.model.MALDownloadAssetResponse;
import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

/**
 * Client that downloads files from MAL server
 */
@Component
public class MALDownloadAssetClient {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Gson GSON = new Gson();

    private final RestTemplate restTemplate;

    private final AppConfig appConfig;

    public MALDownloadAssetClient(@Qualifier("DownloadRestTemplate") final RestTemplate restTemplate,
                                   final AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    /**
     * Configure rest template download handlers
     * @param url - download url
     * @param fileName - file name where file is going to be downloaded
     * @return - {@link MALDownloadAssetResponse}
     */
    public MALDownloadAssetResponse download(String url, final String fileName) {
        final MALDownloadAssetResponse response = new MALDownloadAssetResponse();
		if (url != null && !url.isEmpty()) {
			if (url.startsWith("http:")) {
				logger.info("URL BEFORE:   " + url);
				url = url.replace("http:", "https:");
				logger.info("URL AFTER: " + url);
			} else {
				logger.info("URL OK: " + url);
			}
		}
        restTemplate.execute(url, HttpMethod.GET,
                clientHttpRequest -> clientHttpRequest.getHeaders().set("Accept-Encoding", "gzip"),
                clientHttpResponse -> {
                    final HttpHeaders responseHeaders = clientHttpResponse.getHeaders();
                    if (responseHeaders.getContentType() != null && MediaType.APPLICATION_JSON.equals(responseHeaders.getContentType())) {
                        String body;
                        try (final Reader reader = new InputStreamReader(clientHttpResponse.getBody())) {
                            body = CharStreams.toString(reader);
                        }
                        response.fromMALAbstractResposne(GSON.fromJson(body, MALDownloadAssetResponse.class));
                        return null;
                    }
                    final String absoluteTempFile = appConfig.getTempDir() + fileName;
                    final File file = new File(absoluteTempFile);
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        StreamUtils.copy(clientHttpResponse.getBody(), fileOutputStream);
                        fileOutputStream.close();
                        response.setAbsoluteFilePath(absoluteTempFile);
                    } catch (final Exception e) {
                        response.fromException("error", e.getMessage());
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw e;
                    }
                    return null;
                });
        return response;
    }
}
