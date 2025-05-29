import java.util.ArrayList;
import java.util.List;

/**
 * The Board class is a representation of the game board.
 * A 3x3 board where it tracks moves made, checks for a win, and if its full.
 *
 * @author bmiller38
 */
public class Board {
    private char[][] gameBoard; //2D arrary to represent the game board ex {{x,x,x}, {o,o,o}, {x,o,x}}

    /**
     * Construct a new gameboard to hold the game
     * Makes a 3x3 character array
     */
    public Board(){
        gameBoard = new char[3][3]; //initialize a 3x3 board with an array
        for(int i = 0; i < 3; i++){//go row by row
            for(int j = 0; j < 3; j++){//for each row make 3 columns in it
                gameBoard[i][j] = ' ';//make game board a 3x3 of blanks (initialize it)
            }
        }
    }

    /**
     * Get the gameBoard as it currently is
     * @return <code>char[][]</code> representing the game board
     */
    public char[][] getGameBoard() {
        return gameBoard; //return the game board
    }

    /**
     * Set the game board to a specific state, could be used to "resume" a game
     * @param gameBoard holds the game being played
     */
    public void setGameBoard(char[][] gameBoard) {
        this.gameBoard = gameBoard; //update game board with values inputted
    }

    /**
     * Gets a list of valid moves that are open on the game baord
     * @return <code>List</int[]></code> representing the valid moves (row by col)
     */
    public List<int[]> validMove(){
        //instead of using array like int[10][2], arraylist allows for resizing so everytime method is called it resizes itself
        //use List of ArrayList for code encapsulation and flexibility
        List<int[]> moves = new ArrayList<>(); //create a list to store valid moves
        for(int i = 0; i < 3; i++){//row by row
            for(int j = 0; j < 3; j++){//each row gets 3 cols
                if(gameBoard[i][j] == ' '){//check if the cell is empty
                    moves.add(new int[]{i,j});//if empty then add that to valid moves, ex. [(1,2), (2,2)]=valid
                }
            }
        }
        return moves;//return list of valid moves
    }

    /**
     * Checks if the game board is full using method validMove
     * @return <code>boolean</code> true if board is full, false otherwise
     */
    public boolean boardFull(){
        return validMove().isEmpty();//return true if validMoves is empty
    }

    /**
     * Checks to see if the specified player has won the game
     * @param letter the letter representing the player ("X" or "O")
     * @return <code>char</code> representing the winning players letter or blank if no winner
     */
    public char checkWin(char letter){
        for(int i = 0; i < 3; i++){//check rows and columns for a win
            if(gameBoard[i][0] == letter && gameBoard[i][1] == letter && gameBoard[i][2] == letter){//check rows for win
                return gameBoard[i][0];//check rows first
            }
            if(gameBoard[0][i] == letter && gameBoard[1][i] == letter && gameBoard[2][i] == letter){//check columns for win
                return gameBoard[0][i];//check columns second
            }
        }
        if(gameBoard[0][0] == letter && gameBoard[1][1] == letter && gameBoard[2][2] == letter){ //diagonal win (top left->bottom right)
            return gameBoard[0][0];//return letter
        }
        if(gameBoard[0][2] == letter && gameBoard[1][1] == letter && gameBoard[2][0] == letter){//diagonal win (bottom left->top right)
            return gameBoard[0][2];
        }
        return ' ';//no winner return ' '
    }

    /**
     * Makes a move onto the game board for the player/computer
     * @param gameMove the move to make onto the baord (row and col)
     * @param letter the letter ("X" or "O") to fill in that space
     * @return <code>boolean</code> true if the move was made, false if no move was made
     */
    public boolean makeMove(int[] gameMove, char letter){
        int row = gameMove[0];//get row from move to make
        int col = gameMove[1];//get col from move to make
        if(gameBoard[row][col] == ' '){//confirm cell is empty
            gameBoard[row][col] = letter;//place the players letter in the cell
            return true; //valid move
        }
        return false; //invalid move
    }

    /**
     * Returns a string representation of the 3x3 board at its current state
     * @return <code>String</code> representation of the board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();//create a stringbuilder to build string
        for(int i = 0; i < 3; i++){//go through each row
            sb.append("| ");//start each row with |
            for(int j = 0; j < 3; j++){//3 columns per row
                sb.append(gameBoard[i][j]);//add board cells contents
                if(j < 2){//add seperators between columns 0-1 and 1-2
                    sb.append(" | ");//vertical seprators between
                }
            }
            sb.append(" |\n"); //new line done with that row
        }
        return sb.toString();//convert stringbuilder to a string
    }
}
