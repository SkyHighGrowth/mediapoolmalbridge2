package MediaPoolMalBridge.controller.model;

public class ThreadPoolsStatuses {

    private int schdulerPoolSize;

    private int schedulerQueueSize;

    private int schedulerActiveCount;

    private int malMaximalPoolSize;

    private int malPoolSize;

    private int malQueueSize;

    private int malMaximalQueueSize;

    private int malActiveCount;

    private int malLockCount;

    private int bmMaximalPoolSize;

    private int bmPoolSize;

    private int bmQueueSize;

    private int bmMaximalQueueSize;

    private int bmActiveCount;

    private int bmLockCount;

    private int bmPriorityMaximalPoolSize;

    private int bmPriorityPoolSize;

    private int bmPriorityQueueSize;

    private int bmPriorityMaximalQueueSize;

    private int bmPriorityActiveCount;

    private int bmPriorityLockCount;

    public ThreadPoolsStatuses(final int schdulerPoolSize, final int schedulerQueueSize, final int schedulerActiveCount,
                               final int malMaximalPoolSize, final int malPoolSize, final int malQueueSize, final int malMaximalQueueSize, final int malActiveCount, final int malLockCount,
                               final int bmMaximalPoolSize, final int bmPoolSize, final int bmQueueSize, final int bmMaximalQueueSize, final int bmActiveCount, final int bmLockCount,
                               final int bmPriorityMaximalPoolSize, final int bmPriorityPoolSize, final int bmPriorityQueueSize, final int bmPriorityMaximalQueueSize, final int bmPriorityActiveCount, final int bmPriorityLockCount) {
        this.schdulerPoolSize = schdulerPoolSize;
        this.schedulerQueueSize = schedulerQueueSize;
        this.schedulerActiveCount = schedulerActiveCount;
        this.malMaximalPoolSize = malMaximalPoolSize;
        this.malPoolSize = malPoolSize;
        this.malQueueSize = malQueueSize;
        this.malMaximalQueueSize = malMaximalQueueSize;
        this.malActiveCount = malActiveCount;
        this.malLockCount = malLockCount;
        this.bmMaximalPoolSize = bmMaximalPoolSize;
        this.bmPoolSize = bmPoolSize;
        this.bmQueueSize = bmQueueSize;
        this.bmMaximalQueueSize = bmMaximalQueueSize;
        this.bmActiveCount = bmActiveCount;
        this.bmLockCount = bmLockCount;
        this.bmPriorityMaximalPoolSize = bmPriorityMaximalPoolSize;
        this.bmPriorityPoolSize = bmPriorityPoolSize;
        this.bmPriorityQueueSize = bmPriorityQueueSize;
        this.bmPriorityMaximalQueueSize = bmPriorityMaximalQueueSize;
        this.bmPriorityActiveCount = bmPriorityActiveCount;
        this.bmPriorityLockCount = bmPriorityLockCount;
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

    public int getMalMaximalPoolSize() {
        return malMaximalPoolSize;
    }

    public void setMalMaximalPoolSize(int malMaximalPoolSize) {
        this.malMaximalPoolSize = malMaximalPoolSize;
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

    public int getBmMaximalPoolSize() {
        return bmMaximalPoolSize;
    }

    public void setBmMaximalPoolSize(int bmMaximalPoolSize) {
        this.bmMaximalPoolSize = bmMaximalPoolSize;
    }

    public int getBmPriorityMaximalPoolSize() {
        return bmPriorityMaximalPoolSize;
    }

    public void setBmPriorityMaximalPoolSize(int bmPriorityMaximalPoolSize) {
        this.bmPriorityMaximalPoolSize = bmPriorityMaximalPoolSize;
    }

    public int getBmPriorityPoolSize() {
        return bmPriorityPoolSize;
    }

    public void setBmPriorityPoolSize(int bmPriorityPoolSize) {
        this.bmPriorityPoolSize = bmPriorityPoolSize;
    }

    public int getBmPriorityQueueSize() {
        return bmPriorityQueueSize;
    }

    public void setBmPriorityQueueSize(int bmPriorityQueueSize) {
        this.bmPriorityQueueSize = bmPriorityQueueSize;
    }

    public int getBmPriorityMaximalQueueSize() {
        return bmPriorityMaximalQueueSize;
    }

    public void setBmPriorityMaximalQueueSize(int bmPriorityMaximalQueueSize) {
        this.bmPriorityMaximalQueueSize = bmPriorityMaximalQueueSize;
    }

    public int getBmPriorityActiveCount() {
        return bmPriorityActiveCount;
    }

    public void setBmPriorityActiveCount(int bmPriorityActiveCount) {
        this.bmPriorityActiveCount = bmPriorityActiveCount;
    }

    public int getBmPriorityLockCount() {
        return bmPriorityLockCount;
    }

    public void setBmPriorityLockCount(int bmPriorityLockCount) {
        this.bmPriorityLockCount = bmPriorityLockCount;
    }
}
