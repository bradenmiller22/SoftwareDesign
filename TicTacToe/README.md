# TicTacToe Game

## Problem Statement
Implement a Tic-Tac-Toe game using inheritance and polymorphism. Create a `Board` class for the 3x3 grid, a `Player` abstract class with `HumanPlayer` and `ComputerPlayer` subclasses, and a `Main` class to handle game flow. The game supports human vs. human, human vs. computer, and computer vs. computer modes.

## Developer Documentation

### 1. Board
- **Variables**: `gameBoard (char[][])`
- **Methods**:
  - Constructor initializes empty board
  - `getGameBoard()`, `validMove()`, `boardFull()`, `checkWin(char)`, `makeMove(int[], char)`, `toString()`

### 2. Player (abstract)
- **Variables**: `letter (char)`
- **Methods**:
  - Constructor, `getLetter()`, `setLetter(char)`, abstract `getMove(Board)`, `toString()`

### 3. HumanPlayer (extends Player)
- **Variables**: `scanner (Scanner)`
- **Methods**:
  - Constructor
  - `getMove(Board)`: prompts user for move via console

### 4. ComputerPlayer (extends Player)
- **Methods**:
  - `getMove(Board)`: selects a random valid move

### 5. Main
- Prompts user to select game mode and runs the game loop until win/tie.

## UML Diagram
![UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/TicTacToe/doc/umlDiagram.png)

## Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

## User Documentation

### Run the Program
1. Open `Main.java`
2. Compile and run

### Play the Game
1. Choose a game mode:
   - `1`: Human vs Human
   - `2`: Human vs Computer
   - `3`: Computer vs Computer
2. Enter moves in the format `row column` (e.g., `1 2`)
3. Game ends on win or tie

## Source Code
[View Code](https://github.com/bradenmiller22/SoftwareDesign/tree/main/TicTacToe/src)
