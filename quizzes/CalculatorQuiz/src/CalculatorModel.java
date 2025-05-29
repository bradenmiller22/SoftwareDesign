import java.text.NumberFormat;

public class CalculatorModel {
    private static final NumberFormat formatted = NumberFormat.getInstance();
    private double number1;
    private char op = ' ';

    public String store (String display){
        String result = "";
        try{
            number1 = Double.parseDouble(display);
            result = formatted.format(number1);
        }catch(NumberFormatException e){
            result = "Error";
        }
        return result;
    }

    public void storeOp (char op){
        this.op = op;
    }

    public String compute (String display){
        String result = "";
        try {
            double number2 = Double.parseDouble(display);
            switch (op){
                case '+': number1 += number2;
                break;
                case '-': number1 -= number2;
                break;
                case '/': number1 /= number2;
                break;
                case '*': number1 *= number2;
                break;
                default: number1 = number2;
                break;
            }
            result = formatted.format(number1);
        } catch (Exception e){
            result = "Error";
            number1 = 0;
        }
        return result;
    }
}
