import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;

/**
 * GameController class represents the main panel for the Connect 4 game.
 * Responsible for rendering the game board, handling user interaction, animating chip drops, managing player turns, checking win conditions, and communicating moves with the server.
 *
 * Uses JPanel with a custom-drawn grid to simulate the connect 4 game board.
 * Distinguishes between local players and spectators.
 *
 * Integrated into a Swing-based GUI
 */
public class GameController extends JPanel {
    private int cols; //columns in grid
    private int rows; //rows in grid
    private JPanel gridCanvas; //canvas holding the grid
    private Color[][] board; //the board itself
    private PrintWriter clientOutput; //reference to output stream
    private final Color[] playerColors = {Color.RED, Color.BLACK, Color.BLUE, Color.ORANGE};
    private boolean isTurn = false; //tracks this player's turn
    private boolean gameOver = false; //indicates this game is finished
    private JLabel spectatorLabel;
    private int animateCol = -1; //column of animated chip
    private int animateY = -1; //vertical pixel position
    private Color animateColor = null; //color of animated chip
    private boolean animatingFlag = false;

    private int playerIndex = 0; //index of the player

    private JLabel turnLabel; //label to prompt for plays
    private int win; //win condition number of chips in a row to win.


    /**
     * Constructs a new GameController which sets up the entire board, defines the gui components of the board, and manages some game logic.
     *
     * @param rows number of rows.
     * @param cols number of columns.
     * @param win win condition number of chips in a row to win.
     */
    public GameController(int rows, int cols, int win) {
        this.cols = cols;
        this.rows = rows;
        this.win = win;
        this.board = new Color[rows][cols];
        setLayout(new BorderLayout());
        boardSetup();
        gridCanvas.setFocusable(true);

    }

    /**
     * Marks the game as finished for this user.
     */
    public void markGameOver(){
        gameOver = true;
        isTurn = false;
    }

    /**
     * Returns the index of this player.
     *
     * @return this player's index.
     */
    public int getPlayerIndex(){
        return this.playerIndex;
    }

    /**
     * Sets up the output PrintWriter reference.
     * @param output
     */
    public void setClientOutput(PrintWriter output) {
        this.clientOutput = output;
    }

    /**
     * sets the current player's turnLabel to prompt them to play.
     * @param turn
     */
    public void setTurn(boolean turn){
        this.isTurn = turn;
        if(turnLabel != null){
            if(turn){
                turnLabel.setText("Your Turn!!!");
                turnLabel.setForeground(playerColors[playerIndex]); //use this player's color
            }
        }
    }

    /**
     * Displays to the turnLabel whose turn it is.
     * @param playerNum index of the player whose turn it is.
     */
    public void displayWaitingOnPlayer(int playerNum){
        if(turnLabel != null){
            turnLabel.setText("Waiting on Player " + (playerNum + 1) + "...");
            turnLabel.setForeground(playerColors[playerNum]);
        }
    }


    /**
     * method to set up the board and defines the painting of the gui.
     */
    public void boardSetup() {

        gridCanvas = new JPanel() {
            /**
             * overridden paintComponent to paint the board and animated chips.
             * @param graphic the <code>Graphics</code> object to protect
             */
            @Override
            public void paintComponent(Graphics graphic) {
                super.paintComponent(graphic);
                int cellWidth = getWidth() / cols;
                int cellHeight = getHeight() / rows;
                int cellDiameter = Math.min(cellWidth, cellHeight) - 6;
                Graphics2D graphics2 = (Graphics2D) graphic;
                graphics2.setColor(Color.YELLOW);
                graphics2.fillRect(0,0,getWidth(),getHeight());


                //blue frame
                int standWidth = 10;
                graphics2.setColor(new Color(0, 51, 153));
                graphics2.fillRect(0,0,standWidth, getHeight()); //left stand
                graphics2.fillRect(getWidth() - standWidth, 0, standWidth, getHeight());//right stand

                //create the cells of the grid
                graphics2.setStroke(new BasicStroke(3));
                for (int r = 0; r < rows; r++) { //initialize all cells to blue
                    for (int c = 0; c < cols; c++) {
                        int x = c * cellWidth + ((cellWidth - cellDiameter) / 2);
                        int y = r * cellHeight + ((cellHeight - cellDiameter) / 2);

                        graphics2.setColor(Color.WHITE); // Always fill with white first
                        graphics2.fillOval(x, y, cellDiameter, cellDiameter);

                        graphics2.setColor(Color.BLUE);
                        graphics2.drawOval(x, y, cellDiameter, cellDiameter);
                        Color fillColor = board[r][c];
                        if (fillColor != null) {
                            graphics2.setColor(fillColor);
                            graphics2.fillOval(x, y, cellDiameter, cellDiameter);
                        }
                    }
                }

                //drawing the single falling disc on top of the board, the chip is not officially a part of the board state until animation finished).
                if(animatingFlag && animateColor != null && animateCol >= 0 && animateY >= 0){
                    int cellWidth1 = getWidth() / cols;
                    int cellDiameter1 = Math.min(cellWidth1, getHeight() / rows) - 6;
                    int x = animateCol * cellWidth1 + ((cellWidth1 - cellDiameter1) / 2);
                    Graphics2D g2 = (Graphics2D) graphic;
                    g2.setColor(animateColor);
                    g2.fillOval(x, animateY, cellDiameter1, cellDiameter1);
                }
            }
        };

        gridCanvas.addMouseListener(new MouseAdapter() {
            /**
             * handles the mouse clicked event.
             * @param e the event to be processed.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked. isTurn= " + isTurn);

                //included animating flag to ensure no move can be sent while a chip is animating
                if(!isTurn || gameOver || animatingFlag){//ignore if its not player turn
                    return;
                }
                int userCol = e.getX() * cols / gridCanvas.getWidth();
                isTurn = false; //block further moves
                if(clientOutput != null){
                    clientOutput.println(userCol);
                    clientOutput.flush();
                }


            }
        });

        //turn label
        turnLabel = new JLabel("Waiting for your turn...");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setForeground(Color.GRAY);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(turnLabel, BorderLayout.NORTH);

        gridCanvas.setPreferredSize(new Dimension(500,500));
        add(gridCanvas, BorderLayout.CENTER);//add to layout

        spectatorLabel = new JLabel("Game Complete. You are now spectating.");
        spectatorLabel.setHorizontalAlignment((SwingConstants.CENTER));
        spectatorLabel.setForeground(Color.BLUE);
        spectatorLabel.setVisible(false); //hidden by default

        add(spectatorLabel, BorderLayout.SOUTH);



        gridCanvas.setFocusable(true);
        gridCanvas.requestFocusInWindow();


        revalidate();
        repaint();
    }

    //overloaded animateChipDrop method
//    public void animateChipDrop(int col, int playerIndex, Runnable afterAnimation) {
//        animateChipDrop(col, playerIndex, afterAnimation, true);
//    }

    /**
     * Place a chip move from the server.
     * triggers animation.
     *
     * @param col column to move chip to.
     * @param playerIndex index of player who moade a move.
     */
    public void placeMoveFromServer(int col, int playerIndex) {
        boolean localMove = (playerIndex == this.playerIndex);

        //turn label is updated in the client

        //animate the chip drop
        animateChipDrop(col, playerIndex, null, localMove);


    }

    /**
     * Method to animate the chip drop mechanism. Calculates where the chip will land, animates the falling motion from the top of the board to its destination cell using a Swing Timer.
     * Places the chip officially onto board once animation ends.
     * If the animation was done by the local user, check for the win condition.
     *
     * @param col column to drop into.
     * @param playerIndex player index.
     * @param afterAnimation runnable task to run after animation completes (can be null).
     * @param localMove a boolean indicating whether the move was made by the local player.
     */
    public void animateChipDrop(int col, int playerIndex, Runnable afterAnimation, Boolean localMove){


        //find the row in the column where the chip should land
        int finalRow = getDropRow(col);

        //no animation if the column is full
        if(finalRow == -1){
            return; //column is full
        }

        //set up the initial animation values
        int cellHeight = gridCanvas.getHeight()/rows;
        animateCol = col; //track which column to animate in
        animateY = -cellHeight;//starts the chip anove the board to simulate dropping in
        animateColor = playerColors[playerIndex];// color of the chip

        animatingFlag = true;//set the animation flag to true while animating in paintComponent()

        Timer timer = new Timer(5, null); //5ms between each frame


        timer.addActionListener(e -> {

            //determines the vertical pixel target where the chip should stop
            int targetY = finalRow * cellHeight + (cellHeight / 2); //center of target cell
            animateY += 17; //pixel steps per frame

            //if we reached or passed the target position, then clamp the chip to the final position.
            if(animateY >= targetY) {
                animateY = targetY; //clamp it to the exact position Y
                timer.stop(); //stop animating
                board[finalRow][col] = animateColor; //permanently place the chip in the board

                //reset animation values and state so the chip is no longer redrawn separately
                animatingFlag = false;
                animateY = -1;
                animateCol = -1;
                animateColor = null;
                gridCanvas.repaint(); //redraw placed chip



                Color placedColor = board[finalRow][col];


                //win check runs if this was the local player's move
                if(localMove && checkWin(finalRow, col, placedColor)) {
                    markGameOver(); //update game state

                    //inform the sever that this client won
                    if (clientOutput != null) {
                        clientOutput.println("WON");
                        clientOutput.flush();

                        //dialog box with win message
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(this, "You Won!");
                        });
                    }

                    //run extra code that happens after animation completes
                    if (afterAnimation != null) {
                        afterAnimation.run();
                    }
                }
            }else{
                //mid-animation, repaint to show the new temporary position
                gridCanvas.repaint();
            }
        });

        timer.start();
    };


    /**
     * Get the row to drop the chip to.
     *
     * @param col the column to drop in.
     *
     * @return row to drop the chip to.
     */
    private int getDropRow(int col){
        for(int r = rows - 1; r >= 0; r--){
            if(board[r][col] == null){
                return r;
            }
        }
        return -1;
    }


    /**
     * Checks for win condition based on the grid and which color.
     *
     * @param row row of the latest chip added.
     * @param col column of the latest chip added.
     * @param color color of the latest chip added.
     * @return if a win condition was met.
     */
    public boolean checkWin(int row, int col, Color color){
        boolean checkRow = (countWin(row, col, 1, 0, color) + countWin(row,col,-1,0,color)) >= win-1;
        boolean checkCol = (countWin(row,col,0,1,color) + countWin(row, col, 0, -1, color))  >= win-1;
        boolean checkRightDiagonal = (countWin(row, col, 1,1,color) + countWin(row,col,-1,-1,color)) >= win-1;
        boolean checkLeftDiagonal = (countWin(row, col, -1, 1, color) + countWin(row, col, 1, -1, color)) >= win-1;
        if(checkRow || checkCol || checkRightDiagonal || checkLeftDiagonal){
            return true;
        }
        return false;
    }

    /**
     * Counts the number of consecutive chips in a specified direction from a given starting point.
     *
     * @param row chip row.
     * @param col chip column.
     * @param checkRow the row to check (1 for down, -1 for up, 0 for no movement vertically).
     * @param checkCol the column to check (1 for right, -1 for left, 0 for no horizontal movement).
     * @param color color of the chip to check for (player's chip).
     * @return the number of consecutive chips found in a specified direction.
     */
    public int countWin(int row, int col, int checkRow, int checkCol,Color color){
        int count = 0;
        row += checkRow;
        col += checkCol;
        while(row >=0 && row < rows && col >= 0 && col < cols && color.equals(board[row][col])){
            count++;
            row += checkRow;
            col += checkCol;
        }
        return count;
    }

    /**
     * Sets the player index.
     *
     * @param index player's index.
     */
    public void setPlayerIndex(int index){
        this.playerIndex = index;
    }

    /**
     * Sets this client as a spectator and updates the gui accordingly.
     */
    public void setSpectator(){
        this.gameOver = true;
        this.isTurn = false;
        SwingUtilities.invokeLater(() -> {
            turnLabel.setVisible(false);
            spectatorLabel.setVisible(true);
            gridCanvas.repaint();
        });
    }
}
