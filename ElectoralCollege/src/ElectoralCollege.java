//import the swing package which allows graphical user interfaces (GUIs)
//includes JFrame, JRadioButton, JButton, JTextField, JComboBox, JLabel
import javax.swing.*;
//import FlowLayout that arranges components in a left-to-right flow and wrapping to next line if needed
import java.awt.FlowLayout;
//import ActionEvent to handle events triggered by user action of clicking buttons
import java.awt.event.ActionEvent;
//import ActionListener to define what happens when a user does perform action of clicking the button
import java.awt.event.ActionListener;

/**
 * The ElectoralCollege class simulates an electoral college voting system.
 * It allows a user to select a state, vote for a party, and then tracks the votes.
 * Once 270 votes are hit it displays the winning party.
 * @author bmiller38
 */
public class ElectoralCollege extends JFrame {
    //radio buttons allow a user to select and deselect a button
    private JRadioButton democratButton;//radio button for democrat party
    private JRadioButton republicanButton;//radio button for republican party
    private JRadioButton undecidedButton;//radio button for undecided party
    private JButton submitButton;//push button for submitting the vote
    private JTextField winningField;//displays a text field for the winning party
    private JComboBox<String> stateBox;//dropdown box to select state
    //variables to track votes
    private int democratVotes = 0;//total votes for democrats
    private int republicanVotes = 0;//total votes for republican
    private int undecidedVotes = 538;//total votes for undecided
    private ButtonGroup voteGroup;//button group to only allow one radio button to be selected ata time
    //static final as these arrays represent constant data that is shared across all instances of the class
    //static because the variables belong to the class itself rather than any specific instance of the class
    //final because the states and votes are never going to change during execution of the program
    private static final String[] states = {"", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
    "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana",
    "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine Overall", "Maine 1", "Maine 2", "Maryland", "Massachusetts",
    "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska Overall", "Nebraska 1", "Nebraska 2",
    "Nebraska 3", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
    "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
    "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};//all states
    private static final int[] stateVotes = {0, 9,3,11,6,54,10,7,3,3,30,16,4,4,19,11,6,6,8,8,2,1,1,10,11,15,
    10,6,10,4,2,1,1,1,6,4,14,5,28,16,3,17,7,8,19,4,9,3,11,40,6,3,13,12,4,10,3};//votes corresponding to states

    private String[] previousVotes;//string array to track previous votes for states

    /**
     * Constructor for the ElectoralCollege class.
     * Initializes the GUI components, sets the layout, and adds event listeners
     */
    public ElectoralCollege() {
        super("Electoral College Simulator");//sets the title of the frame(box)
        //arranges buttons horizontally until no more buttons fit on that same line
        setLayout(new FlowLayout());//use flowlayout for the layout manager

        //initialize previous votes to length of states
        previousVotes = new String[states.length];
        for(int i =0; i < previousVotes.length; i++){
            previousVotes[i] = "Undecided";//set each previous vote as empty for all states
        }

        //create a dropdown (JComboBox) for selecting states
        stateBox = new JComboBox<>(states);
        add(new JLabel("         Select State:     "));//add a label for the dropdown (spaces are used for formatting across the line)
        add(stateBox);//add the state dropdown

        //create radio buttons for each party
        democratButton = new JRadioButton("Democrat");
        republicanButton = new JRadioButton("Republican");
        undecidedButton = new JRadioButton("Undecided");

        //create logical relationship between buttons to ensure only one can be selected at a time
        //means selecting one button will deselect all other buttons
        voteGroup = new ButtonGroup();//create button group
        voteGroup.add(democratButton);
        voteGroup.add(republicanButton);
        voteGroup.add(undecidedButton);

        //add labels and radio buttons to the frame
        //will appear after the dropdown for selecting a state
        add(new JLabel("Select Vote: "));
        add(democratButton);
        add(republicanButton);//add them to the jframe
        add(undecidedButton);

        //create the click button to click on when submitting votes for the selected state
        submitButton = new JButton("Submit Vote");
        add(submitButton);//add it to the jframe

        //create a text field that is not editable to display winning party
        winningField = new JTextField(17);//length of the field (roughly 2 characters per 1 col)
        winningField.setEditable(false);//cannot edit this text field
        add(new JLabel("Votes:"));//display label next to winning field
        add(winningField);//add to jframe

        //add an ActionListener to the submit button to update votes when it is clicked
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //make sure a radio button is actually selected
                        if(republicanButton.isSelected() || democratButton.isSelected() || undecidedButton.isSelected()) {
                            updateVotes();//call the updateVotes method when the button is clicked
                        }
                    }
                }
        );
        updateVotes();//initialize the winning party display when the program starts
    }

    /**
     * Updates the vote count based on selected state and party.
     * Adjusts the total votes for each party and updates the winning party display.
     */
    private void updateVotes(){
        int stateIndex = stateBox.getSelectedIndex();//get index of selected state
        int vote = stateVotes[stateIndex];//get the votes from that selected state

        //remove previous votes from the selected state (if any)
        String previousVote = previousVotes[stateIndex];
        switch(previousVote){//switch statement to determine what the state held
            case "Democrat":
                democratVotes -= vote;//subtract vote from democrat
                break;
            case "Republican":
                republicanVotes -= vote;//subtract vote from republican
                break;
            case "Undecided":
                undecidedVotes -= vote;//subtract vote from undecided
                break;
        }

        //add new vote based on party selected
        if(democratButton.isSelected()){//if democrats
            democratVotes += vote;//add it to democrat total
            previousVotes[stateIndex] = "Democrat";//previous vote for this selected state is now democrat
        }else if(republicanButton.isSelected()){//if republicans
            republicanVotes += vote;//add it to republican total
            previousVotes[stateIndex] = "Republican";//previous vote for this selected state is now republican
        } else if(undecidedButton.isSelected()){//if undecided
            undecidedVotes += vote;//add it to undecided total
            previousVotes[stateIndex] = "Undecided";//previous vote for this selected state is now undecided
        }//no else because if the submit button is selected and no party button is selected it wont reach the updateVotes routine

        //check everytime submit button is pushed if the votes have reached the winning threshold
        if(democratVotes >= 270){//democrats win if they reach 270
            winningField.setText("Democrat Wins!!");//update winningField text box to democrat wins
        } else if(republicanVotes >= 270){//republicans win if they reach 270
            winningField.setText("Republican Wins!!");//update winningField text box to republican wins
        }else{//if 270 votes has not been reached yet continuously display to winningField no winning party
            winningField.setText("Dem: " + democratVotes + " Rep: " + republicanVotes + " Und: "+undecidedVotes);//if a party hasn't won yet then remain at no winning party
        }
    }
}
