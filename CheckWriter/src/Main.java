import java.util.Scanner;

/**
 * Represents the main driver class and the functionality for the check writer program
 * Handles user input, sends user input to be validated, and returns an appropriate amount
 * @author bmiller38
 */
public class Main {
    /**
     * Main method for check writer
     * Prompts user input, validates it, than outputs word if (<1000) or protected version if (>1000)
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);//scanner object to read user input

        System.out.println("Enter check amount: ");//get user input for a check amount
        String input = scanner.nextLine().trim();//read the user input and remove any leading/trailing spaces

        try {
            //continuously trys the user input until a valid amount is entered
            Check.validateAmount(input);

            //convert the validated string to a double by removing commas
            double amount = Double.parseDouble(input.replace(",",""));
            //choose either protected amount or the word version based on amount inputted
            if(amount < 1000){
                System.out.println(Check.convertWords(amount));//convert to words if less than 1000
            } else {
                System.out.println(Check.protectedAmount(amount));//convert to a protected amount if more than 1000
            }
        } catch (IllegalArgumentException e){
            //display error message if the user input is invalid and it will go again
            System.out.println("Error: " + e.getMessage());
        }
    }
}