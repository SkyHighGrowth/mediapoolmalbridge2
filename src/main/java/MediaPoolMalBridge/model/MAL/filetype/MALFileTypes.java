package MediaPoolMalBridge.model.MAL.filetype;

import MediaPoolMalBridge.clients.MAL.filetypes.client.model.MALGetFileTypesResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class MALFileTypes extends ConcurrentHashMap<String, String> {

    public synchronized void update(final MALGetFileTypesResponse response)
    {
        clear();
        response.getFileTypes()
                .forEach( fileType -> put( fileType.getFileTypeId(), fileType.getName().toLowerCase() ) );
    }

    public synchronized String getFileType(final String fileTypeId)
    {
        return get( fileTypeId );
    }
}
