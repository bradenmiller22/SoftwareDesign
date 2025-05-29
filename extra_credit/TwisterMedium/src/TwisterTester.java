import javax.swing.*;
/**
 * TwisterTester Class serves as the driver for Twister application.
 * Creates instance of Twister class, sets window properties, and makes window visible.
 *
 */
public class TwisterTester {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() ->{//swing utilities ensures events happen in event dispatch thread, so queues events in order to execute
                Twister twisterFrame = new Twister();
                //set the default close operation for JFrame
                //ensures that application exits when the user closes the window
                twisterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                twisterFrame.setSize(250, 200);//set size of frame to 418x200
                twisterFrame.setLocationRelativeTo(null);//center on screen of user
                twisterFrame.setVisible(true);//make frame visible to user, if false it would be created but not displayed
            });
        }
}
