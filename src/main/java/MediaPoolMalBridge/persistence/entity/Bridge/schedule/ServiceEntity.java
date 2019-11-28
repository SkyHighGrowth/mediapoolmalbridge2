package MediaPoolMalBridge.persistence.entity.Bridge.schedule;

import MediaPoolMalBridge.persistence.entity.enums.schedule.ServiceState;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "service_execution" )
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column( name = "id" )
    private long id;

    @Column( name = "service_state" )
    @Enumerated( EnumType.STRING )
    private ServiceState serviceState;

    @Column( name = "clazz" )
    private String clazz;

    @Column( name = "thread_id" )
    private String threadId;

    @CreationTimestamp
    @Column( name = "created")
    private LocalDateTime created;

    @Column(name = "task_active_count")
    private int taskActiveCount;

    @Column(name = "task_queue_size")
    private int taskQueueSize;

    public ServiceEntity(final ServiceState serviceState, final String clazz, final String threadId, final int taskActiveCount, final int taskQueueSize )
    {
        this.serviceState = serviceState;
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

    public ServiceState getServiceState() {
        return serviceState;
    }

    public void setServiceState(ServiceState serviceState) {
        this.serviceState = serviceState;
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
