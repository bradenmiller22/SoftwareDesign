import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * InfoAnalyzer class processes information logs from shared buffer
 * Counts total logs and login information from users
 *
 * @author bmiller38
 */
public class InfoAnalyzer implements Runnable{
    //blockingqeueu provides thread-safe adding and removing
    /**
     * BlockingQueue provides a thread safe way of adding and removing from a queue
     */
    private BlockingQueue<String> buffer;//buffer containing info logs to process
    /**
     * Count to keep track of the total processed logs
     */
    private int count = 0;//count to track number of processed logs
    /**
     * Hashmap to track the number of logins per user group by
     */
    private Map<String, Integer> login = new HashMap<>();//track number of logins for each user group

    /**
     * Constructor to initialize infobuffer to read from
     * @param buffer blocking queue containing logs to process
     */
    public InfoAnalyzer(BlockingQueue<String> buffer){
        this.buffer = buffer;
    }

    /**
     * Reads in logs from buffer, counts them and tracks login information
     * Terminates when it gets "Done" from buffer
     */
    @Override
    public void run() {
        try{
            while(true){
                String log = buffer.take();//waits until a log is available in the queue to take
                if(log.equals(LogBuffer.end)){//check if "done"
                    break;//exit loop
                }
                count++;//increment a processed log counter
                if(log.contains("logged in")){//check for login information
                    String[] part = log.split(": ");//split at : to get username
                    if(part.length > 1){
                        String user = part[1];//get username from second part of the string
                        login.put(user, login.getOrDefault(user, 0) + 1);//increments count of total username processed using keys, (user123 is a key, will increase each time)
                    }
                }
            }
            //use stringbuilder for printing to console
            StringBuilder out = new StringBuilder();
            out.append("Info logs processed: ").append(count).append("\n");//total info logs processed
            out.append("Login Information: \n");//print the user log in information
            for(String user : login.keySet()){//go through each "key" (user) in the map and print the total logins for them
                out.append(user).append(": ").append(login.get(user)).append(" logins\n");//.get gets the value of the key
            }
            System.out.println(out);//print the string builder
        }catch (InterruptedException e){//if thread was interrupted during wait from .take
            Thread.currentThread().interrupt();//restore interrupt status
        }
    }
}
