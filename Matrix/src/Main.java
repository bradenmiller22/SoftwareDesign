/**
 * Main class is entry point of program
 * Demonstrate the functionality of matrix class by creating matrices, and performing operations on them
 *
 * @author bmiller38
 */
public class Main {
    public static void main(String[] args) {
        //define a 4x4 matrix as 2d array of doubles
        double[][] data1 = {
                {1.0, 3.0, 2.0, 2.0},
                {3.0, 4.0, 7.0,4.0},
                {1.0, 2.0, 9.0,3.0},
                {1.0,3.0,2.0,6.0}
        };
        //define another 4x4 matrix as 2d array of doubles
        double[][] data2 = {
                {3.0, 6.0, 6.0,4.0},
                {2.0, 5.0, 10.0,5.0},
                {4.0, 7.0, 8.0,9.0},
                {2.0,9.0,6.0,7.0}
        };
        //create matrix objects using 2d arrays
        Matrix m1 = new Matrix(data1);//matrix from data1
        Matrix m2 = new Matrix(data2);//matrix from data2
        Matrix m3 = new Matrix(2, 2);//2x2 matrix with default data(zeros)

        //print matrices to console, toString is implicity called
        System.out.println("Matrix 1: \n" + m1);//m1.toString
        System.out.println("Matrix 2: \n" + m2);//m2.toString
        System.out.println("Matrix 3: \n" + m3);//m3.toString
        //create and print a 5x5 identity matrix
        System.out.println("Identity Matrix (size 5): \n" + Matrix.identity(5));


        //calling instance method useful for adding matrix to instance of a matrix
        //calling static is useful for just calling add without depending on a specific instance of a Matrix
        System.out.println("Static add: \n"+Matrix.add(m1, m2));
        System.out.println("m1 + m2: \n" +m1.add(m2));//perform matrix addition
        System.out.println("m1 - m2: \n" + m1.subtract(m2));//perform matrix subtraction
        System.out.println("m1 * m2: \n" + m1.multiply(m2));//perform matrix multiplication
        System.out.println("Scalar multiplication of m1 * 3: \n" + m1.scalarMultiply(3));//perform scalar multiplication on matrix
        System.out.println("Sub matrix of m1 (2x2): \n" + m1.subMatrix(1,1,2,2));//extract a 2x2 submatrix

    }
}