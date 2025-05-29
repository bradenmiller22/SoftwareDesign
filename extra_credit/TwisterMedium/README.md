# Twister Game Simulator (Java Swing)

## Problem Statement
Implement a Twister Game Simulator using Java Swing. The GUI features a spinner that randomly selects one of 16 Twister moves (e.g., left hand red). It displays the result using text and color. The game includes a bias mode to favor difficult moves.

## Developer Documentation

### Twister Class
- **Variables**:
  - `sides[]`: ["Left", "Right"]
  - `bodyPart[]`: ["Hand", "Foot"]
  - `colors[]`: ["Red", "Green", "Blue", "Yellow"]
  - `biasMoves[][]`: predefined difficult moves
  - `bias (boolean)`: toggles bias mode
  - `displayLabel (JLabel)`, `mainPanel (JPanel)`: GUI display components
- **Methods**:
  - `Twister()`: sets up GUI layout, listeners, and components
  - `spin()`: selects and displays a random (or biased) move
  - Bias toggle is triggered by pressing the `B` key
- **GUI Components**:
  - `JButton`: spins the spinner
  - `JLabel`: shows the current move
  - `JPanel`: changes background color based on move
  - `JOptionPane`: alerts when bias mode toggles

### Main Class
- Launches the GUI by creating a `Twister` instance

## UML Diagram
![UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/extra_credit/TwisterMedium/doc/TwisterUML.png)

## Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

## User Documentation

### Run the Program
1. Open `Main.java`
2. Compile and run

### Use the GUI
1. Click the `Spin` button to generate a random move
2. View the move text and background color
3. Press `B` on your keyboard to toggle bias mode on or off

## Source Code
[View Code](https://github.com/bradenmiller22/SoftwareDesign/tree/main/extra_credit/TwisterMedium/src)
