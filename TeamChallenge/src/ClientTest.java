import javax.swing.*;

/**
 * Creates and launches Client with specified IP
 */
public class ClientTest {
    public static void main(String[] args) {
        Client application; // declare client application
        if (args.length == 0) {// if no command line args
            application = new Client("128.255.17.149"); // connect to localhost
        }else {
            application = new Client(args[0]); // use args to connect
        }
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runClient(); // run client application
    }
}