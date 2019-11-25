package MediaPoolMalBridge.persistence.entity.schedule;

import MediaPoolMalBridge.persistence.entity.enums.schedule.JobState;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "scheduled_jobs" )
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column( name = "job_state" )
    @Enumerated( EnumType.STRING )
    private JobState jobState;

    @Column( name = "clazz" )
    private String clazz;

    @CreationTimestamp
    private LocalDateTime created;

    @Column( name = "thread_id" )
    private String threadId;

    @Column( name = "threadExecutorSize")
    private int threadExecutorSize;

    public JobEntity( final JobState jobState, final String clazz, final String threadId, final int threadExecutorSize )
    {
        this.jobState = jobState;
        this.clazz = clazz;
        this.threadId = threadId;
        this.threadExecutorSize = threadExecutorSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public JobState getJobState() {
        return jobState;
    }

    public void setJobState(JobState jobState) {
        this.jobState = jobState;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public int getThreadExecutorSize() {
        return threadExecutorSize;
    }

    public void setThreadExecutorSize(int threadExecutorSize) {
        this.threadExecutorSize = threadExecutorSize;
    }
}