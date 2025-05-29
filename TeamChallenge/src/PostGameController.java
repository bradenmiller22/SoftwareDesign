import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 * PostGameController extends JPanel and is used to control the GUI displayed post game.
 */
public class PostGameController extends JPanel {
    private int counter;
    private final String[] rankings = new String[3];
    private PrintWriter clientOutput;
    private final JTextArea chatArea;
    private final JTextField chatTextField;
    private final JLabel firstPlace;
    private final JLabel secondPlace;
    private final JLabel thirdPlace;

    /**
     * Constructor for PostGameController, initializes the GUI used following the game
     */
    public PostGameController(){
        Font rankFont = new Font("SansSerif", Font.BOLD, 18); //create font
        counter = 0;
        setLayout(new BorderLayout());

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        firstPlace = new JLabel();
        firstPlace.setAlignmentX(Component.CENTER_ALIGNMENT); //center component horizontally
        firstPlace.setFont(rankFont);

        secondPlace = new JLabel();
        secondPlace.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondPlace.setFont(rankFont);

        thirdPlace = new JLabel();
        thirdPlace.setAlignmentX(Component.CENTER_ALIGNMENT);
        thirdPlace.setFont(rankFont);

        firstPlace.setForeground(new Color(246, 246, 0, 255));  //gold
        secondPlace.setForeground(new Color(192, 192, 192)); //silver
        thirdPlace.setForeground(new Color(205, 127, 50));   //bronze

        firstPlace.setBorder(new EmptyBorder(10, 20, 10, 20));  //padding
        secondPlace.setBorder(new EmptyBorder(10, 20, 10, 20));
        thirdPlace.setBorder(new EmptyBorder(10, 20, 10, 20));

        westPanel.setBackground(Color.BLACK); //black background
        westPanel.add(firstPlace); //add subpanels to panel
        westPanel.add(secondPlace);
        westPanel.add(thirdPlace);

        chatArea = new JTextArea(20,15);
        chatArea.setEditable(false);

        chatTextField = new JTextField();
        add(chatTextField, BorderLayout.NORTH);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
        chatTextField.addActionListener(
                new ActionListener() {
                    /**
                     * Action listener for text field. Sends message to server and clears text field.
                     * @param e the event to be processed
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String message = chatTextField.getText();
                            sendMessage(message);
                            chatTextField.setText("");
                        }
                        catch (Exception x){
                            x.printStackTrace();
                        }

                    }
                }
        );

        chatArea.append("Welcome to the post-game chat room!\n");
    }

    /**
     * Set output stream of post game controllere
     * @param output PrintWriter object
     */
    public void setOutput(PrintWriter output){
        this.clientOutput = output;
    }

    /**
     * Send message to server.
     * @param message String message to be sent
     */
    public void sendMessage(String message){
        if(clientOutput != null) {
            clientOutput.println("CHAT|" + message);
            clientOutput.flush();
        }
    }

    /**
     * Append message to chat text area for conversing.
     * @param message String to display
     */
    public void displayMessage(String message){
        chatArea.append(message + "\n");
    }

    /**
     * set rankings to display post game
     * @param ranking String with player name
     */
    public void setRanking(String ranking){
        if (counter == 0) {
            rankings[0] = ranking;
            firstPlace.setText(rankings[0]);
        } else if (counter == 1) {
            rankings[1] = ranking;
            secondPlace.setText(rankings[1]);
        } else if (counter == 2) {
            rankings[2] = ranking;
            thirdPlace.setText(rankings[2]);
        }
        counter++;
    }

}

