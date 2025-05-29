# Multiplayer Connect 4 (Team Challenge)

## 🏆 Overview
This project is a **multiplayer Connect 4 game** developed as part of a 48-hour team challenge in the Software Design course at the University of Iowa. Built using Java Sockets and Swing, the game supports **2–4 players**, each on their own client instance, and features a live lobby system, real-time gameplay, animations, a leaderboard, and post-game chat.

> 🥇 **1st Place** of 18 teams.

## 👥 Team Members
- Braden Miller  
- Aidan Doruska  
- Kyle Balk  

## 🎮 Key Features
- ✅ Supports 2–4 players  
- ✅ Customizable grid size and win condition (e.g., 6x7, 8x8, 9x10, 11x11 grids; 2–4 in a row)  
- ✅ Lobby system with real-time player count  
- ✅ Randomized background images in lobby  
- ✅ Animated chip drop for moves  
- ✅ Server-enforced turn order using `ReentrantLock` and `Condition`  
- ✅ "Spectator Mode" after winning  
- ✅ Final leaderboard with player rankings  
- ✅ Post-game chat room  
- ✅ Colorblind-friendly chip colors  

## 🧪 Technologies Used
- **Java** (11+)  
- **Java Swing** for GUI components  
- **Java Sockets** for client-server communication  
- **Multithreading** (`Runnable`, `ExecutorService`, `ReentrantLock`, `Condition`)  

## 🗂️ Key Classes

| Class                 | Role                                                                 |
|----------------------|----------------------------------------------------------------------|
| `Server`             | Accepts player connections, manages game state and broadcasts events |
| `Player`             | Handles per-client thread and game logic                             |
| `Client`             | Manages server connection and event responses                        |
| `LobbyController`    | Displays lobby and lets the leader configure settings                |
| `GameController`     | Main gameplay screen, handles board logic and animations             |
| `PostGameController` | Displays leaderboard and chat after game ends                        |
| `BackgroundController` | Applies a random image to the lobby background                    |

## 🧩 Game Flow

1. First player becomes **leader** and configures the game:  
   - Grid size (`6x7`, `8x8`, `9x10`, `11x11`)  
   - Win condition (`2`, `3`, or `4` in a row)  
2. When **Start Game** is clicked, all players' screens transition to the game board  
3. Players are prompted when it’s their turn and can drop a chip by clicking a column  
4. After winning, a player enters **Spectator Mode**  
5. Game continues until only one active player remains  
6. Leaderboard and chat are shown to all players  

## 💻 How to Run

### Requirements
- Java 11 or higher  
- IntelliJ IDEA (recommended)

### Compile & Launch

1. Check the port/IP Address of your machine, and update if needed in Server.java and ClientTest.java
2. Compile all files: javac *.java
3. Run the server: java Server
4. Run each client (on different terminals or machines): java ClientTest

> ⚠️ **IMPORTANT:** If running on multiple machines, make sure to:
> - Update the **IP address** and **port** in `Client.java` to match the server machine  
> - Ensure firewall settings allow traffic through the selected port  
> - Run all clients **after** starting the server

> The **first client to connect** becomes the lobby leader and can set the grid and win conditions.

## 🧭 Controls & Interface

- Click a column to drop a chip  
- See whose turn it is via the top banner  
- Winning players enter **Spectator Mode**  
- Chat becomes available in the **post-game screen**

## 📄 Documentation
- [Connect 4 Project - Developer's Guide.pdf](./Connect%204%20Project%20-%20Developer's%20Guide.pdf)  
- [Connect 4 Project - User's Guide.pdf](./Connect%204%20Project%20-%20User's%20Guide.pdf)  
- [Connect 4 Project - Executive Summary.pdf](./Connect%204%20Project%20-%20Executive%20Summary.pdf)  

## 🎥 Demo Video

[Click here to watch the demo video](https://drive.google.com/file/d/1MrCfX0QgOVw-uY2acYdKtSOYvoLvCSxO/view?usp=sharing)

### UML Diagram

![Connect4 UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/TeamChallenge/docs/UML_diagram.png)

## 🏁 Notes

- All game logic and UI sync is managed server-side to ensure fairness.  
- GUI animations and layout adapt dynamically to grid size and player actions.  
- Built in under **48 hours** as a team sprint challenge.  
- Tested locally with multiple clients; server handles all turn-order logic and win validation.

## 📬 Contact
- [Braden Miller](https://github.com/bradenmiller22)

