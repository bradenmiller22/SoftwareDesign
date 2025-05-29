import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Random;

/**
 * LobbyController extends JPanel and controls the pre game lobby GUI
 */
public class LobbyController extends JPanel {
    private final JButton startButton;
    private boolean isLeader = false;
    private final JLabel playerCountLabel;
    private PrintWriter clientOutput;
    private static final int MAX_PLAYERS = 4;
    private final JComboBox<String> gridBox;
    private final JComboBox<String> winSize;
    private final BackgroundController background;
    private String[] backgroundPaths = {"/lion.jpg", "/monkey.jpg", "/bear.jpg", "/cat.jpg", "/chicken.jpg", "/dog.jpg"};
    //Photo by <a href="/photographer/btklamf-42547">btklamf</a> on <a href="/">Freeimages.com</a>
    //Photo by <a href="/photographer/jonathan_n-58973">jonathan_n</a> on <a href="/">Freeimages.com</a>

    private final Random randomGenerator = new Random();

    /**
     * Constructor for LobbyController. Creates GUI with random background image.
     */
    public LobbyController() {
        String path = backgroundPaths[randomGenerator.nextInt(backgroundPaths.length)];
        background = new BackgroundController(path); //background panel

        JPanel centerPanel = new JPanel(new GridBagLayout()); //panel for player count and start button
        centerPanel.setOpaque(false);
      //  centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); //box layout for centerPanel, stack components vert. in panel

        playerCountLabel = new JLabel("Waiting for players..."); //label for player count
        playerCountLabel.setHorizontalAlignment(SwingConstants.CENTER); //center text horizontally
        playerCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //set label in center of panel

        startButton = new JButton("Start Game!"); //start button
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); //center text
        startButton.setEnabled(false); //disable ability to press

        gridBox = new JComboBox<>(new String[]{
                "6 x 7",
                "8 x 8",
                "9 x 10",
                "11 x 11"
        });
        gridBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        gridBox.setEnabled(false);

        winSize = new JComboBox<>(new String[]{
                "2 in a row",
                "3 in a row",
                "4 in a row"
        });
        winSize.setAlignmentX(Component.CENTER_ALIGNMENT);
        winSize.setEnabled(false);

        Dimension boxSize = new Dimension(120,25);
        gridBox.setPreferredSize(boxSize);
        winSize.setPreferredSize(boxSize);
        startButton.setPreferredSize(boxSize);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER,5,0));
        buttonRow.setOpaque(false);
        buttonRow.add(gridBox);
        buttonRow.add(startButton);
        buttonRow.add(winSize);
        centerPanel.add(buttonRow);

        background.add(centerPanel); //add center panel to background panel

        setLayout(new BorderLayout()); //set GUI layout to border
        add(background, BorderLayout.CENTER); //place background panel in center
        add(playerCountLabel,BorderLayout.NORTH);

        startButton.addActionListener(


                new ActionListener() {
                    /**
                     * Action Listener for Start button. Starts game with specified board size and win conditions.
                     * @param e the event to be processed
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (isLeader && clientOutput != null) { //ignore mouse click if not group leader
                            String selectedSize = (String) gridBox.getSelectedItem();
                            String selectedWin = (String) winSize.getSelectedItem();

                            String size = "CLASSIC";
                            if(selectedSize.contains("8 x 8")) size = "MEDIUM";
                            else if(selectedSize.contains("9 x 10")) size = "LARGE";
                            else if(selectedSize.contains("11 x 11")) size = "JUMBO";

                            String win = "4";
                            if(selectedWin.contains("2")) win = "2";
                            else if(selectedWin.contains("3")) win = "3";

                            clientOutput.println("START|"+size+"|"+win); //send START
                            clientOutput.flush();
                            startButton.setEnabled(false); //disable button
                        }
                    }
                }
        );
    }

    /**
     * SetClientOutput used to set the PrintWriter object;
     * @param output PrintWriter object to be set to
     */
    public void setClientOutput(PrintWriter output) {
        this.clientOutput = output; //get output stream from client
    }

    /**
     * setLeader method. Controls leader Boolean and ability to start game.
     * @param leader
     */
    public void setLeader(boolean leader) {
        isLeader = leader;
        startButton.setEnabled(isLeader); //allow to click start button if leader
        gridBox.setEnabled(isLeader);
        winSize.setEnabled(isLeader);
    }

    /**
     * UpdatePlayerCount updates the GUI panel displaying how many players there are
     * @param count
     */
    public void updatePlayerCount(int count) {
        playerCountLabel.setText(count + " / " + MAX_PLAYERS + " players connected. \n\n"); //update text in label when new players
    }
}
