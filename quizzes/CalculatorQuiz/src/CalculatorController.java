import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML
    private Button clear;

    @FXML
    private TextField display;

    @FXML
    private Button divide;

    @FXML
    private Button eight;

    @FXML
    private Button equals;

    @FXML
    private Button five;

    @FXML
    private Button four;

    @FXML
    private Button minus;

    @FXML
    private Button multiply;

    @FXML
    private Button nine;

    @FXML
    private Button one;

    @FXML
    private Button plus;

    @FXML
    private Button seven;

    @FXML
    private Button six;

    @FXML
    private Button three;

    @FXML
    private Button two;

    @FXML
    private Button zero;
    private CalculatorModel model = new CalculatorModel();
    private boolean start = true;
    private boolean pressed = false;

    @FXML
    void buttonPressed(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        if(isNumeric(buttonText)){
            handleNumeric(buttonText);
            return;
        }
        if(isOperator(buttonText)){
            handleOperator(buttonText);
            return;
        }
        if(buttonText.equals("=")){
            handleEquals();
            return;
        }
        if(buttonText.equals("C")){
            handleClear();
            return;
        }

    }
    private boolean isNumeric(String text){
        return text.matches("[0-9]");
    }
    private boolean isOperator(String text){
        return text.equals("+") || text.equals("-") || text.equals("/") || text.equals("*");
    }

    private void handleNumeric(String num){
        if(start || display.getText().equals("0") || display.getText().equals("Error")){
            display.setText(num);
            start = false;
        } else {
            display.setText(display.getText() + num);
        }
        pressed = false;
    }

    private void handleOperator(String op){
        if(!pressed){
            if(!start){
                if(!display.getText().equals("Error")){
                    String result = model.store(display.getText());
                    display.setText(result);
                }
            }
        }

        model.storeOp(op.charAt(0));
        start = true;
        pressed = true;

    }

    private void handleEquals(){
        if(!start && !display.getText().equals("Error")){
            String result = model.compute(display.getText());
            if(result.isEmpty()){
                result = model.store("");

            }
            display.setText(result);
            start = true;
        }
    }
    private void handleClear(){
        display.setText("0");
        model = new CalculatorModel();
        start=true;
        pressed=false;
    }

    @FXML
    public void initialize(){
        display.setText("0");
    }
}
