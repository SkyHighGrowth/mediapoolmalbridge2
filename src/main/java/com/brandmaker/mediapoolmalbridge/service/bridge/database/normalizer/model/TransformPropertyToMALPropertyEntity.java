package com.brandmaker.mediapoolmalbridge.service.bridge.database.normalizer.model;

import com.brandmaker.mediapoolmalbridge.persistence.entity.mal.MALPropertyEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.property.MALPropertyStatus;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

@SuppressWarnings("unusedLegacyCode")
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
        malPropertyEntity.setTelephone( property.getTelephone() );
        malPropertyEntity.setBrand( property.getBrand() );
        malPropertyEntity.setParentBrand( property.getParentBrand() );
        malPropertyEntity.setLatitude( property.getLatitude() );
        malPropertyEntity.setLongitude( property.getLongitude() );
        malPropertyEntity.setMd5Hash( DigestUtils.md5Hex( (new Gson()).toJson( malPropertyEntity ) ) );
        malPropertyEntity.setMalPropertyStatus( MALPropertyStatus.OBSERVED );

        return malPropertyEntity;
    }
}
