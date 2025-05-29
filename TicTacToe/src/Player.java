/**
 * The player class is an abstract representation of a player in a game of tictactoe
 * It provides base methods to get the letter of a player and to get a players move
 * @author bmiller38
 */
public abstract class Player {
    private char letter; //represents the players character ("X" or "O")

    /**
     * Constructs a new Player with the specified letter
     * @param letter representing the player "X" or "O"
     */
    public Player(char letter){
        this.letter = letter; //initialize the players letter (called from subclasses using super())
    }

    /**
     * Get the players letter
     * @return <code>char</code> representing the players letter
     */
    public char getLetter(){
        return letter; //return the players letter
    }

    /**
     * Sets the players letter
     * @param letter the letter to set of the player
     */
    public void setLetter(char letter) {
        this.letter = letter; //update players letter to specific choice
    }

    /**
     * Abstract method to get the players move
     * @param board the current state of the game board
     * @return <code>int[]</code> representing the players move (Row, Col)
     */
    public abstract int[] getMove(Board board);//int list of move (row, col): Must be implemented by subclasses for functionality

    /**
     * Returns a string representation of the player
     * @return <code>String</code> formatted string of Player "X" or Player "O"
     */
    @Override
    public String toString() {
        return "Player " + letter;
    }
}
