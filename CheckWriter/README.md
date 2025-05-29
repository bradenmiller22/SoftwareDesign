# CheckWriter

## Problem Statement
Implement a CheckWriter system that validates user input for monetary amounts and converts the input into two forms: a protected amount with asterisks and a written amount in words. The program must handle proper formatting (e.g., 2 decimal places, commas) and validate input robustness.

## Developer Documentation

### Check Class
- **Variables**:
  - `totalSpaces (int)`, `units[]`, `middle[]`, `tens[]`
- **Methods**:
  - `validateAmount(String num)`: checks for valid format
  - `validateComma(String num)`: checks proper comma placement
  - `protectedAmount(double amount)`: converts to 9-digit protected format
  - `convertWords(double amount)`: converts amount to words
  - `convertNum(int num)`: helper for 0–999

### Main Class
- Demonstrates CheckWriter functionality

### CheckTest Class
- Contains 31 JUnit tests to verify Check functionality

## UML Diagram
![UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/CheckWriter/doc/CheckWriterUML.png)

## Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

## User Documentation

### Option 1: Run Main Program
1. Open `Main.java`
2. Compile and run
3. Input an amount (e.g., `1,234.56`)
4. View protected and word-form output

### Option 2: Run Tests
1. Open `CheckTest.java`
2. Compile and run
3. Ensure all 31 tests pass

## Source Code
[View Code](https://github.com/bradenmiller22/SoftwareDesign/tree/main/CheckWriter/src)
