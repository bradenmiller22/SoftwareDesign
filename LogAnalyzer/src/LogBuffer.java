import java.util.concurrent.ArrayBlockingQueue;

/**
 * LogBuffer class provides shared blocking queues for different logs
 * Container for holding buffered logs to be processed
 * @author bmiller38
 */
public class LogBuffer {
    /**
     * Constant used as a signal to indicate the end of processing in buffers
     */
    public static final String end = "Done";
    //arrayblockingqueue is bounded blocking queue that stores elements in array
    //when full (10) any attempt to add elements will block until space is available
    /**
     * Array Blocking Queue implements a blocking queue that is first in first out approach
     * InfoBuffer is of size 10 so once 10 elements are in the array will block until a position opens
     */
    public final ArrayBlockingQueue<String> infoBuffer = new ArrayBlockingQueue<>(10);
    /**
     * WarnBuffer is a array blocking queue that has a max of 10 elements to be stored in the queue
     */
    public final ArrayBlockingQueue<String> warnBuffer = new ArrayBlockingQueue<>(10);
    /**
     * ErrorBuffer is a array blocking queue that has a max of 10 elements to be stored in the queue
     */
    public final ArrayBlockingQueue<String> errorBuffer = new ArrayBlockingQueue<>(10);
}
