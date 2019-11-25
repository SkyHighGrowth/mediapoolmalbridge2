package MediaPoolMalBridge.service.Bridge.sendmail.model;

import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public class MALMailFormat {

    public String transform(final Slice<ReportsEntity> reports )
    {
        return "hello";
    }
}


