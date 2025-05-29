import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
public class Server {
    /**
     * Port number for networking.
     */
    private static final int PORT_NUM = 23630;
    /**
     * Max number of players in the game is 2.
     */
    public static final int MAX_PLAYERS = 4;
    /**
     * Executor service with 1 thread for each player. Total threads allocated = 4.
     */
    private static ExecutorService runGame = Executors.newFixedThreadPool(MAX_PLAYERS);

    /**
     * Array of Players. (Game begins when 2 players are connected to the server).
     */
    public static Player[] playerArray = new Player[MAX_PLAYERS]; //holds Players

    /**
     * Tracks the number of players who have joined.
     */
    public static int playerCount = 0; //track number of players joined

    /**
     * Track whose turn it is.
     */
    public static int currentPlayerTurn = 0;

    /**
     * A lock to ensure mutual exclusion when accessing and modifying the currentPlayerTurn variable. This ensures that it is clear whose turn it is.
     */
    public static final ReentrantLock turnLock = new ReentrantLock();

    /**
     * A condition variable associated with the turn lock that is used to make threads wait until it is their turn, and to signal to the next player's turn.
     */
    public static final Condition turnCondition = turnLock.newCondition();

    /**
     * Main driver that runs and handles the Connect 4 server for players to connect to.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUM)) {
            System.out.println("Connect 4 Server is running on port " + PORT_NUM + ".....");

            System.out.println("Waiting for 1st player...");

            //add first player
            Socket socket = serverSocket.accept();
            Player player1 = new Player(socket, playerCount, true); //create the first player.
            playerArray[playerCount] = player1; //add the first player to the array of players
            playerCount++;
            runGame.execute(player1);

            System.out.println("Player " + playerCount + " connected.");

            //TODO: Prompt player 1 to enter in the number of users they wish to play the game (up to 4 players total)

            while (playerCount < MAX_PLAYERS) {
                Socket socketForAdditionalPlayers = serverSocket.accept(); //socket for adding player 2, 3, and 4.
                Player player = new Player(socketForAdditionalPlayers, playerCount, false); //creates new player
                playerArray[playerCount] = player; //adds player to the array of players
                playerCount++;
                runGame.execute(player); //runs the game for the player
                System.out.println("Player " + playerCount + " connected.");

                //notify all players of the current player count
                for(Player p : playerArray){
                    if(p != null && p.getOutputReference() != null){
                        p.getOutputReference().println("CONNECTED|" + playerCount);
                    }
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            runGame.shutdown();
        }
    }
}
