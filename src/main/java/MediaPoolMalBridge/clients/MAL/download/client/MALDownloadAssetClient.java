package MediaPoolMalBridge.clients.MAL.download.client;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.MAL.download.client.model.MALDownloadAssetResponse;
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

@Component
public class MALDownloadAssetClient {

    private static final Gson GSON = new Gson();

    private static Logger logger = LoggerFactory.getLogger(MALDownloadAssetClient.class);

    private final RestTemplate restTemplate;

    private final AppConfig appConfig;

    private MALDownloadAssetClient(@Qualifier("DownloadRestTemplate") final RestTemplate restTemplate,
                                   final AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    public MALDownloadAssetResponse download(final String url, final String fileName) {
        final MALDownloadAssetResponse response = new MALDownloadAssetResponse();
        try {
            restTemplate.execute(url, HttpMethod.GET,
                    clientHttpRequest -> {
                        //clientHttpRequest.getHeaders().set( "Accept-Encoding", "gzip" );
                    },
                    clientHttpResponse -> {
                        final HttpHeaders responseHeaders = clientHttpResponse.getHeaders();
                        if( responseHeaders.getContentType() != null && MediaType.APPLICATION_JSON_UTF8.equals( responseHeaders.getContentType() ) )
                        {
                            String body = null;
                            try (final Reader reader = new InputStreamReader(clientHttpResponse.getBody())) {
                                body = CharStreams.toString(reader);
                            }
                            response.fromMALAbstractResposne( GSON.fromJson( body, MALDownloadAssetResponse.class ) );
                            return null;
                        }
                        final String absoluteTempFile = appConfig.getTempDir() + fileName;
                        final File file = new File(absoluteTempFile);
                        FileOutputStream fileOutputStream = null;
                        try {
                            fileOutputStream = new FileOutputStream(file);
                            StreamUtils.copy(clientHttpResponse.getBody(), fileOutputStream);
                            fileOutputStream.close();
                            response.setAbsoluteFilePath( absoluteTempFile );
                        }
                        catch ( final Exception e )
                        {
                            response.fromException( "error", e.getMessage() );
                            if( fileOutputStream != null )
                            {
                                fileOutputStream.close();
                            }
                        }
                        return null;
                    });
        } catch (final Exception e) {
            logger.error("Can not download file with fileName {} and url {}", fileName, url, e);
            response.fromException( "error", e.getMessage() );
        }
        logger.debug( "Download fileName {} url {} result {}", fileName, url, (new Gson()).toJson(response));
        return response;
    }
}
