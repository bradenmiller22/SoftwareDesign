import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controller class for base conversion, has user input, validation for numbers
 * and performs conversions between bases 2,8,10,16
 * @author bmiller38
 */
public class BaseChangeController {

    @FXML
    private ComboBox<String> currentBase;//dropdown for current base

    @FXML
    private ComboBox<String> desiredBase;//dropdown for desired base

    @FXML
    private TextField outputField;//output field that shows converted number

    @FXML
    private TextField textField;//input field for user to enter number

    /**
     * Called when GUI is loaded
     * Populates combo boxes with the bases to convert with
     */
    @FXML
    public void initialize(){
        currentBase.getItems().addAll("2","8","10","16");//bases
        desiredBase.getItems().addAll("2","8","10","16");
    }

    /**
     * Action Event when the user presses "Convert" button
     * Validates input, converting the number to and from bases
     *
     * @param event the button clicked event
     */
    @FXML
    void buttonPressed(ActionEvent event) {
        String input = textField.getText().trim();//get user input removing spaces
        String currentBaseInput = currentBase.getValue();//current base from dropdown
        String desiredBaseInput = desiredBase.getValue();//desired base from dropdown

        //Check that all fields are filled in
        if(input.isEmpty() || currentBaseInput == null || desiredBaseInput == null){
            outputField.setText("Please fill out all fields");
            textField.selectAll();//highlight input text box
            textField.requestFocus();//send user cursor to it
            return;//do it again
        }

        int fromBase = getBase(currentBaseInput);//parse selected bases manually
        int toBase = getBase(desiredBaseInput);

        //check for invalid base selection
        if(fromBase == -1 || toBase == -1){
            outputField.setText("Invalid base selected");
            textField.selectAll();//error handling
            textField.requestFocus();
            return;
        }
        //validate that input is a valid number in the current base
        if(!isValidBase(input, fromBase)){
            outputField.setText("Input number invalid for base " + fromBase);
            textField.selectAll();//error handling
            textField.requestFocus();
            return;
        }
        //perform conversion first converting to decimal form
        int decimalValue = convertToDecimal(input, fromBase);
        //then converting from decimal to the desired base
        String result = convertFromDecimal(decimalValue, toBase);

        outputField.setText(result);//display result
    }

    /**
     * Method to convert the base string from dropdown box to integer value
     * returns -1 if no base is selected
     * @param base the base selected in ComboBox
     * @return integer base value
     */
    private int getBase(String base){
        if(base.equals("2")) return 2;
        if(base.equals("8")) return 8;
        if(base.equals("10")) return 10;
        if(base.equals("16")) return 16;
        return -1; //invalid
    }

    /**
     * Method to check if the input is a valid number in the current base
     * @param num user input number
     * @param base the current base selected
     * @return true if valid, false otherwise
     */
    private boolean isValidBase(String num, int base){
        for(char character:num.toUpperCase().toCharArray()){//go through every character from the user input
            int value;
            //if to get actual value (uses ascii chart to get numeric value of user input)
            if(character >= '0' && character <= '9'){
                //ex: '5' - '0' is 53 - 48 in ascii which equals 5
                value = character - '0';
            } else if (character >= 'A' && character <= 'F') {
                //for hex characters adding 10 offsets value: 'B' - 'A' is 66 - 65 = 1 in ascii so 10 + 1 is 11 which gives numeric value of B
                value = 10 +(character - 'A');
            }else {
                return false;
            }
            if(value >= base) return false;//if any single character exceeds the numeric value then return false
        }
        return true;
    }

    /**
     * Converts input number from original base to decimal
     * @param num number from user input
     * @param base base of current base
     * @return decimal value of the number
     */
    private int convertToDecimal(String num, int base){
        int result = 0;
        int power = 1;//initial right most number is always *1
        num = num.toUpperCase();//hexadecimal letters

        //go through the whole input character by character
        for(int i = num.length() - 1; i>=0;i--){
            char character = num.charAt(i);
            int value;
            //way to get numeric value of character using ascii number values
            if(character >= '0' && character <= '9'){
                value = character - '0';//get ascii value for digits
            }else {
                value = 10 + (character - 'A');//get ascii value for letters
            }

            result += value * power;//multiply the numeric value of rightmost character by its positional power
            //to start its always *1 for power, next one in hex is 1*16 so then for position 2 the numeric value times 16 then 256, 4096....
            power *= base;
        }
        return result;
    }

    /**
     * Converts a decimal number into desired base
     * @param num decimal number to convert
     * @param base desired output base
     * @return the number as a string in desired base
     */
    private String convertFromDecimal(int num, int base){
        if(num ==0) return "0";//if its simply 0

        StringBuilder result = new StringBuilder();//new stringbuilder

        //repeated division for extracting digits of the whole decimal number
        while (num > 0){
            //use this as remainder because we need to build the outputted number from left to right
            int remainder = num % base;
            char digit;//each digit for final string

            //determines the character for the remainder of number divided by the base
            if(remainder  < 10){
                digit = (char) ('0' + remainder);//get char value of ascii table
                //if remainder is 0-9 user 0-9
            }else {
                //if 10-15 use A-F, ex 13 goes to D
                digit = (char) ('A' + (remainder-10));
            }
            //add at beginning (to left of string being built) as lower digits come last
            //ex 27 (decimal) to base 16 is 1B not B1 as it would give B first then would get 1
            result.insert(0, digit);
            num /= base;//divide by base to go to next digit (27/16=1 > 1/16 = 0 )
        }
        return  result.toString();//send back full string
    }
}
