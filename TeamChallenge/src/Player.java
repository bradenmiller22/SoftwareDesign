import java.util.*;
import java.io.*;
import java.net.Socket;

/**
 * Player class represents an individual player. It holds the entirety of the game logic from the user's side. Manages the game logic based on incoming move messages sent from other players over the server.
 */
public class Player implements Runnable {
    /**
     * The socket associated with this Player for communicating with the client.
     */
    private Socket socket;

    /**
     * The player's unique number (0 to 4), used to identify turn order.
     */
    private int playerNumber;

    /**
     * Scanner to receive input from this player's client.
     */
    private Scanner input;

    /**
     * PrintWriter to send output to this player's client.
     */
    private PrintWriter output;

    /**
     * tracks if this player is the 1st to join (leader). Used for managing the start of the game.
     */
    private boolean isLeader;

    /**
     * Flag indicates if this user is done.
     */
    private boolean isDone = false;

    /**
     * holds the number of moves.
     */
    private int moveCount = 0;

    /**
     * Constructor to create a new Player which is added to the given socket and assigned a player number.
     *
     * @param socket       the socket that the server is ran on.
     * @param playerNumber unique player number used to differentiate the players.
     */
    public Player(Socket socket, int playerNumber, boolean isLeader) {
        this.socket = socket;
        this.playerNumber = playerNumber;
        this.isLeader = isLeader;

    }

    /**
     * marks this player as done with the game.
     */
    public void markAsDone(){
        this.isDone = true;
    }

    /**
     * Returns if this player is done with the game.
     * @return isDone value.
     */
    public boolean isDone(){
        return isDone;
    }

    /**
     * Returns the reference that this Player instance uses for outputting information.
     *
     * @return output reference for this player.
     */
    public PrintWriter getOutputReference(){
        return this.output;
    }

    /**
     * logic when waiting for the game to start. This waits for the leader to start the game once they've chosen grid dimensions an win condition.
     */
    private void waitForStart(){
        while(true){
            String line = input.nextLine();
            System.out.println("Received from client: " + line);

            if(line.startsWith("START|") && isLeader){
                String[] parts = line.split("\\|");
                String size;
                if(parts.length > 1){
                    size = parts[1];
                }else{
                    size = "CLASSIC";
                }
                String win;
                if(parts.length > 2){
                    win = parts[2];
                }else{
                    win = "4";
                }

                int cols, rows;

                switch(size){
                    case "MEDIUM":
                        cols = 8;
                        rows = 8;
                        break;
                    case "LARGE":
                        cols = 10;
                        rows = 9;
                        break;
                    case "JUMBO":
                        cols = 11;
                        rows = 11;
                        break;
                    case "CLASSIC":
                    default:
                        cols = 7;
                        rows = 6;
                        break;
                }
                String dimMessage = "Starting with dimensions: " + rows + "x" + cols + ". Connect " + win + " in a row to win!";

                //broadcasts the game has started
                for(Player p : Server.playerArray){
                    if(p != null && p.output != null){
                        p.output.println("NOTIFY|" + dimMessage);
                        p.output.flush();
                        p.output.println("START|" + rows + "|" + cols + "|" + win);
                        p.output.flush();
                    }
                }

                //starts the game turn logic
                Server.currentPlayerTurn = 0; //breaks the loop once the start message is processed
                broadcastTurn(Server.currentPlayerTurn);
                break;

            }
        }
    }

    /**
     * run method for the Player that holds the game loop and keeps track of this Player's side of the game.
     * This method is required as a part of the implemented Runnable class.
     */
    @Override
    public void run() {
        try {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true); //autoflushing enabled to ensure all calls are sent

            //notify the leader client
            output.println("You are Player " + (playerNumber + 1));
            //Wait for all players to connect

            output.println("Waiting for other players to join...");
            while (Server.playerCount < 2) { //at least 2 players
                Thread.sleep(100); // uses the sleep method from the Thread class to wait. No need for locking since it doesn't affect turn conditions.
            }

            if (isLeader) {
                output.println("LEADER");
                waitForStart();
            }

            output.println("ALL PLAYERS CONNECTED:\nGame Starting!!!! You are Player " + (playerNumber + 1));

            //Game loop
            while (!checkIfGameOver()) {
                Server.turnLock.lock(); //lock the game and wait for the other player to go
                //check if current player's turn
                try {
                    while (Server.currentPlayerTurn != playerNumber || isDone) {
                        if (checkIfGameOver()) break;
                        Server.turnCondition.await();
                        //this skips players who are done and move on to the next player
                    }

                    //notify user before reading
                    output.println("Your turn!");
                    output.flush();

                    String line = input.nextLine();
                    if(line.startsWith("CHAT|")){
                        String chatMessage = line.substring(5);
                        String formattedMessage = "CHAT|" + chatMessage + "|" + (playerNumber+1);
                        for(Player p : Server.playerArray){
                            if(p != null && p.output != null){
                                p.output.println(formattedMessage);
                                p.output.flush();
                            }
                        }
                        Server.turnCondition.signalAll();
                        continue;
                    }
                    if (line.equals("WON")) {
                        this.markAsDone();

                        if (checkIfGameOver()) {
                            //if game is over, tell all the players so they can go to post game chat
                            for (Player p : Server.playerArray) {
                                if (p != null && p.output != null) {
                                    p.output.println("GAMEOVER");
                                    p.output.flush();
                                }
                            }
                            //    Server.turnCondition.signalAll();
                        }
                        //advance to next player
                        for (Player p : Server.playerArray) {
                            if (p != null && p.getOutputReference() != null) {
                                p.getOutputReference().println("PLAYERWON|" + playerNumber);
                                p.getOutputReference().flush();
                            }
                        }
                        int nextPlayer = findNextPlayer();

                        Server.currentPlayerTurn = nextPlayer;
                        broadcastTurn(Server.currentPlayerTurn);
                        Server.turnCondition.signalAll();
                        continue;
                    }
                    //Notify and prompt this player for their turn
                    int col = Integer.parseInt(line);
                    moveCount++; //increment the move count tracking this player's moves
                    System.out.println("move" + col);
                    //broadcast the move to everyone else
                    for (Player p : Server.playerArray) {
                        if (p != null) {
                            p.output.println(col + "|" + playerNumber);
                            p.output.flush();
                            System.out.println("col" + col + "num" + playerNumber);
                        }
                    }

                    //change player turn and broadcast to others after normal move
                    Server.currentPlayerTurn = findNextPlayer();
                    broadcastTurn(Server.currentPlayerTurn);
                    Server.turnCondition.signalAll();


                } finally {
                    Server.turnLock.unlock();
                }
            }
            while(input.hasNextLine()){
                String line = input.nextLine();
                if(line.startsWith("CHAT|")){
                    String chatMessage = line.substring(5);
                    String formattedMessage = "CHAT|" + chatMessage + "|" + (playerNumber+1);
                    for(Player p : Server.playerArray){
                        if(p != null && p.output != null){
                            p.output.println(formattedMessage);
                            p.output.flush();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Player " + playerNumber + " disconnected.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                //ignoring socket close failures
            }
        }
    }

    /**
     * Finds the next player's turn.
     *
     * @return next player.
     */
    private int findNextPlayer(){
        int total = Server.playerCount;
        int next = (this.playerNumber + 1) % total;
        int attempt = 0;


        while(attempt < total){
            if(!Server.playerArray[next].isDone){
                return next;
            }
            next = (next+1) % total;
            attempt++;
        }
        return -1;
    }

    /**
     * Checks if the game has finished (1 or 0 users left). indicates that the order of finishes is known.
     *
     * @return if the game has finished
     */
    private boolean checkIfGameOver(){
        int activePlayers = 0;
        for(Player p: Server.playerArray){
            if(p != null && !p.isDone()){
                activePlayers++;
            }
        }

        return activePlayers <= 1; //true if one or 0 players remain
    }


    /**
     * Broadcasts whose turn it is.
     *
     * @param playerNum index of the player whose turn it currently is.
     */
    private void broadcastTurn(int playerNum){
        System.out.println("Server log: Player " + (playerNum + 1) + "'s turn.");
        for(Player p: Server.playerArray){
            if(p != null && p.output != null){
                p.output.println("Turn|" + playerNum);
                p.output.flush();
            }
        }
    }
}