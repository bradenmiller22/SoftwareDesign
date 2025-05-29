import java.util.concurrent.BlockingQueue;

/**
 * WarnAnalyzer processes warning logs from shared buffer
 * Counts total logs and tracks unauthorized access attempts
 * Implements Runnable to allow for concurrent execution in thread
 * @author bmiller38
 */
public class WarnAnalyzer implements Runnable{
    //blockingqueue provides thread-safe access to queue of warning logs
    /**
     * Blocking queue provides a safe way to add and remove from queue
     * Buffer of 10 containing warning logs
     */
    private BlockingQueue<String> buffer;
    /**
     * total count of warning logs processed
     */
    private int count = 0;
    /**
     * Total count of unauthorized access attempts
     */
    private int unauthorizedCount = 0;//count of unauthorized access attempts

    /**
     * WarnAnalyzer with specified buffer to read from
     * @param buffer Blockign queue containing warning logs to process
     */
    public WarnAnalyzer(BlockingQueue<String> buffer){
        this.buffer = buffer;
    }

    /**
     * Reads warning logs from buffer, counting them, tracking unauthorized access attempts
     * Terminates when it gets to end ("Done")
     * All analyzers act as "Consumer"
     */
    @Override
    public void run() {
        try{
            while(true){
                String log = buffer.take();//gets and remove top of queue, waiting if necessary till element available
                if(log.equals(LogBuffer.end)){//check for termination signal ("Done")
                    break;//exit loop
                }
                count++;//increment count of processed logs
                if(log.contains("Unauthorized access attempt")){//check if it has unauthorized attempt
                    unauthorizedCount++;//increment
                }
            }
            //stringbuilder to build string
            StringBuilder out = new StringBuilder();
            out.append("Warn logs Processed: ").append(count).append("\n");//count of logs processed
            out.append("Unauthorized access attempts: ").append(unauthorizedCount).append("\n");//count of unauthorized attempts
            System.out.println(out);//print to console
        }catch(InterruptedException e){//if thread was interrupted while waiitgn
            Thread.currentThread().interrupt();//restore interrupted status
        }
    }
}
