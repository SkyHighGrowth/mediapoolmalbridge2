package MediaPoolMalBridge.service.Bridge.sendmail.model;

import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Mail formatter for Brandmaker
 */
@Component
public class BMMailFormat {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );

    private static final String NEW_LINE = System.getProperty( "line.separator" );

    public String transform(final List<ReportsEntity> reports )
    {
        final StringWriter stringWriter = new StringWriter();
        for (ReportsEntity report : reports) {
            stringWriter.append( "id: " );
            stringWriter.append( String.valueOf( report.getId() ) );
            stringWriter.append( NEW_LINE );
            stringWriter.append( "created: " );
            stringWriter.append( report.getCreated()
                    .atOffset(ZoneOffset.of( ZoneId.systemDefault().getId() ) )
                    .format( DATE_TIME_FORMATTER ) );
            stringWriter.append( NEW_LINE );
            stringWriter.append( "loggerName: " );
            stringWriter.append( report.getLoggerName() );
            stringWriter.append( NEW_LINE );
            stringWriter.append( "message: " );
            stringWriter.append( report.getMessage() );
            stringWriter.append( NEW_LINE );
            stringWriter.append( NEW_LINE );
        }
        return stringWriter.toString();
    }
}
