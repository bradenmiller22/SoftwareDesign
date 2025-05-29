import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * LogProcessorTest class is the main driver
 * Coordinates creation of executor service, reads input file, and sends to shared buffer
 * @author bmiller38
 */
public class LogProcessorTest {
    /**
     * Main method to start application
     * @param args Command line argument
     * @throws Exception if any error occur during file reading/thread execution
     */
    public static void main(String[] args) throws Exception{
        //ExecutorService manages a pool of threads
        //newFixedThreadPool specifies 100 threads to be made
        //allows for control of number of threads
        ExecutorService executor = Executors.newFixedThreadPool(100);
        LogBuffer logBuffer = new LogBuffer();//create a shared buffer for logs

        //execute submits a Runnable execution in thread pool
        executor.execute(new ErrorAnalyzer(logBuffer.errorBuffer));//error analyzer thread
        executor.execute(new WarnAnalyzer(logBuffer.warnBuffer));//warn analyzer thread
        executor.execute(new InfoAnalyzer(logBuffer.infoBuffer));//info analyzer thread

        //reads text from character input stream with buffering
        BufferedReader reader = new BufferedReader(new FileReader("oral_exam2/LogAnalyzer/large_log_file.log"));//reads in the log file
        List<String> group = new ArrayList<>();//create a group of logs to process
        String line;

        while((line = reader.readLine()) != null){//read file line by line
            group.add(line);//add log (line) to the group
            if(group.size() == 100){//when group reaches 100 logs
                //process in groups of 100
                executor.execute(new LogReader(group, logBuffer));//process batch(group)
                group = new ArrayList<>();//reset the group
            }
        }
        //if it didnt reach 100, then go through those
        if(!group.isEmpty()) {
            executor.execute(new LogReader(group, logBuffer));
        }
        //shut down the executor service - no new tasks accepted
        executor.shutdown();
        //wait up to 3 seconds for all tasks to complete
        executor.awaitTermination(3, TimeUnit.SECONDS);

        //send termination signals to analyzer threads
        logBuffer.errorBuffer.put(LogBuffer.end);//add element to queue to read being Done
        logBuffer.warnBuffer.put(LogBuffer.end);
        logBuffer.infoBuffer.put(LogBuffer.end);

    }
}
