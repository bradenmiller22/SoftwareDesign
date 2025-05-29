import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main driver that launches the JavaFX Base Changer
 * @author bmiller38
 */
public class BaseChange extends Application {
    public static void main(String[] args) {
        launch(args);//launch the application
    }

    /**
     * Called by JavaFX to start the application
     * Loads FXML file and initializes the scene/stage
     * @param stage primary stage for application
     * @throws Exception if FXML file isn't loaded
     */
    @Override
    public void start(Stage stage) throws Exception{
        //load fxml file
        Parent root = FXMLLoader.load(getClass().getResource("BaseChange.fxml"));

        Scene scene = new Scene(root);//create new scene with the fxml file
        stage.setTitle("Base Change");//set title
        stage.setScene(scene);//apply scene to stage
        stage.show();//display
    }
}