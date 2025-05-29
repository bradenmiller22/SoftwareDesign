import javax.swing.JFrame;

/**
 * ServerTest is the driver class for starting the server,
 * sets up Server window and waits for the client
 * @author bmiller38
 */
public class ServerTest
{
    public static void main(String[] args)
    {
        Server application = new Server(); //create server instance
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//if the window is closed then end the program
        application.waitForPackets(); //run server application and wait for client connection
    }
}