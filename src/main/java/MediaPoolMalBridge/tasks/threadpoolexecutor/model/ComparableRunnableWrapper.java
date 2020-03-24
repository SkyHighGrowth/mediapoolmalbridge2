package MediaPoolMalBridge.tasks.threadpoolexecutor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparableRunnableWrapper implements Runnable, Comparable<ComparableRunnableWrapper> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private int priority;

    private String malAssetId;

    private Runnable task;

    public ComparableRunnableWrapper( final Runnable task, final int priority, final String malAssetId ) {
        this.task = task;
        this.priority = priority;
        this.malAssetId = malAssetId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority( final int priority ) {
        this.priority = priority;
    }

    public String getMalAssetId() {
        return malAssetId;
    }

    public void setMalAssetId(String malAssetId) {
        this.malAssetId = malAssetId;
    }

    @Override
    public int compareTo(ComparableRunnableWrapper o) {
        return priority - o.getPriority();
    }

    @Override
    public void run() {
        //logger.info( "RUNNING for priority {} on malAssetId {}", priority, malAssetId );
        task.run();
    }
}
