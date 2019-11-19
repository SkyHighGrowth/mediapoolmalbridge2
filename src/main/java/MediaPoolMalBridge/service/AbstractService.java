package MediaPoolMalBridge.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final Gson GSON = new Gson();
}
