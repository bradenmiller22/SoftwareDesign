import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.security.SecureRandom;

/**
 * Controller class for the screen saver
 * Handles user interaction, canvas drawing, and the animation for lines
 * Generates lines that animate from one random edge to another
 *
 * @author bmiller38
 */
public class ScreenSaverController {
    /**
     * Random generator for getting random coordinates and colors for a line
     */
    private SecureRandom rand = new SecureRandom();//secure random generator for random coordinates and colors
    /**
     * Timeline controls scheduling for animation of the lines every 1 second to display to screen
     */
    private Timeline timeline;//timeline controls the scheduling of the animation for the lines

    /**
     * Canvas is the drawing surface for drawing lines to the screen
     */
    @FXML
    private Canvas canvas;//drawing surface in the layout

    /**
     * Text field in order to get user input for how many lines to go through
     */
    @FXML
    private TextField userInput;//textfield for user to input how many lines to animate
    /**
     * Total lines for displaying to the screen
     */
    private int totalLines;
    /**
     * how many lines have been currently drawn to the screen
     */
    private int currentLines = 0;

    /**
     * Generates a random coordinate pair located on an edge
     * @param edge Integer indicating which edge (0=top, 1=right, 2=bottom, 3=left)
     * @param width Width of the canvas
     * @param height Height of the canvas
     * @return <code>integer[]</code> array with two elements: [x,y] coordinate
     */
    private int[] getEdge(int edge, int width, int height){
        int[] coords = new int[2];//array with 2 spaces

        switch (edge) {
            case 0: //TOP of canvas //user input starts at x=255, is 90 long and ends at x = 345
                int choice1 = rand.nextInt(256);//x on the left side of the user text box
                int choice2 = rand.nextInt(256) + 345;//x on the right side of the user text box
                coords[0] = rand.nextInt(2);//random x on top between choice 1 and choice 2 (0 or a 1)
                if(coords[0] == 0){
                    coords[0] = choice1;
                }else{
                    coords[0] = choice2;
                }
                coords[1] = 0; //top left is 0,0 in canvas for y val
                break;
            case 1: //RIGHT of canvas
                coords[0] = width; //x val is far right so the max width
                coords[1] = rand.nextInt(height); // y val is 0 to the total height
                break;
            case 2: //BOTTOM of canvas
                coords[0] = rand.nextInt(width);//rand x on bottom from 0 to the width
                coords[1] = height; //bottom is actaully the height for y value
                break;
            case 3://LEFT of canvas
                coords[0] = 0;//0 for x on left
                coords[1] = rand.nextInt(height);//rand height for left side from 0 to the height
                break;
        }

        return coords; //return an ordered pair x,y
    }

    /**
     * This is called when the user clicks enter on the text field
     * Reads input, clears the canvas to begin drawing, and draws lines between random edges
     * @param event the action event triggered by the user
     */
    @FXML
    void buttonPressed(ActionEvent event) {
        try {
            String input = userInput.getText();//user input from text box
            if(input.contains("-")){//avoid negative numbers as they dont work
                throw new NumberFormatException();
            }
            if (timeline != null) {//if an animation was going from a previous line drawings, stop it
                timeline.stop();
            }
            totalLines = Integer.valueOf(userInput.getText());//get user input as an integer number
            currentLines = 0;//reset current drawn lines to 0
            GraphicsContext graphic = canvas.getGraphicsContext2D();//get context for drawing
            graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());//clear all previous lines

            drawLines();//call draw lines
        } catch (NumberFormatException e){
            //catch an error if the userinput isnt a number
            userInput.setText("Enter Lines");//set the text box to enter lines
            userInput.selectAll();//highlight the input
            userInput.requestFocus();//place user cursor on the textfield
        }
    }

    /**
     * Called upon when a user types in enter
     * Loops through the user input of total lines and when reaching the end will reset to 0 and loop again
     */
    private void drawLines() {
        GraphicsContext graphic = canvas.getGraphicsContext2D();
        int width = (int) canvas.getWidth();//set width to width of the canvas
        int height = (int) canvas.getHeight();//set height to the height of the canvas

        //Timeline allows for events to be scheduled over time period
        timeline = new Timeline();//initialize a new timeline
        timeline.setCycleCount(Timeline.INDEFINITE);//loops indefinitely

        //create the keyframe for drawing the current line
        //Keyframe acts like a scene in the timeline, so starting at 0 delay is 1 second from the start
        KeyFrame key = new KeyFrame(Duration.millis(1000), //delay from start of animation (ms)
                e -> {//event handler defines what happens when the keyframe(scene) is reached in the timeline
                    if (currentLines >= totalLines) {
                        graphic.clearRect(0, 0, width, height);
                        currentLines = 0;
                    }
                    int startEdge = rand.nextInt(4);//0-3 gives TOP, RIGHT, BOTTOM, LEFT edge
                    int endEdge = startEdge;//set the end to the start
                    while (endEdge == startEdge) {//while they are equal continuously find a random edge for the end
                        endEdge = rand.nextInt(4);//find a new random edge that isnt the starting edge
                    }

                    int[] startCoords = getEdge(startEdge, width, height);//get random coordinates for the starting edge
                    int[] endCoords = getEdge(endEdge, width, height);//get random coordinates for the ending edge

                    int x1 = startCoords[0];//x1 is first num in array of start
                    int y1 = startCoords[1];//y1 is second num in array of start
                    int x2 = endCoords[0];//x2 is first num in end array
                    int y2 = endCoords[1];//y2 is second num end array

                    //make the line a random RGB color
                    Color color = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                    graphic.setStroke(color);//set lines color
                    graphic.strokeLine(x1, y1, x2, y2);//draw the line on the canvas
                    currentLines++;
                });
        //add this keyframe (scene) to the timelines sequence of scenes
        timeline.getKeyFrames().add(key);
        //play the entire timeiline after all lines have been added to the timeline
        //each keyframe is executed at its scheduled time, creating an animation of lines
        timeline.play();
    }
}