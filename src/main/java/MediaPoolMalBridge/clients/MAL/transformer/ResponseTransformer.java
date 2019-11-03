package MediaPoolMalBridge.clients.MAL.transformer;

import MediaPoolMalBridge.clients.MAL.model.response.ErrorResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class ResponseTransformer<RESPONSE> {

    private static Logger logger = LoggerFactory.getLogger(ResponseTransformer.class);

    private static final Gson GSON = new Gson();

    public RestResponse<RESPONSE> transform(final ResponseEntity<String> response) {
        try {
            final ErrorResponse errorResponse = (new Gson()).fromJson(response.getBody(), ErrorResponse.class);
            return new RestResponse<>(response.getStatusCode(), response.getHeaders(), errorResponse);
        } catch (final JsonSyntaxException e) {

        }

        try {
            final Type responseType = new TypeToken<RESPONSE>() {
            }.getType();
            final RESPONSE deserializedResponse = GSON.fromJson(response.getBody(), responseType);
            return new RestResponse<>(response.getStatusCode(), response.getHeaders(), deserializedResponse);
        } catch (final JsonSyntaxException e) {
            logger.error("Can not deserialize to class response body {}", GSON.toJson(response));
            return new RestResponse<>(response.getStatusCode(), response.getHeaders(), new ErrorResponse("failure", "Can not deserialize"));
        }
    }
}
