package MediaPoolMalBridge.service.Bridge.sendmail;

import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.AbstractUniqueService;
import MediaPoolMalBridge.service.Bridge.sendmail.model.BMMailFormat;
import MediaPoolMalBridge.service.Bridge.sendmail.model.MALMailFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class BridgeSendMailService extends AbstractUniqueService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final int pageSize = 200;

    private final String bmEmail;

    private final String malEmail;

    private final JavaMailSender emailSender;

    private final BMMailFormat bmMailFormat;

    private final MALMailFormat malMailFormat;

    public BridgeSendMailService(@Value( "${mail.bm.address}" ) final String bmEmail,
                                 @Value( "${mail.mal.address}" ) final String malEmail,
                                 final JavaMailSender emailSender,
                                 final BMMailFormat bmMailFormat,
                                 final MALMailFormat malMailFormat)
    {
        this.bmEmail = bmEmail;
        this.malEmail = malEmail;
        this.emailSender = emailSender;
        this.bmMailFormat = bmMailFormat;
        this.malMailFormat = malMailFormat;
    }

    @Override
    protected void run() {
        final LocalDateTime since = Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset( ZoneOffset.UTC )
                .toLocalDateTime()
                .withHour( 0 )
                .withMinute( 0 )
                .withSecond( 0 );
        sendToBM( since );
        sendToMAL( since );
    }

    private void sendToBM( final LocalDateTime since )
    {
        boolean condition = true;
        for( int page = 0; condition; ++page ) {
            try {
                Slice<ReportsEntity> reports = reportsRepository.findAllByReportToAndCreatedIsAfter(ReportTo.BM, since, PageRequest.of(page, pageSize));
                condition = reports.hasNext();
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(bmEmail);
                message.setSubject( "Automatic daily report for MALToBMBridge" );
                message.setText( bmMailFormat.transform( reports ) );
                emailSender.send(message);
            } catch( final Exception e ) {
                final String message = String.format( "Can not send mail reports for page [%s] on date [%s] to mail address [%s] with error [%s]", page, since.format(DATE_TIME_FORMATTER), bmEmail, e.getMessage() );
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
                Slice<ReportsEntity> reports = reportsRepository.findAllByReportToAndCreatedIsAfter(ReportTo.MAL, since, PageRequest.of(page, pageSize));
                condition = reports.hasNext();
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(malEmail);
                message.setSubject( "Automatic daily report for MALToBMBridge" );
                message.setText( malMailFormat.transform( reports ) );
                emailSender.send(message);
            } catch( final Exception e ) {
                final String message = String.format( "Can not send mail reports for page [%s] on date [%s] to mail address [%s] with error [%s]", page, since.format(DATE_TIME_FORMATTER), malEmail, e.getMessage() );
                final ReportsEntity report = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
                reportsRepository.save( report );
                logger.error( message, e );
            }
        }
    }
}
