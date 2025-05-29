// Fig. 26.2: Addition.java
// Addition program that uses JOptionPane for input and output.

import javax.swing.*;

public class Addition 
{
   public static void main(String[] args)
   {
      // obtain user input from JOptionPane input dialogs
      String firstNumber = 
         JOptionPane.showInputDialog("Enter integers");

      String[] numbers = firstNumber.trim().split("\\s+");
      // convert String inputs to int values for use in a calculation
      int number1 = Integer.parseInt(numbers[0]);
      int number2 = Integer.parseInt(numbers[1]);

      int sum = number1 + number2; // add numbers

      // display result in a JOptionPane message dialog
      JOptionPane.showMessageDialog(null, "Enter two ints" + "The sum is " + sum,
         "Sum of Two Integers", JOptionPane.PLAIN_MESSAGE);
   } 
} // end class Addition


/**************************************************************************
 * (C) Copyright 1992-2018 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
