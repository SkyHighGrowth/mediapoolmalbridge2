package MediaPoolMalBridge.service.Bridge.sendmail.model;

import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public class BMMailFormat {

    public String transform(final Slice<ReportsEntity> reports )
    {
        return "hello";
    }
}
