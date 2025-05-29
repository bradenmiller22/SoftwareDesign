import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/**
 * The ErrorAnalyzer class processes error logs from a shared buffer and writes them to file
 * Implements Runnable to allow execution in thread
 *
 * @author bmiller38
 */
public class ErrorAnalyzer implements Runnable{
    /**
     * Blocking queue provides a thread safe way of adding and removing from a queue
     * Buffer of max 10 that contains error logs to process
     */
    private BlockingQueue<String> buffer;
    /**
     * Total count of error logs processed
     */
    private int count = 0;//counter to track number of error logs processed

    /**
     * Constructor for ErrorAnalyzer with specified buffer to read from
     * @param buffer The blocking queue containing error logs
     */
    public ErrorAnalyzer(BlockingQueue<String> buffer){
        this.buffer = buffer;//this is a ref to buffer above
    }

    /**
     * Runs error analysis, reading error logs from the buffer
     * Writes them to a file and then counts them, terminating when it reads "Done"
     */
    @Override
    public void run() {
        //in a try to attempt to ensure valid writing to error file
        //FileWriter - writes to a file with the path
        //PrintWriter - allows for writing formatted text to a file
        try(PrintWriter writer = new PrintWriter(new FileWriter("oral_exam2/LogAnalyzer/errors_only.log"))){
            while(true){//contiosly go through until it reaches the end of file
                String log = buffer.take();//blocking operation that waits until an element is available and removes it from the queue
                if(log.equals(LogBuffer.end)){//check for "Done" (termination)
                    break;//exit loop
                }
                writer.println(log);//write error log to output file using printwriter method
                count++;//increment count of processed logs
            }
            System.out.println("Error logs processed: " + count);//print final error count
        //catch file error and if thread was interrupted
        }catch(IOException | InterruptedException e){//catch if thread was interrupted
            Thread.currentThread().interrupt();//restore interrupted status flag
            System.out.println("Error");//error notification
        }
    }
}
