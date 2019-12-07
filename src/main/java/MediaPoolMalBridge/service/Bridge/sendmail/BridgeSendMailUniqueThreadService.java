package MediaPoolMalBridge.service.Bridge.sendmail;

import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.Bridge.AbstractBridgeUniqueThreadService;
import MediaPoolMalBridge.service.Bridge.sendmail.model.BMMailFormat;
import MediaPoolMalBridge.service.Bridge.sendmail.model.MALMailFormat;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service that sends mail messages
 */
@Service
public class BridgeSendMailUniqueThreadService extends AbstractBridgeUniqueThreadService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final JavaMailSender emailSender;

    private final BMMailFormat bmMailFormat;

    private final MALMailFormat malMailFormat;

    public BridgeSendMailUniqueThreadService(final JavaMailSender emailSender,
                                             final BMMailFormat bmMailFormat,
                                             final MALMailFormat malMailFormat)
    {
        this.emailSender = emailSender;
        this.bmMailFormat = bmMailFormat;
        this.malMailFormat = malMailFormat;
    }

    @Override
    protected void run() {
        sendToBM( getMidnightBridgeLookInThePast() );
        sendToMAL( getMidnightBridgeLookInThePast() );
    }

    private void sendToBM( final LocalDateTime since )
    {
        boolean condition = true;
        for( int page = 0; condition; ++page ) {
            try {
                List<ReportsEntity> reports = reportsRepository.findAllByReportToAndSentNotAndCreatedIsAfter(ReportTo.BM, false, since, PageRequest.of(0, appConfig.getDatabasePageSize()));
                if( reports.isEmpty() || page > 1000 ) {
                    break;
                }
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(appConfig.getMailBmAddress());
                message.setSubject( "Automatic report from MALToBMBridge" );
                message.setText( bmMailFormat.transform( reports ) );
                emailSender.send(message);
                markAsSent( reports );
            } catch( final Exception e ) {
                final String message = String.format( "Can not send mail reports for page [%s] on date [%s] to mail address [%s] with error [%s]", page, since.format(DATE_TIME_FORMATTER), appConfig.getMailBmAddress(), e.getMessage() );
                final ReportsEntity report = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null );
                reportsRepository.save( report );
                logger.error( message, e );
            }
        }
    }

    private void sendToMAL( final LocalDateTime since )
    {
        boolean condition = true;
        for( int page = 0; condition; ++page ) {
            try {
                List<ReportsEntity> reports = reportsRepository.findAllByReportToAndSentNotAndCreatedIsAfter(ReportTo.MAL, false, since, PageRequest.of(0, appConfig.getDatabasePageSize()));
                if( reports.isEmpty() || page > 1000 ) {
                    break;
                }
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(appConfig.getMailMalAddress());
                message.setSubject( "Automatic report from MALToBMBridge" );
                message.setText( malMailFormat.transform( reports ) );
                emailSender.send(message);
                markAsSent( reports );
            } catch( final Exception e ) {
                final String message = String.format( "Can not send mail reports for page [%s] on date [%s] to mail address [%s] with error [%s]", page, since.format(DATE_TIME_FORMATTER), appConfig.getMailMalAddress(), e.getMessage() );
                final ReportsEntity report = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
                reportsRepository.save( report );
                logger.error( message, e );
            }
        }
    }

    @Transactional
    protected void markAsSent( final List<ReportsEntity> reports )
    {
        reports.forEach( reportsEntity -> {
            reportsEntity.setSent( true );
            reportsRepository.save( reportsEntity );
        } );
    }
}
