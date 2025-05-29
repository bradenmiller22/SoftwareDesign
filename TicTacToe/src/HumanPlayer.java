import java.util.Scanner;

/**
 * HumanPlayer class represents a human player in the game
 * It extends the Player class and provides method to get the players move
 * @author bmiller38
 */
public class HumanPlayer extends Player{
    private Scanner scanner; //scanner object to read user input

    /**
     * Constructs a new HumanPlayer with specific letter
     * @param letter the letter representign the player ("X" or "O")
     */
    public HumanPlayer(char letter){
        super(letter);//call the superclass constructor to set players letter
        scanner = new Scanner(System.in); //initialize the scanner
    }

    /**
     * Gets the players move from the user input
     * @param board the current state of the game board
     * @return <code>int[]</code> representing the players move row, col
     */
    @Override
    public int[] getMove(Board board) {
        int[] move = new int[2];//array to store the move row, col
        boolean validMove = false; //check if the move is available
        while(!validMove){//loop forever till a valid move
            while(true) {//loop to verify valid input
                try {
                    System.out.println("Enter your move, (row[0-2] and col[0-2]) seperated with a space: ");
                    move[0] = scanner.nextInt(); //read row
                    move[1] = scanner.nextInt();//read col
                    //check if row is valid 0-2 and col is valid 0-2
                    if(move[0] >=0 && move[0] <=2 && move[1] >= 0 && move[1] <=2){
                        break;//exit user input loop if valid
                    }else{//prompt user to input another number
                        System.out.println("Invalid input, please enter a number for row (0-2) and number for col (0-2). ");
                    }
                }catch (Exception invalid){//throw exception instead of error if wrong input type
                    System.out.println("Invalid input, please enter a number for row (0-2) and number for col (0-2).");
                    scanner.next();//clear both previous invalidinputs
                    scanner.next();
                }
            }//exit user input loop

            for(int[] valid : board.validMove()) {//check if the move is available
                if (valid[0] == move[0] && valid[1] == move[1]) { //keeps user input in move, then goes through each row and col of available moves
                    validMove = true; //marks true if move is valid
                    break; //if move is found then exit for each loop
                }
            }
            if(!validMove) {
                System.out.println("Invalid move, try again!"); //if not available then ask for another move
            }
        }
        return move; //return valid move
    }

    /**
     * Reutrns a string representation of the human player
     * @return <code>String</code> representation of the human player
     */
    @Override
    public String toString() {
    return super.toString(); //use superclass toString method
    }
}
