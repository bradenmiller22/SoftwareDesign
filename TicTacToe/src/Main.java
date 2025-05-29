import java.util.Scanner;

/**
 * Main driver class to implement the game
 * Allows user to select game mode and input moves
 *
 * @author bmiller38
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //create a scanner object to read in user input
        int choice = 0;//variable to store user input on specific game mode to be played

        while(true){ //loop until the user enters a valid choice of game mode to play (1,2,3)
            try{
                System.out.println("Choose mode (enter number 1-3): \n1. Player vs. Player \n2. Player vs Computer \n3. Computer vs. Computer");
                choice = scanner.nextInt(); //read the users input
                if(choice >=1 && choice <= 3){ //check to see its 1,2,3
                    break; //exit the while loop if the choice is valid
                }
                else{ //if not valid then ask user again and restart the loop
                    System.out.println("Invalid input, please enter a number between 1-3. ");
                }
            } catch (Exception invalid){ //catch invalid input from throwing an error during runtime like a letter
                System.out.println("Invalid input, please enter a number between 1-3");
                scanner.next(); //clear the current input and go to next user input
            }
        }

        Player p1; //store first player
        Player p2; //store second player
        if(choice == 1) { //check user input to determine type of game to play
            p1 = new HumanPlayer('X'); //human v human
            p2 = new HumanPlayer('O');
        } else if(choice == 2) {
            p1 = new HumanPlayer('X'); //human v computer
            p2 = new ComputerPlayer('O');
        }else {
            p1 = new ComputerPlayer('X'); //computer v computer
            p2 = new ComputerPlayer('O');
        }

        Board board = new Board(); //create a new game baord
        Player currentPlayer = p1; //set the player to be tracked from first player (could be human or computer "player" )

        while(true){//main game loop
            System.out.println(board); //print the current board (calls the board toString method)
            //Polymorphic call to get the currentplayers move (human or computer) and store it in move[row, col]
            int[] move = currentPlayer.getMove(board);
            //use the move that was gathered and the currentplayers letter to make the move
            board.makeMove(move, currentPlayer.getLetter());

            char winner = board.checkWin(currentPlayer.getLetter()); //check if the currentplayer has won,returns letter if so else ' '
            if(winner != ' '){//if winner isnt blank then this displays the winner of the game and stops the program
                System.out.println(board); //print board
                System.out.println(currentPlayer + " wins!"); //announce winner using players toString
                break; //exit loop
            }

            if(board.boardFull()){ //check if board is full (a tie)
                System.out.println(board);//print the board
                System.out.println("Its a tie"); //tell user a tie
                break;//exit loop
            }

            //check what currentplayer is currently and then switch to other player to "restart" the loop and get next player move
            if(currentPlayer == p1){
                currentPlayer = p2;
            }else {
                currentPlayer = p1;
            }
        }
    }
}