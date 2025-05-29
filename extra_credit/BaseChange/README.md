# Base Change GUI (JavaFX)

## Problem Statement
Implement a JavaFX-based GUI program that converts numbers between base 2, 8, 10, and 16. The user selects the current and desired base, inputs a number, and the result is displayed after validation. The application includes proper input handling and error checking.

## Developer Documentation

### BaseChangeController
- **Variables**:
  - `currentBase (ComboBox<String>)`, `desiredBase (ComboBox<String>)`
  - `outputField (TextField)`, `textField (TextField)`
- **Methods**:
  - `initialize()`: sets up base options
  - `getBase(String)`: converts string to numeric base
  - `isValidBase(String num, int base)`: checks if input is valid for given base
  - `convertToDecimal(String num, int base)`: converts input to base 10
  - `convertFromDecimal(int num, int base)`: converts base 10 to target base
  - `buttonPressed(ActionEvent)`: handles conversion on button click

### JavaFX GUI Components
- Two ComboBoxes for base selection
- One TextField for input
- One TextField for output

### BaseChange (Driver)
- Launches the JavaFX application

## UML Diagram
![UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/BaseChange/doc/BaseChangeUML.png)

## Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

## User Documentation

### Run the Program
1. Open `BaseChange.java`
2. Compile and run

### Use the GUI
1. Enter a number in the input field
2. Choose current and desired base from dropdowns
3. Click the convert button to see the result

## Source Code
[View Code](https://github.com/bradenmiller22/SoftwareDesign/tree/main/BaseChange/src)
