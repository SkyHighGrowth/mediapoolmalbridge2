package MediaPoolMalBridge.persistence.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * base class for extities
 */
public class AbstractEntity {

    protected static final Gson GSON = new Gson();

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
}
