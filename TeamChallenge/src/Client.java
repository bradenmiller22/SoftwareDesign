import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Client class processes the incoming messages from the server and updates the GUI game board accordingly.
 */
public class Client extends JFrame {
    public GameController board;
    private PrintWriter output; // output stream to server
    private BufferedReader input; // input stream from server
    private Socket client; // socket to communicate with server
    private final String chatServer; // host server for this application
    private String message = ""; // message from server
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean isLeader = false;
    private LobbyController lobby;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private boolean turnPending = false;
    private int playerNumber = -1;
    private PostGameController postGame;

    /**
     * Constructs a new Client with a given host. Starts the GUI.
     * @param host
     */
    public Client(String host) {
        this.chatServer = host;
        makeGUI();
    }

    /**
     * Runs the client. Sets up the connections to the server.
     */
    public void runClient() {
        try // connect to server, get streams, process connection
        {
            connectToServer(); // create a Socket to make connection
            getStreams(); // get the input and output streams
            lobby.setClientOutput(output);
            executor.execute(() -> {
                try {
                    processConnection(); // This will run in a separate thread
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException ioException) {
            SwingUtilities.invokeLater(()->{
                JOptionPane.showMessageDialog(this, "Lobby is full");
            });
        }
    }

    /**
     * Makes the GUI and adds the different frames to the main panel.
     */
    private void makeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 390);

        lobby = new LobbyController();

        mainPanel.add(lobby, "Lobby");

        add(mainPanel);
        setVisible(true);

        //default screen, changes to lobby later if this player is the leader
        cardLayout.show(mainPanel, "Lobby");

        postGame = new PostGameController();
        postGame.setOutput(output);
        mainPanel.add(postGame, "Post");
    }

    /**
     * Connects to the server.
     * @throws IOException if connection to port does not work.
     */
    private void connectToServer() throws IOException // connect to server
    {

        // create Socket to make connection to server
        client = new Socket(InetAddress.getByName(chatServer), 23630);

        // display connection information
    }

    /**
     * Gets the input and output stream references.
     *
     * @throws IOException if establishing input/output streams has issues.
     */
    private void getStreams() throws IOException // get streams to send and receive data
    {
        // set up output stream for objects
        output = new PrintWriter(client.getOutputStream());
        output.flush(); // flush output buffer to send header information

        // set up input stream for objects
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    /**
     * Manages GUI based on the incoming messages sent through the server.
     *
     * @throws IOException if an incoming connection has issues.
     */
    private void processConnection() throws IOException // process connection with server
    {
        do // process messages sent from server
        {
            try // read message and display it
            {
                message = input.readLine(); // read new message
                if(message == null){
                    break;
                }

                if(message.startsWith("You are Player ")){
                    playerNumber = Integer.parseInt(message.split(" ")[3])-1;
                }else if(message.equals("Your turn!")) {
                    System.out.println("DEBUG: got turn!");
                    SwingUtilities.invokeLater(() -> {
                        if (board != null) {
                            board.setTurn(true);
                        } else {
                            System.out.println("Board is null, deferring turn");
                            turnPending = true;

                        }
                    });
                }else if(message.startsWith("Turn|")){
                    int currentPlayer = Integer.parseInt(message.split("\\|")[1]);

                    SwingUtilities.invokeLater(()->{
                        if(board != null){
                            if(currentPlayer == playerNumber){
                                board.setTurn(true); //your own turn
                            }else if(currentPlayer>=0 && currentPlayer<4){
                                board.displayWaitingOnPlayer(currentPlayer); //another player's turn
                            }
                        }
                    });

                } else if(message.equals("GAMEOVER")){
                    SwingUtilities.invokeLater(() -> {
                        postGame.setOutput(output);
                        cardLayout.show(mainPanel, "Post");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    });

                } else if(message.contains("CHAT|")){
                    String[] messageParts = message.split("\\|", 3); //CHAT|message|player
                    if(messageParts.length>=3) {
                        String words = messageParts[1];
                        String user = messageParts[2];
                        SwingUtilities.invokeLater(() -> {
                            postGame.displayMessage("Player " + user + ">> " + words);
                        });
                    }
                }
                else if(message.equals("LEADER")){
                    isLeader = true;
                    SwingUtilities.invokeLater(() ->{
                        lobby.setLeader(true);

                        cardLayout.show(mainPanel, "Lobby");
                    });
                }else if(message.startsWith("CONNECTED|")){
                    String[] parts = message.split("\\|");
                    int count = Integer.parseInt(parts[1]);

                    SwingUtilities.invokeLater(() -> {
                        lobby.updatePlayerCount(count);
                    });
                }else if(message.startsWith("PLAYERWON|")) {
                    int winnerNum = Integer.parseInt(message.split("\\|")[1]);
                    SwingUtilities.invokeLater(() -> {
                        if (board != null) {
                            if (winnerNum == board.getPlayerIndex()) {
                                postGame.setRanking("Player " + (winnerNum + 1));
                                board.setSpectator();
                            } else {
                                JOptionPane.showMessageDialog(this, "Player " + (winnerNum + 1) + " has won");
                                postGame.setRanking("Player " + (winnerNum+1));
                            }
                            //do nothing
                        }
                    });
                }else if(message.startsWith("NOTIFY|")){
                    String text = message.substring(7);
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(Client.this, text)
                    );
                } else if (message.contains("|")) {
                    String[] messageParts = message.split("\\|");
                    if(messageParts[0].equals("START")){
                        System.out.println("in start");
                        int rows = Integer.parseInt(messageParts[1]);
                        int cols = Integer.parseInt(messageParts[2]);
                        int win = 4;
                        if(messageParts.length > 3) {
                            win = Integer.parseInt(messageParts[3]);
                        }
                        board = new GameController(rows,cols, win);
                        board.setClientOutput(output);

                        board.setPlayerIndex(playerNumber);
                        SwingUtilities.invokeLater(() -> {
                            mainPanel.add(board, "Game");
                            cardLayout.show(mainPanel, "Game");
                            mainPanel.revalidate();
                            mainPanel.repaint();
                        });

                        if(turnPending && board != null){
                            board.setTurn(true);
                            turnPending = false;
                        }

                    } else {
                        int column = Integer.parseInt(messageParts[0]);
                        int number = Integer.parseInt(messageParts[1]);
                        //make board here with rows and cols from server
                        SwingUtilities.invokeLater(() -> {
                            if(board != null) {
                                board.placeMoveFromServer(column, number); //animate the move
                            }
                        });
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } while (!message.contains("TERMINATE"));
        closeConnection();
    }

    /**
     * closes the streams and socket when ending the game.
     */
    private void closeConnection() // close streams and socket
    {
        try {
            output.close(); // close output stream
            input.close(); // close input stream
            client.close(); // close socket
            executor.shutdown();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
