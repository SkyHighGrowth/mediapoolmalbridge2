package com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.schedule;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.schedule.JobState;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Job entity table where scheduler services logs their execution
 */
@Entity
@Table( name = "scheduled_jobs",
        indexes  = @Index( columnList = "created" ) )
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "job_state")
    @Enumerated(EnumType.STRING)
    private JobState jobState;

    @Column(name = "clazz")
    private String clazz;

    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "thread_id")
    private String threadId;

    @Column(name = "task_active_count")
    private int taskActiveCount;

    @Column(name = "task_queue_size")
    private int taskQueueSize;

    public JobEntity() {}

    public JobEntity(final JobState jobState, final String clazz, final String threadId, final int taskActiveCount, final int taskQueueSize) {
        this.jobState = jobState;
        this.clazz = clazz;
        this.threadId = threadId;
        this.taskActiveCount = taskActiveCount;
        this.taskQueueSize = taskQueueSize;
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

    public int getTaskActiveCount() {
        return taskActiveCount;
    }

    public void setTaskActiveCount(int taskActiveCount) {
        this.taskActiveCount = taskActiveCount;
    }

    public int getTaskQueueSize() {
        return taskQueueSize;
    }

    public void setTaskQueueSize(int taskQueueSize) {
        this.taskQueueSize = taskQueueSize;
    }
}