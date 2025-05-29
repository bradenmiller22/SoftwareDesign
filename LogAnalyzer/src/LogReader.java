import java.util.List;

/**
 * LogReader parses logs and then distributes to correct buffer
 * Implements Runnable to allow for concurrent processing of batches
 * @author bmiller38
 */

public class LogReader implements Runnable{
    /**
     * The batch of logs to process from the buffer
     */
    private final List<String> logs;
    /**
     * The shared buffer to distribute logs to the analyzers
     */
    private final LogBuffer logBuffer;

    /**
     * Constructor with logs to process and buffer to distribute
     * @param logs List of log strings to process
     * @param logBuffer Shared buffer to distribute logs to
     */
    public LogReader(List<String> logs, LogBuffer logBuffer){
        this.logs = logs;
        this.logBuffer = logBuffer;
    }

    /**
     * Runs log reading process
     * Categorizes each log by prefix and sends to correct buffer
     * Acts as the "producer" putting logs into correct buffer
     */
    @Override
    public void run() {
        for (String log : logs){//go through all logs that were sent in (100) till end (<100)
            try{
                if(log.startsWith("INFO")){//send to correct analyzer
                    //put adds element to queue, waiting if its full
                    logBuffer.infoBuffer.put(log);
                } else if (log.startsWith("WARN")) {
                    logBuffer.warnBuffer.put(log);
                } else if (log.startsWith("ERROR")) {
                    logBuffer.errorBuffer.put(log);
                }
            }catch (InterruptedException e){//if thread was interrupted while waiting on put
                Thread.currentThread().interrupt();//restore interrupted status
            }
        }
    }
}
