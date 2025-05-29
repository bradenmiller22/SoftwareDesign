//import JFrame class which is used to create the main window of application
import javax.swing.JFrame;

/**
 * Main Class serves as the driver for ElectoralCollege application.
 * Creates instance of ElectoralCollege class, sets window properties, and makes window visible.
 */
public class Main {
    public static void main(String[] args) {
        //create an instance of ElectoralCollege class which initializes GUI components and sets up the simulation
        ElectoralCollege electoralCollegeFrame = new ElectoralCollege();
        //set the default close operation for JFrame
        //ensures that application exits when the user closes the window
        electoralCollegeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        electoralCollegeFrame.setSize(418, 200);//set size of frame to 418x200
        electoralCollegeFrame.setVisible(true);//make frame visible to user, if false it would be created but not displayed
    }
}