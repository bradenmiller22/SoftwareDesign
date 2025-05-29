import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Twister class simulates the popular game twister
 * Has a spin button to generate a random move from a possible of 16 total
 * Can activate bias by pressing the key 'B' to generate hard moves
 *
 * @author bmiller38
 */
public class Twister extends JFrame {
    /**
     * Shows the spin button to spin a new move
     */
    private JButton spinButton;
    /**
     * Shows the main color and move
     */
    private JPanel mainPanel;
    /**
     * Shows what move was spun
     */
    private JLabel displayLabel;
    /**
     * New random variable
     */
    private Random random = new Random();
    /**
     * Array of side for left or right
     */
    private final String[] sides = {"Right", "Left"};
    /**
     * Array of body parts
     */
    private final String[] bodyPart = {"Hand", "Foot"};
    /**
     * Array of colors
     */
    private final String[] colors = {"Red", "Green", "Blue", "Yellow"};
    /**
     * 2d array to keep track of biased moves
     */
    private final String[][] biasMoves = {
            {"Right", "Foot", "Red"},
            {"Left", "Foot", "Red"},
            {"Right", "Hand", "Green"},
            {"Left", "Hand", "Green"},
            {"Left", "Foot", "Green"}
    };
    private boolean bias = false;//tracker for bias

    /**
     * Constructor that initializes the swing twister GUI
     * Sets up button listener and key typed listener
     * Shows the functionality of the twister game
     */
    public Twister(){
        super("Twister Game");
        setLayout(new BorderLayout());

        //create component for button
        spinButton = new JButton("Spin");
        spinButton.addActionListener(e -> spin());//has a spin function

        add(spinButton, BorderLayout.NORTH);//add button

        displayLabel = new JLabel("Waiting", SwingConstants.CENTER);//display for the text

        mainPanel = new JPanel(new BorderLayout());//main panel for color/text from spin
        mainPanel.add(displayLabel, BorderLayout.CENTER);//add the text to center
        mainPanel.setBackground(Color.WHITE);//white background initially

        add(mainPanel, BorderLayout.CENTER);//add to overall

        mainPanel.setFocusable(true);
        mainPanel.addKeyListener(new KeyAdapter() {//adds a key listener to mainpanel waiting for b to pressed
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == 'b' || e.getKeyChar() == 'B'){//check if B or b pressed
                    bias = !bias;//switch to opposite of what it currently is
                    String yes;
                    if(bias){//bias check
                        yes = "Bias On";
                    }else{
                        yes = "Bias Off";
                    }
                    //display text message for bias
                    JOptionPane.showMessageDialog(Twister.this, yes, "Bias Toggled", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        SwingUtilities.invokeLater(() -> mainPanel.requestFocusInWindow());//shift focus back to mainpanel
    }

    /**
     * Spin method is called upon when the spin button is clicked
     * It sets the text field to a random body part and left or right
     * Sets the color to a random color
     */
    private void spin(){
        String side;
        String body;
        String color;

        if (bias && random.nextDouble() < 0.8) {
            String[] hardMove = biasMoves[random.nextInt(biasMoves.length)];//random list of biased moves
            side = hardMove[0];//first part is left or right
            body = hardMove[1];//second part is body part
            color = hardMove[2];//third part is color rgby
        }else {
            side = sides[random.nextInt(sides.length)];//get random left or right
            body = bodyPart[random.nextInt(bodyPart.length)];//get random foot or hand
            color = colors[random.nextInt(colors.length)];//random color r,g,b,y
        }
        displayLabel.setText(side + " " + body);//display the left or right and then body part

        switch (color){//go through colors and set background to color
            case "Red":
                mainPanel.setBackground(Color.RED);
                break;
            case "Green":
                mainPanel.setBackground(Color.GREEN);
                break;
            case "Blue":
                mainPanel.setBackground(Color.BLUE);
                break;
            case "Yellow":
                mainPanel.setBackground(Color.YELLOW);
                break;
        }
        mainPanel.requestFocusInWindow();//back to main panel after spin
    }
}
