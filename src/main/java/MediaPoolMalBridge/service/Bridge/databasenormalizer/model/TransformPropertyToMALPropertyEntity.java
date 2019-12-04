package MediaPoolMalBridge.service.Bridge.databasenormalizer.model;

import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import MediaPoolMalBridge.persistence.entity.enums.property.MALPropertyStatus;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

public class TransformPropertyToMALPropertyEntity {

    public MALPropertyEntity transform(final Property property ) {
        final MALPropertyEntity malPropertyEntity = new MALPropertyEntity( );
        malPropertyEntity.setPropertyId( String.valueOf( property.getPropertyId() ) );
        malPropertyEntity.setName( property.getName() );
        malPropertyEntity.setAddress( property.getAddress() );
        malPropertyEntity.setState( property.getState() );
        malPropertyEntity.setZip( property.getZip() );
        malPropertyEntity.setCity( property.getCity() );
        malPropertyEntity.setCountry( property.getCountry() );
        malPropertyEntity.setUrl( property.getUrl() );
        //malPropertyEntity.setVanityUrl( property.getVanityUrl() );
        malPropertyEntity.setTelephone( property.getTelephone() );
        malPropertyEntity.setBrand( property.getBrand() );
        malPropertyEntity.setParentBrand( property.getParentBrand() );
        malPropertyEntity.setLatitude( property.getLatitude() );
        malPropertyEntity.setLongitude( property.getLongitude() );
        //malPropertyEntity.setLongitude( property.getPrimaryPropertyImage() );
        malPropertyEntity.setMd5Hash( DigestUtils.md5Hex( (new Gson()).toJson( malPropertyEntity ) ) );
        malPropertyEntity.setMalPropertyStatus( MALPropertyStatus.OBSERVED );

        return malPropertyEntity;
    }
}
