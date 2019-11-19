package MediaPoolMalBridge.persistence.entity;

import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;

import javax.persistence.*;

@Entity
public class ReportsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id" )
    private long id;

    @Column( name = "bm_report_type" )
    @Enumerated(EnumType.STRING)
    private ReportType bmReportType;

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
    private ReportTo reportTo;

    public ReportsEntity( ) { }

    public ReportsEntity( final ReportType reportType, final String loggerName, final String message, final ReportTo reportTo, final String jsonedObject01, final String jsonedObject02, final String jsonedObject03 ) {
        this.bmReportType = reportType;
        this.loggerName = loggerName;
        this.message = message;
        this.reportTo = reportTo;
        this.jsonedObject01 = jsonedObject01;
        this.jsonedObject02 = jsonedObject02;
        this.jsonedObject03 = jsonedObject03;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReportType getBmReportType() {
        return bmReportType;
    }

    public void setBmReportType(ReportType bmReportType) {
        this.bmReportType = bmReportType;
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
}
