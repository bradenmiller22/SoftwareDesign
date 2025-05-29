# ScreenSaver GUI (JavaFX)

## Problem Statement
Design and implement a JavaFX-based screen saver that draws animated lines between random canvas edges. The number of lines is user-defined. The screen resets and restarts the animation when a new number is entered.

## Developer Documentation

### ScreenSaverController
- **Variables**:
  - `rand (SecureRandom)`: generates random positions/colors
  - `timeline (Timeline)`: controls animation timing
  - `canvas (Canvas)`: drawing surface
  - `userInput (TextField)`: line count input
  - `totalLines`, `currentLines`: track animation progress
- **Methods**:
  - `buttonPressed(ActionEvent)`: reads input and starts animation
  - `drawLines()`: handles random line drawing via animation
  - `getEdge(int edge, int width, int height)`: returns coordinate on canvas edge

### JavaFX GUI Components
- `Canvas`: drawing surface
- `TextField`: for user input
- `Timeline/KeyFrame`: controls the animation timing

### Main Class
- Launches JavaFX application

## UML Diagram
![UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/ScreenSaver/doc/ScreenSaverUML.png)

## Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

## User Documentation

### Run the Program
1. Open `ScreenSaver.java`
2. Compile and run

### Use the GUI
1. Enter a number in the text field for total lines
2. Press Enter to start drawing
3. Watch lines animate onto the canvas
4. Enter a new number to reset and start again

## Source Code
[View Code](https://github.com/bradenmiller22/SoftwareDesign/tree/main/ScreenSaver/src)
