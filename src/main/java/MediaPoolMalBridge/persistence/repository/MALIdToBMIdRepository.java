package MediaPoolMalBridge.persistence.repository;


import MediaPoolMalBridge.persistence.entity.MALIdToBMIdEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MALIdToBMIdRepository extends CrudRepository<MALIdToBMIdEntity, String> {

    public List<MALIdToBMIdEntity> findBySalId( final String salId );

    public List<MALIdToBMIdEntity> findByBmId( final String bmId );
}
