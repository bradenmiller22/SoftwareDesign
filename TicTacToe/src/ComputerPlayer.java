import java.util.List;

/**
 * ComputerPlayer class represents a computer player in the game
 * Extends the Player class and provides method for random move
 *
 * @author bmiller38
 */
public class ComputerPlayer extends Player{
    /**
     * Constructs a new ComputerPlayer with specified letter
     * @param letter the letter representing the player ("X" or "O")
     */
    public ComputerPlayer(char letter){
        super(letter); //call superclass constructor to set players letter
    }

    /**
     * Gets a random valid move from list of available moves
     * @param board the current state of game board
     * @return <code>int[]</code> representing the computers move row, col
     */
    @Override
    public int[] getMove(Board board) {
        List<int[]> validMoves = board.validMove(); //get the list of valid moves as pairs ex. [(0,0), (0,1), (1,2)]
        int rand = (int) (Math.random() * validMoves.size()); //convert to a random integer from [0, validMoves.size()-1]
        return validMoves.get(rand);//return a random index of validMoves to get a random move
    }

    /**
     * Returns a string representation of the computer player
     * @return <code>String</code> representation of the computer player
     */
    @Override
    public String toString() {
        return super.toString(); //use the superclass toString method
    }
}
