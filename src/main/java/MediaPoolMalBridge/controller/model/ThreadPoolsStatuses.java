package MediaPoolMalBridge.controller.model;

public class ThreadPoolsStatuses {

    private int schdulerPoolSize;

    private int schedulerQueueSize;

    private int schedulerActiveCount;

    private int malPoolSize;

    private int malQueueSize;

    private int malMaximalQueueSize;

    private int malActiveCount;

    private int malLockCount;

    private int bmPoolSize;

    private int bmQueueSize;

    private int bmMaximalQueueSize;

    private int bmActiveCount;

    private int bmLockCount;

    public ThreadPoolsStatuses(final int schdulerPoolSize, final int schedulerQueueSize, final int schedulerActiveCount,
                               final int malPoolSize, final int malQueueSize, final int malMaximalQueueSize, final int malActiveCount, final int malLockCount,
                               final int bmPoolSize, final int bmQueueSize, final int bmMaximalQueueSize, final int bmActiveCount, final int bmLockCount) {
        this.schdulerPoolSize = schdulerPoolSize;
        this.schedulerQueueSize = schedulerQueueSize;
        this.schedulerActiveCount = schedulerActiveCount;
        this.malPoolSize = malPoolSize;
        this.malQueueSize = malQueueSize;
        this.malMaximalQueueSize = malMaximalQueueSize;
        this.malActiveCount = malActiveCount;
        this.malLockCount = malLockCount;
        this.bmPoolSize = bmPoolSize;
        this.bmQueueSize = bmQueueSize;
        this.bmMaximalQueueSize = bmMaximalQueueSize;
        this.bmActiveCount = bmActiveCount;
        this.bmLockCount = bmLockCount;
    }

    public int getSchdulerPoolSize() {
        return schdulerPoolSize;
    }

    public void setSchdulerPoolSize(int schdulerPoolSize) {
        this.schdulerPoolSize = schdulerPoolSize;
    }

    public int getSchedulerQueueSize() {
        return schedulerQueueSize;
    }

    public void setSchedulerQueueSize(int schedulerQueueSize) {
        this.schedulerQueueSize = schedulerQueueSize;
    }

    public int getSchedulerActiveCount() {
        return schedulerActiveCount;
    }

    public void setSchedulerActiveCount(int schedulerActiveCount) {
        this.schedulerActiveCount = schedulerActiveCount;
    }

    public int getMalPoolSize() {
        return malPoolSize;
    }

    public void setMalPoolSize(int malPoolSize) {
        this.malPoolSize = malPoolSize;
    }

    public int getMalQueueSize() {
        return malQueueSize;
    }

    public void setMalQueueSize(int malQueueSize) {
        this.malQueueSize = malQueueSize;
    }

    public int getMalMaximalQueueSize() {
        return malMaximalQueueSize;
    }

    public void setMalMaximalQueueSize(int malMaximalQueueSize) {
        this.malMaximalQueueSize = malMaximalQueueSize;
    }

    public int getMalActiveCount() {
        return malActiveCount;
    }

    public void setMalActiveCount(int malActiveCount) {
        this.malActiveCount = malActiveCount;
    }

    public int getMalLockCount() {
        return malLockCount;
    }

    public void setMalLockCount(int malLockCount) {
        this.malLockCount = malLockCount;
    }

    public int getBmPoolSize() {
        return bmPoolSize;
    }

    public void setBmPoolSize(int bmPoolSize) {
        this.bmPoolSize = bmPoolSize;
    }

    public int getBmQueueSize() {
        return bmQueueSize;
    }

    public void setBmQueueSize(int bmQueueSize) {
        this.bmQueueSize = bmQueueSize;
    }

    public int getBmMaximalQueueSize() {
        return bmMaximalQueueSize;
    }

    public void setBmMaximalQueueSize(int bmMaximalQueueSize) {
        this.bmMaximalQueueSize = bmMaximalQueueSize;
    }

    public int getBmActiveCount() {
        return bmActiveCount;
    }

    public void setBmActiveCount(int bmActiveCount) {
        this.bmActiveCount = bmActiveCount;
    }

    public int getBmLockCount() {
        return bmLockCount;
    }

    public void setBmLockCount(int bmLockCount) {
        this.bmLockCount = bmLockCount;
    }
}
