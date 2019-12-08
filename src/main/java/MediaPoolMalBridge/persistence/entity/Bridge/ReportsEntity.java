package MediaPoolMalBridge.persistence.entity.Bridge;

import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Table where reports on error conditions are stored
 */
@Entity
@Table( name = "reports",
        indexes = { @Index( columnList = "report_to" ),
                    @Index( columnList = "report_to, created"),
                    @Index( columnList = "created" ) } )
public class ReportsEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "id" )
    private long id;

    @Column( name = "report_type" )
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column( name = "logger_name" )
    private String loggerName;

    @Column( name = "message" )
    private String message;

    @Column( name = "jsoned_object_01" )
    private String jsonedObject01;

    @Column( name = "jsoned_object_02" )
    private String jsonedObject02;

    @Column( name = "jsoned_object_03" )
    private String jsonedObject03;

    @Column( name = "report_to" )
    @Enumerated( EnumType.STRING )
    private ReportTo reportTo;

    @Column( name = "thread_name" )
    private String threadName;

    @Column( name = "sent" )
    private boolean sent;

    @CreationTimestamp
    @Column( name = "created" )
    private LocalDateTime created;

    @Column( name = "mal_asset_id" )
    private String malAssetId;

    public ReportsEntity( ) { }

    public ReportsEntity( final ReportType reportType, final String loggerName, final String malAssetId, final String message, final ReportTo reportTo, final String jsonedObject01, final String jsonedObject02, final String jsonedObject03 ) {
        this.reportType = reportType;
        this.loggerName = loggerName;
        this.malAssetId = malAssetId;
        this.message = message;
        this.reportTo = reportTo;
        this.jsonedObject01 = jsonedObject01;
        this.jsonedObject02 = jsonedObject02;
        this.jsonedObject03 = jsonedObject03;
        this.sent = false;
        this.threadName = Thread.currentThread().getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType bmReportType) {
        this.reportType = bmReportType;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJsonedObject01() {
        return jsonedObject01;
    }

    public void setJsonedObject01(String jsonedObject01) {
        this.jsonedObject01 = jsonedObject01;
    }

    public String getJsonedObject02() {
        return jsonedObject02;
    }

    public void setJsonedObject02(String jsonedObject02) {
        this.jsonedObject02 = jsonedObject02;
    }

    public String getJsonedObject03() {
        return jsonedObject03;
    }

    public void setJsonedObject03(String jsonedObject03) {
        this.jsonedObject03 = jsonedObject03;
    }

    public ReportTo getReportTo() {
        return reportTo;
    }

    public void setReportTo(ReportTo reportTo) {
        this.reportTo = reportTo;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getMalAssetId() {
        return malAssetId;
    }

    public void setMalAssetId(String malAssetId) {
        this.malAssetId = malAssetId;
    }
}
