package MediaPoolMalBridge.persistence.entity.schedule;

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

    @Column( name = "thread_executor_size" )
    private int threadExecutorSize;

    @CreationTimestamp
    @Column( name = "created")
    private LocalDateTime created;

    public ServiceEntity(final ServiceState serviceState, final String clazz, final String threadId, final int threadExecutorSize )
    {
        this.serviceState = serviceState;
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

    public int getThreadExecutorSize() {
        return threadExecutorSize;
    }

    public void setThreadExecutorSize(int threadExecutorSize) {
        this.threadExecutorSize = threadExecutorSize;
    }
}
