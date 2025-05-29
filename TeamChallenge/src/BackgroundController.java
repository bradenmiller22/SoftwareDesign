import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * BackgroundController extends JPanel and is used to create a panel with an image that redraws the image as window size changes.
 */
public class BackgroundController extends JPanel {
    private final Image image; //image to show

    /**
     * Constructor for Background controller
     * @param path String path to image
     */
    public BackgroundController(String path){
        image = new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage(); //get image from class -> resource
        setLayout(new GridBagLayout()); //layout grid bag
    }

    /**
     * PaintComponent draws the image to the graphic
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g); //draw to panel
        g.drawImage(image,0,0,getWidth(),getHeight(),this); //actively resize
    }

}
