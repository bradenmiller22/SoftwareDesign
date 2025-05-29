//import static to not have to use Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.*; //imports all assertion types(equals,true, false) are used for this
import org.junit.jupiter.api.Test;//import @test to define test methods and junit executes these methods
import org.junit.jupiter.params.ParameterizedTest;//import for parameterized tests, allows same test to be run multiple times with different input values
import org.junit.jupiter.params.provider.ValueSource;//import for value source in parameterized tests, supplies a set of values to test
public class MatrixTest {
    /**
     * Tests matrix constructor that initializes matrix with specified dimensions
     * Verifies that matrix has correct rows, cols, default values
     */
    @Test//annotation to specify to JUnit to run these methods
    void testConstructorDimensions(){
        Matrix m1 = new Matrix(2,3);//new 2x3 matrix
        assertEquals(2, m1.getRows());//verify rows=2
        assertEquals(3, m1.getColumns());//verify cols=3
        assertEquals(0.0, m1.getMatrix(0,0));//verify values are 0 at 0,0
    }

    /**
     * tests the matrix constructor that initializes a matrix with given 2d array
     * Verifies matrix is correctly populated
     */
    @Test
    void testConstructorInput(){
        Matrix m1 = new Matrix(new double[][] {{1,2}, {3,4}});//matrix from 2d array
        assertEquals(1.0, m1.getMatrix(0,0));//verify value (0,0)=1
        assertEquals(3.0, m1.getMatrix(1,0));//verify value (1,0)=3
    }

    /**
     * Test the equals method of matrix class
     * Verifies that two matrices with same values are equal
     */
    @Test
    void testEquals(){
        //initialize 3 matrices (2 equal, 1 not)
        Matrix m1 = new Matrix(new double[][] {{1,2}, {2,3}});
        Matrix m2 = new Matrix(new double[][] {{1,2}, {2,3}});
        Matrix m3 = new Matrix(new double[][] {{1,2}, {2,7}});

        assertTrue(m1.equals(m2));//should return true
        assertFalse(m1.equals(m3));//should return false
    }

    /**
     * Tests the identity method
     * Verifies that the identity matrix of given size is correctly generated
     */
    @Test
    void testIdentity(){
        //initialize expected output for a 4x4
        Matrix expected = new Matrix(new double[][] {{1, 0, 0, 0},{0,1,0,0}, {0,0,1,0}, {0,0,0,1}});
        Matrix identity = Matrix.identity(4);
        assertTrue(identity.equals(expected));//check if identity matrix works
    }

    /**
     * Tests a 1x1 Matrix with trying to set the [2,2] position to 3
     * Verifies that a Matrix cannot set something it doesn't have
     */
    @Test
    void testInvalidSet(){
        Matrix invalid = new Matrix(1,1);
        assertThrows(IllegalArgumentException.class, () -> invalid.setMatrix(2,2,3));
    }

    /**
     * Tests subMatrix mehtod of Matrix class
     * Verifies that a submatrix is correctly extracted from larger matrix
     */
    @Test
    void testSubMatrix(){
        //initalize 3x3 matrix
        Matrix m1 = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
        Matrix sub = m1.subMatrix(0,0,1,1);
        //extract a 2x2 matrix
        Matrix expected = new Matrix(new double[][] {{1,2},{4,5}});
        assertTrue(sub.equals(expected));//check if equal using .equals as previously tested to work
    }

    /**
     * Tests the matrix addition functionality
     * Verifies that instance, static, in-place addition all work
     */
    @Test
    void testMatrixAddition(){
        //initialize 2 matrices and expected output
        Matrix m1 = new Matrix(new double[][] {{1,2},{3,4}});
        Matrix m2 = new Matrix(new double[][] {{5,6}, {7,8}});
        Matrix expected = new Matrix(new double[][] {{6, 8}, {10,12}});
        //perform addition using instance method
        Matrix result1 = m1.add(m2);
        assertTrue(expected.equals(result1));//verify result
        //perform addition using static method
        Matrix result2 = Matrix.add(m1, m2);
        assertTrue(expected.equals(result2));//verify result
        //perform in-place addition
        m1.addInPlace(m2);
        assertTrue(expected.equals(m1));//check that m1 was modified
    }

    /**
     *  Tests the Matrix subtraction funcionality
     *  Verifies that the instance, static, and in-place subtraction all work
     */
    @Test
    void testMatrixSubtraction(){
        //initialize 2 matrices and a expected matrix
        Matrix m1 = new Matrix(new double[][] {{1,2}, {3,4}});
        Matrix m2 = new Matrix(new double[][] {{5,6}, {7,8}});
        Matrix expected = new Matrix(new double[][] {{-4,-4},{-4,-4}});
        //perform subtraction using instance method
        Matrix result1 = m1.subtract(m2);
        assertTrue(expected.equals(result1));//verify result matches expected
        //perform subtraction using static method call
        Matrix result2 = Matrix.subtract(m1, m2);
        assertTrue(expected.equals(result2));//verify result matches expected
        //perform subtraction usingg in-place to modify m1 directly
        m1.subtractInPlace(m2);
        assertTrue(expected.equals(m1));//verify result matches expected
    }

    /**
     * Test matrix multiplication functionality
     * Verifies that matrix multiplication works for instance, static, and in-place methods
     */
    @Test
    void testMatrixMultiply(){
        //initialize 2 matrices and a expected result
        Matrix m1 = new Matrix(new double[][] {{1,2},{3,4}});
        Matrix m2 = new Matrix(new double[][] {{5,6},{7,8}});
        Matrix expected = new Matrix(new double[][] {{19,22},{43,50}});
        //perform instance method of multiplication
        Matrix result1 = m1.multiply(m2);
        assertTrue(expected.equals(result1));//verify result
        //perform static method of multiplication
        Matrix result2 = Matrix.multiply(m1,m2);
        assertTrue(expected.equals(result2));//verify result
        //perform in-place method of multiplication modifying m1
        m1.multiplyInPlace(m2);
        assertTrue(expected.equals(m1));//verify original matrix is updated correctly
    }

    /**
     * Tests the scalar multiplication functionality
     * Verifies that scalar multiplication works correctly for instance, static, and in-place method
     * @param scalar value to multiply matrix by
     */
    @ParameterizedTest//JUnit will run this multiple times with values
    @ValueSource(doubles = {0.0,1.0,3.0}) //test with multiple values
    void testMatrixScalar(double scalar){
        Matrix m1 = new Matrix(new double[][] {{1,2},{3,4}});//2x2 matrix
        Matrix expected = new Matrix(2,2);//empty result matrix
        for(int i = 0; i<2;i++){//go through twice
            for(int j = 0; j < 2;j++){//go through twice
                expected.setMatrix(i, j, m1.getMatrix(i,j)*scalar);//compute the result using setMatrix and getMatix
            }
        }
        //perform scalar multiplication using instance method
        Matrix result1 = m1.scalarMultiply(scalar);
        assertTrue(expected.equals(result1));//verify result matches expected
        //perform scalar multiplication using static method
        Matrix result2 = Matrix.scalarMultiply(m1, scalar);
        assertTrue(expected.equals(result2));//verify result matches expected
        //perform in-place scalar multiplication
        m1.scalarInPlaceMultiply(scalar);
        assertTrue(expected.equals(m1));//verify original matrix is updated correctly
    }

    /**
     * Test the toString method of Matrix class
     * Verifies that the matrix is correctly converted to string representation
     */
    @Test
    void testToString(){
        Matrix m1 = new Matrix(new double[][] {{1,2},{3,4}});//new 2x2 matrix
        String expected = "1.0 2.0 \n3.0 4.0 \n";//expected result
        assertEquals(expected, m1.toString());//chekc if equal
    }

}
