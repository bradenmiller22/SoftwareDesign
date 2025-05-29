import javax.swing.JFrame;

/**
 * Client Test starts the client by setting up the client window
 * Allows for file interaction with server
 * @author bmiller38
 */
public class ClientTest
{
    public static void main(String[] args)
    {
        Client application = new Client(); // create client instance
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close when client GUI is closed

    }
}