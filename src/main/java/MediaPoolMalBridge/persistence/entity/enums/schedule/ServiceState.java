package MediaPoolMalBridge.persistence.entity.enums.schedule;

/**
 * Enum which represents state of the service
 */
public enum ServiceState {
    SERVICE_START,
    SERVICE_LOCK_ACQUIRED,
    SERVICE_FINISHED,
    SERVICE_LOCK_ACQUIRE_FAILED
}
