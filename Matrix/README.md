# Matrix

## Problem Statement
Implement a `Matrix` class to represent and manipulate mathematical matrices. The class should support basic matrix operations such as addition, subtraction, multiplication, scalar multiplication, and submatrix extraction. Also include utility methods like identity matrix creation, matrix equality checks, and string conversion.

## Developer Documentation

### Matrix Class
- **Variables**:
  - `rows (int)`, `columns (int)`
  - `matrix (double[][])`
- **Constructors**:
  - `Matrix(int rows, int columns)`
  - `Matrix(double[][] matrix)`
- **Methods**:
  - `getRows()`, `getColumns()`, `getMatrix(int row, int col)`, `setMatrix(int row, int col, double value)`
  - `add()`, `subtract()`, `multiply()`, `scalarMultiply()` – all available in instance, static, and in-place variants
  - `subMatrix(int startRow, int startCol, int endRow, int endCol)`
  - `equals(Matrix)`, `identity(int size)`, `toString()`

### Main Class
- Demonstrates functionality of the `Matrix` class

### MatrixTest Class
- Contains 12 JUnit tests to verify correctness of all operations

## UML Diagram
![UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/oral_exam1/Matrix/doc/Matrix.png)

## Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

## User Documentation

### Option 1: Run the Main Program
1. Open `Main.java`
2. Compile and run the program
3. View output for matrix operations: add, subtract, multiply, scalar multiply, submatrix extraction

### Option 2: Run Tests
1. Open `MatrixTest.java`
2. Compile and run the test suite
3. Confirm all 12 JUnit tests pass

## Source Code
[View Code](https://github.com/bradenmiller22/SoftwareDesign/blob/main/oral_exam1/Matrix/src)
