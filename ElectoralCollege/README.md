# Electoral College Simulator

## Problem Statement
Implement an Electoral College Simulator using Java Swing to recreate the U.S. presidential election system. The program allows users to select a state, choose a party (Democrat, Republican, or Undecided), and submit votes. When one party reaches 270 votes, a winner is declared.

## Developer Documentation

### ElectoralCollege Class
- **Variables**:
  - `democratVotes`, `republicanVotes`, `undecidedVotes`
  - `previousVotes[]`, `states[]`, `stateVotes[]`
- **Methods**:
  - `ElectoralCollege()`: sets up GUI with `JComboBox`, `JRadioButton`, and `JButton`
  - `updateVotes()`: updates total vote counts based on selection
  - GUI Components:
    - `JComboBox`: select state
    - `JRadioButton`: select party
    - `JButton`: submit vote
    - `JTextField`: show vote totals and winner

### Main Class
- Creates instance of `ElectoralCollege` and displays GUI.

## UML Diagram
![UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/ElectoralCollege/doc/uml.png)

## Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

## User Documentation

### Run the Program
1. Open `Main.java`
2. Compile and run
3. The GUI will appear

### Use the GUI
1. Select a state from the dropdown
2. Choose a party (Democrat, Republican, or Undecided)
3. Click **Submit Vote**
4. Totals will update; winner is declared at 270 votes

## Source Code
[View Code](https://github.com/bradenmiller22/SoftwareDesign/tree/main/ElectoralCollege/src)
