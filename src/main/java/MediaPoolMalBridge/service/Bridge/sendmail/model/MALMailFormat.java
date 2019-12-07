package MediaPoolMalBridge.service.Bridge.sendmail.model;

import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mail formatter for Marriott
 */
@Component
public class MALMailFormat {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss'UTC'" );

    private static final String NEW_LINE = System.getProperty( "line.separator" );

    private static final Map<String, String> LOGGER_NAME_MAPPER = Stream.of( new String[][]
            {
                    {"MediaPoolMalBridge.service.MAL.assets.download.MALDownloadAssetService", "Download file"},
                    {"MediaPoolMalBridge.service.MAL.assets.deleted.MALCollectDeletedAssetsUniqueThreadSinceService", "Unavailable assets end point"},
                    {"MediaPoolMalBridge.service.MAL.assetstructures.assettype.MALGetAssetTypeUniqueThreadService", "Asset type end point"},
                    {"MediaPoolMalBridge.service.MAL.kits.MALGetKitsUniqueThreadService", "Kits end point"},
                    {"MediaPoolMalBridge.service.MAL.assetstructures.assetsubject.MALGetAssetSubjectUniqueThreadService", "Subjects end point"},
                    {"MediaPoolMalBridge.service.MAL.assetstructures.assetdestination.MALGetAssetDestinationUniqueThreadService", "Destiantion end point"},
                    {"MediaPoolMalBridge.service.MAL.assetstructures.assetcolor.MALGetAssetColorUniqueThreadService", "Color end point"},
                    {"MediaPoolMalBridge.service.MAL.assetstructures.assetcollection.MALGetAssetCollectionUniqueThreadService", "Collection end point"},
                    {"MediaPoolMalBridge.service.MAL.assetstructures.assetbrand.MALGetAssetBrandUniqueThreadService", "Brand end point"},
                    {"MediaPoolMalBridge.service.MAL.properties.download.MALGetPropertiesUniqueThreadService", "Properties end point"},
                    {"MediaPoolMalBridge.service.MAL.assets.MALCollectCreatedAssetsUniqueThreadSinceService", "Asset end point looking for created assets"},
                    {"MediaPoolMalBridge.service.MAL.assets.AbstractMALAssetsUniqueThreadService", "Asset end point looking for modified"},
                    {"MediaPoolMalBridge.service.MAL.assetstructures.propertytypes.MALGetPropertyTypesUniqueThreadService", "Property types end point"},
                    {"MediaPoolMalBridge.service.Bridge.sendmail.BridgeSendMailUniqueThreadService", "Send mail service"},
                    {"MediaPoolMalBridge.service.MAL.assetstructures.assetfiletypes.MALGetAssetFileTypesUniqueThreadService", "File types end point"}
            }).collect(Collectors.toMap( data -> data[0], data -> data[1]));

    public String transform(final List<ReportsEntity> reports )
    {
        final StringWriter stringWriter = new StringWriter();
        for (ReportsEntity report : reports) {
            stringWriter.append( "id: " );
            stringWriter.append( String.valueOf( report.getId() ) );
            stringWriter.append( NEW_LINE );
            stringWriter.append( report.getCreated().toLocalDate().format(DATE_TIME_FORMATTER) );
            stringWriter.append( NEW_LINE );
            stringWriter.append( "loggerName: " );
            stringWriter.append( LOGGER_NAME_MAPPER.get( report.getLoggerName() ) );
            stringWriter.append( NEW_LINE );
            stringWriter.append( "message: " );
            stringWriter.append( report.getMessage() );
            stringWriter.append( NEW_LINE );
            stringWriter.append( NEW_LINE );
        }
        return stringWriter.toString();
    }
}


