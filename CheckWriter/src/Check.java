/**
 * The Check class provides functionality for validating, converting and formatting amounts of a check
 * Class handles amount validation, formatting, and conversion to words
 * @author bmiller38
 */
public class Check {
    /**
     * Maximum number of characters allowed for the check amount
     */
    private final static int totalSpaces = 9;//maximum number of characters allowed for the check amount
    //all three of these arrays are for converting numbers to words if less than 1000.
    //using final static as the words for numbers are never going to change and these are shared across all instances of class
    /**
     * Units places so words for number 1-9 having index 0 be blank for access purposes
     */
    private final static String[] units = {"", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};//words for numbers if 1-9
    /**
     * Weird teens digit for special cases of numbers from 10-19
     */
    private final static String[] middle = {"TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"};//words for numbers 10-19
    /**
     * Tens place so words for numbers ten through ninety
     */
    private final static String[] tens = {"", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"};//words for the tens place 10,20...

    /**
     * Validates a string representation of a check amount
     * For it to work:
     *  - Has exactly 2 decimal points
     *  - Can't exceed max character length
     *  - If it contains commas they must be formatted correctly
     * @param num The string representation of the check amount to validate
     * @throws IllegalArgumentException if the amount is invalid
     */
    public static void validateAmount(String num){
        //check if the number inputted exceeds the max length (9)
        if(num.length() > totalSpaces){
            throw new IllegalArgumentException("Amount exceeds maximum characters (9)");
        }
        //get the index of the decimal point
        int index = num.indexOf(".");
        if(index == -1){//if there is no decimal point than index will be -1
            throw new IllegalArgumentException("Must have exactly 2 decimal places"); //no decimal point
        }
        //split the number into the real part and a decimal part
        String numberPart = num.substring(0, index);//this is number to left of decimal point
        String fractionPart = num.substring(index+1);//get the decimal part
        if(fractionPart.length() != 2){//decimal must have exactly 2 digits
            throw new IllegalArgumentException("Must have exactly 2 decimal places");
        }
        //now check if the number contains a comma
        //if there is a comma send it to validate the position
        if(numberPart.contains(",")){
            validateComma(numberPart);
        }
    }

    /**
     * Validates that commas in the number part of an amount are correctly placed
     * Should separate thousands place and only be used once
     * @param num The string representation of the whole number part (left of decimal)
     * @throws IllegalArgumentException if commas are not formatted correctly
     */
    private static void validateComma(String num){
        //count the total number of commas
        int commas = num.length() - num.replace(",","").length();//if 1,245 sent in it would be (5-4) 1,245-1245 = 1
        if(commas == 0){//if no commas than nothing to do
            return;
        }
        if(commas >1){//if more than one comma throw exception
            throw new IllegalArgumentException("Can only have 1 comma");
        }
        //get the index of the comma
        int index = num.indexOf(",");
        int length = num.length();
        if(index == 0 || index == length-1){//if comma is at the start or end then its invalid cant be (,234 or 123,)
            throw new IllegalArgumentException("Comma cannot be the start or end of an input");
        }
        if(length-index-1 != 3){//check if the comma seperates the thousands place (1,234 would be 5-1-1 = 3) which would be good
            throw new IllegalArgumentException("Comma must separate thousands place.");
        }
    }

    /**
     * Formats a double into a protected string representation
     * The representation fills empty spaces with asterisks (*)
     *
     * @param amount The double amount to format
     * @return <code>String</code> The protected string representation of amount
     * @throws IllegalArgumentException if amount exceeds max amount
     */
    public static String protectedAmount(double amount){
        //get it with a comma and a decimal
        String formatted = String.format("%,.2f", amount);//get it formatted to a string
        if(formatted.length() > totalSpaces){//check if formatted string exceeds max amount
            throw new IllegalArgumentException("Amount exceeds the maximum");
        }
        //get total asterisks needed
        int asterisks = totalSpaces - formatted.length();

        if(asterisks > 0){//if asterisks are needed create a string builder
            StringBuilder protectedTotal = new StringBuilder();
            for(int i = 0;i < asterisks; i++){//build the string starting with asterisks
                protectedTotal.append("*");
            }
            //after asterisks are placed then add the formatted string after them (***123.89)
            protectedTotal.append(formatted);
            return protectedTotal.toString();//retrun stringbuilder toString
        }
        return formatted;//if no asterisks were needed return the original formatted string
    }

    /**
     * Converts numeric amount to word representation
     *
     * @param amount the double amount to conver to words
     * @return <code>String</code> representation of amount
     */
    public static String convertWords(double amount){
        //split amount into dollar part and cents part
        int dollars = (int) amount;//get  the whole number part
        int cents = (int) Math.round((amount-dollars) * 100);//get cents part by amount-dollar part and then round to a whole number
        String dollarWords = convertNum(dollars);//convert dollar amount to word representation
        return dollarWords + " and " + cents + "/100";//return the formatted string
    }

    /**
     * Helper method to convert whole number to words
     * Handles numbers from 0-999
     * @param num The integer to convert to words
     * @return <code>String</code> representation of the number
     */
    private static String convertNum(int num){
        if(num == 0){//if number was 0 than just return ZERO
            return "ZERO";
        }
        StringBuilder word = new StringBuilder();//create a new stringbuilder
        if(num >= 100){//handle the hundreds place (100-999)
            //ex 921/100 = 9, get index 9 from units which is NINE
            word.append(units[num/100]).append(" hundred ");//get the index position of units (ONE,TWO...) and format that with hundred
            num %= 100;//remove the hundreds place from the number 921 % 100 = 21
        }
        if(num >= 20){//handle the tens place for 20-99
            word.append(tens[num/10]).append(" ");//add the tens word (21/10 = 2) get index 2 from tens which is 20
            num %= 10;//remove the tens place
        } else if (num >= 10) {//handle cases for 10-19 with being ELEVEN, TWELVE, THIRTEEN...
            word.append(middle[num - 10]).append(" ");//add teens word
            num = 0;//last number so no ones
        }

        if(num > 0){//handle the ones place
            word.append(units[num]).append(" "); //get the index of ones in our case 1 equals one
        }
        return word.toString().trim();//remove any spaces and convert it to a string
    }
}
