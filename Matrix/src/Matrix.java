/**
 * The Matrix class represents a math matrix with rows and columns.
 * The class provides functionality to create, verify, manipulate, and perform basic operations on matrixes.
 * @author bmiller38
 */
public class Matrix {
    private int rows;//number of rows in the matrix
    private int columns;//number of columns in the matrix
    private double[][] matrix;//2d array to store matrix values (array of arrays)
    /**
     * Constructs a new Matrix with a specified amount of rows and columns
     * All elements initialized to 0.0
     * @param rows the number of rows in the matrix
     * @param columns the number of columns in the matrix
     * @throws IllegalArgumentException if rows or columns is less than or equal to 0
     */
    public Matrix(int rows, int columns){
        if(rows <= 0 || columns <= 0){//checks for invalid dimensions (needs at least 1 row and 1 columns)
            throw new IllegalArgumentException("Matrix must be initialized with at least 1 row and 1 column!");
        }
        this.rows = rows;//set number of rows
        this.columns = columns;//set number of columns
        this.matrix = new double[rows][columns];//initialize the matrix with number of rows/cols all to 0.0
    }

    /**
     * Constructs a new Matrix from a 2d array of doubles
     * Array must be rectangular that is sent in and not empty(0x0)
     * @param matrix the 2d array to initialize the matrix with
     * @throws IllegalArgumentException if array is null, empty, or jagged
     */
    public Matrix(double[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){//check for null or empty
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        this.rows = matrix.length;//set rows
        this.columns = matrix[0].length;//set columns by taking however long the first row is
        this.matrix = new double[rows][columns]; //need to make a deep copy of array, initialize with zeros
        for(int i = 0; i < rows;i++){//each row
            if(matrix[i].length != columns){//check if jagged(ex row 1 is 3 wide {1,2,3} but row 2 is 2 wide {1,2} will throw error
                throw new IllegalArgumentException("Array must be rectangular not jagged");
            }
            for(int j = 0; j < columns; j++){//each col
                this.matrix[i][j] = matrix[i][j];//copy val from input array
            }
        }
    }

    /**
     * Getter to return the number of rows in matrix
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Getter to return the number of columns in matrix
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Return value at the specified row and column in a matrix
     * @param row row index
     * @param column column index
     * @return the value at specified postion
     * @throws IllegalArgumentException if indices arent in the matrix
     */
    public double getMatrix(int row, int column){
        //check to make sure the row/column is within the matrix
        if(row < 0 || row >= rows || column < 0 || column >= columns){
            throw new IllegalArgumentException("Index must be inside of the matrix");
        }
        return matrix[row][column];//return the value at specified position
    }

    /**
     * Set the value at specified row/columns in matrix
     * @param row row index
     * @param column column index
     * @param val value to set
     * @throws IllegalArgumentException if indices arent in the matrix
     */
    public void setMatrix(int row, int column, double val){
        //check to make sure the row/column is within the matrix
        if(row < 0 || row >= rows || column < 0 || column >= columns){
            throw new IllegalArgumentException("Index must be inside of the matrix");
        }
        matrix[row][column]=val;//set the value at positon
    }

    /**
     * Set number of rows in matrix
     * @param rows new number of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * set number of columns in matrix
     * @param columns new number of columns
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * Check to see if this matrix is equal to another matrix
     * Two matrices are equal if same dimensions and all elemnts are equal
     * @param matrix2 second matrix to compare with
     * @return true if matrices are equal, false otherwise
     */
    public boolean equals(Matrix matrix2){
        //initially check to see if same size (ex. 2x2=2x2 will continue, 2x2 !=2 x3 wont continue)
        if(this.rows != matrix2.rows || this.columns != matrix2.columns){
            return false; //matrix with different dimensions cant be equal
        }
        for(int i = 0; i < rows; i++){//loop through rows
            for(int j = 0; j < columns; j++){//loop through columns
                if(this.matrix[i][j] != matrix2.matrix[i][j]){//compare corresponding elemnts
                    return false;//if elements are different than return false
                }
            }
        }
        return true;//return true if all ements equal
    }

    /**
     * Creates and returns an identity matrix of specified size
     * An identity matrix is a square matrix with diagonal as ones and all other as zeros
     * @param size size of identity matrix
     * @return identity matrix
     * @throws IllegalArgumentException if size is less than or equal to 0
     */
    public static Matrix identity(int size){//static method as it does not depend on any instance of Matrix class(not tied to anything)
        if(size <=0){//check for invalid size
            throw new IllegalArgumentException("Size must be greater than 1");
        }
        Matrix identityMatrix = new Matrix(size, size);//create a new square matrix(all zeros)
        for(int i = 0; i < size; i++){//loop through rows
            //[i][i] is valid as will start 0,0 then 1,1 then 2,2....
            identityMatrix.matrix[i][i] = 1.0;//set diagonal to 1.0
        }
        return identityMatrix;//return matrix
    }

    /**
     * Creates and returns a submatrix of this matrix
     * Submatrix is defined by specified starting and ending row/col indices
     * @param startRow starting row index
     * @param startCol starting col index
     * @param endRow ending row index
     * @param endCol ending col index
     * @return submatrix
     * @throws IllegalArgumentException if indicies are out of bounds or invalid
     */
    public Matrix subMatrix(int startRow, int startCol, int endRow, int endCol){
        //check to see that bounds are valid
        if(startRow < 0 || startCol < 0 || endRow >= rows || endCol >= columns || startRow > endRow || startCol > endCol){
            throw new IllegalArgumentException("Invalid submatrix starting/ending bounds");
        }
        int subRow = endRow-startRow+1;//calculate number of rows in submatrix (offset by 1 as index 0 +1 is 1 row)
        int subCol = endCol-startCol+1;//calculate number of columns in submatrix also +1 to account for index0
        Matrix sub = new Matrix(subRow,subCol);//new matrix for submatrix
        for(int i = 0; i < subRow; i++){//run through each row in submatrix
            for(int j = 0; j < subCol; j++){//each column in submatrix
                //submatrix starts normally at 0,0... but for original matrix it is (0+startrow, 0+start col)
                sub.matrix[i][j] = this.matrix[startRow+i][startCol+j];//copy values from original
            }
        }
        return sub;//return submatrix
    }

    /**
     * return string representation of the matrix
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder mString = new StringBuilder();//create new string builder
        for(int i = 0; i < rows; i++){//iterate through rows
            for(int j = 0; j < columns; j++){//iterate through cols
                mString.append(matrix[i][j]);//append value at current position to string
                mString.append(" ");//add a space
            }
            mString.append("\n");//next row
        }
        return mString.toString();//convert stringbuilder to string and return it
    }

    /**
     * Add another matrix to this matrix and return result as a new matrix
     * @param matrix2 matrix to add
     * @return resulting matrix after addition
     * @throws IllegalArgumentException if matrices are different dimensions
     */
    public Matrix add(Matrix matrix2){
        //check for dimensions(need to be same size to add)
        if(this.rows != matrix2.rows || this.columns != matrix2.columns){
            throw new IllegalArgumentException("Matrixes must be same size to add");
        }
        Matrix added = new Matrix(rows, columns);//new matrix to return
        for(int i = 0; i < rows; i++){//iterate through rows
            for(int j = 0; j < columns; j++){//iterate through cols
                //add each element to the new matrix
                added.matrix[i][j] = this.matrix[i][j] + matrix2.matrix[i][j];
            }
        }
        return added;//return the result
    }

    /**
     * adds 2 matrices and retunrs result as new matric
     * @param m1 first matrix
     * @param m2 second matrix
     * @return resulting matrix after addition
     */
    public static Matrix add(Matrix m1, Matrix m2){//static as its a utility method that doesnt need an instance of Matrix class
        return m1.add(m2); //call instance method add
    }

    /**
     * Adds another matrix to this matrix in place
     * modifies the current matrix
     * @param matrix2 matrix to add
     * @throws IllegalArgumentException if matrices are different sizes
     */
    public void addInPlace(Matrix matrix2){//void as this modifies the matrix
        //check dimensions
        if(this.rows != matrix2.rows || this.columns != matrix2.columns){
            throw new IllegalArgumentException("Matrixes must be same size to add");
        }
        for(int i = 0; i < rows; i++){//iterate through rows
            for(int j = 0; j < columns; j++){//iterate through columns
                this.matrix[i][j] += matrix2.matrix[i][j];//add inputted matrix to this matrix
            }
        }
    }

    /**
     *Subtracts anthoer matrix from this matrix and reutns the result as a new matrix
     * @param matrix2 matrix to subtract
     * @return resulting matrix after subtraction
     * @throws IllegalArgumentException if matrices are different size
     */
    public Matrix subtract(Matrix matrix2){
        //check dimensions
        if(this.rows != matrix2.rows || this.columns != matrix2.columns){
            throw new IllegalArgumentException("Matrixes must be same size to subtract");
        }
        Matrix subtracted = new Matrix(rows, columns);//new matrix to store result
        for(int i = 0; i < rows; i++){//iterate through rows
            for(int j = 0; j < columns; j++){//iterate through columns
                //subtract this matrix from sent matrix in and store in new matrix
                subtracted.matrix[i][j] = this.matrix[i][j] - matrix2.matrix[i][j];
            }
        }
        return subtracted;//return subtracted matrix
    }

    /**
     * subtracts two matrixes and returns result as a new matrix
     * @param m1 first matrix
     * @param m2 second matrix
     * @return subtracted matrix
     */
    public static Matrix subtract(Matrix m1, Matrix m2){//send in two matrices
        //static as it doesnt rely on an instance of matrix
        return m1.subtract(m2);//call instance method subtraction to subtract
    }

    /**
     * Subtracts another matrix from current matrix and modifies current matrix
     * @param matrix2 matrix to subtract
     * @throws IllegalArgumentException if matrices are different sizes
     */
    public void subtractInPlace(Matrix matrix2){
        //check dimensions
        if(this.rows != matrix2.rows || this.columns != matrix2.columns){
            throw new IllegalArgumentException("Matrixes must be same size to subtract");
        }
        for(int i = 0; i < rows; i++){//rows iterate
            for(int j = 0; j < columns; j++){//columns iterate
                this.matrix[i][j] -= matrix2.matrix[i][j];//modify matrix by subtracting and directly modifying it
            }
        }
    }

    /**
     * Multiplies this matrix by a scalar value and returns result as new matrix
     * @param scalar value to multiply by
     * @return resulting matrix after scalar multiplication
     */
    public Matrix scalarMultiply(double scalar){
        Matrix scaledMatrix = new Matrix(rows, columns);//new matrix with same dimensions
        for(int i = 0; i < rows; i++){//go through rows
            for(int j = 0; j < columns; j++){//iterate through columns
                scaledMatrix.matrix[i][j] = this.matrix[i][j] * scalar;//new matrix becomes this matrix multiplied wiht scalar
            }
        }
        return scaledMatrix;//return new multiplied matrix
    }

    /**
     * multiplies a matrix by a scalar value and returns result as a new matrix
     * @param matrix2 matrix to multiply
     * @param scalar scalar value to multiply by
     * @return resulting matrix after scalar multiplication
     */
    public static Matrix scalarMultiply(Matrix matrix2,double scalar){
        //static as it does not depend on an instance of matrix but rather calls a seperate method
        return matrix2.scalarMultiply(scalar);//call instance method to perform scalar multiplication
    }

    /**
     * Multiplies this matrix by a scalar value in places modifying the current matrix
     * @param scalar scalar value to mulitply with
     */
    public void scalarInPlaceMultiply(double scalar){
        for(int i = 0; i < rows; i++){//iterate through rows
            for(int j = 0; j < columns; j++){//iterate through columns
                this.matrix[i][j] *= scalar;//multiply each element by scalar
            }
        }
    }

    /**
     * Multiplies this matrix by another matrix and returns result as a new matrix
     * @param matrix2 matrix to multiply by
     * @return resulting matrix after multiplication
     * @throws IllegalArgumentException if number of columns isnt equal to others rows
     */
    public Matrix multiply(Matrix matrix2){
        //m1 columns need to equal m2 rows for matrix multiplication
        if(this.columns != matrix2.rows){
            throw new IllegalArgumentException("Matrix1 columns do not equal Matrix2 rows.");
        }
        Matrix mulitpliedMatrix = new Matrix(this.rows, matrix2.columns);//new matrix to store
        for(int i = 0; i < this.rows; i++){//iterate through rows of m1
            for(int j = 0; j < matrix2.columns; j++){//iterate through columns of m2
                double sum = 0.0; //initialize sum for dot product(matrix multiplication)
                for(int k = 0; k < this.columns; k++){//iterate through m1 columns(Equal to m2 rows)
                    //multiplication is done by going through each row in m1 and col in m2 with common element k
                    //at element 0,0 it is all of row 0 (m1) * all of col 0 (m2)
                    sum += this.matrix[i][k] * matrix2.matrix[k][j];//compute dot product
                }
                mulitpliedMatrix.matrix[i][j] = sum;//store result in new matrix
            }
        }
        return mulitpliedMatrix;//return matrix
    }

    /**
     * Multiplies two matrices and returns result as new matrix
     * @param m1 first matrix
     * @param m2 second matrix
     * @return resulting matrix after multiplication
     */
    public static Matrix multiply(Matrix m1, Matrix m2){
        //static as dont need a seperate instance of matrix (already have 2)
        return m1.multiply(m2);//call instance method to multiply
    }

    /**
     * multiplies this matrix by another matrix in place, modifying current matrix
     * @param matrix2 matrix to multiply by
     * @throws IllegalArgumentException if columns in this matrix dont equal rows in other matrix
     */
    public void multiplyInPlace(Matrix matrix2){
        if(this.columns != matrix2.rows){
            throw new IllegalArgumentException("Matrix1 columns do not equal rows in other matrix");
        }
        Matrix multipliedMatrix = new Matrix(this.rows, matrix2.columns);//new matrix to store result
        for(int i = 0; i < this.rows; i++){//go through rows in m1
            for(int j = 0; j < matrix2.columns; j++){//columns in m2
                double sum = 0.0;//initialize sum
                for(int k = 0; k < this.columns; k++){//go through common length (m1 cols/m2 rows)
                    //ixk * kxj will become the ixj so k is common element
                    sum += this.matrix[i][k] * matrix2.matrix[k][j];//compute matrix multiplication
                }
                //need a new matrix as matrix multiplication relies on every element and cant update them till full multiplication is complete
                multipliedMatrix.setMatrix(i, j, sum);//call setter to set individual element in new matrix
            }
        }
        this.matrix = multipliedMatrix.matrix;//replaces all original data with multiplied data
        this.columns = multipliedMatrix.columns;//match the size of the multiplied matrix columns
    }
}
