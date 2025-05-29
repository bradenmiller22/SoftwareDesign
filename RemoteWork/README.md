# RemoteWork - Client/Server Text File Manager

## Problem Statement
Create a client-server application that allows a GUI-based client to retrieve or upload text files to a server. The user can select a file, view or edit its content, and send/receive it over the network. The server manages and stores all file interactions.

## Developer Documentation

### 1. Server
- **Variables**:
  - `displayArea (JTextArea)`: shows logs of server activity
- **Methods**:
  - `Server()`: sets up the server GUI
  - `waitForPackets()`: listens for client requests (`RETRIEVE`, `UPLOAD`)
  - `displayMessage(String)`: appends status messages to the display

### 2. Client
- **Variables**:
  - `nameField (JTextField)`, `fileArea (JTextArea)`
  - `retrieveButton`, `uploadButton`
- **Methods**:
  - `Client()`: builds GUI and sets up event handling
  - `sendCommand(String)`: sends client commands and file contents to server

### 3. ServerTest and ClientTest
- Driver classes to launch the Server and Client GUIs respectively

## UML Diagram
![UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/RemoteWork/doc/RemoteWorkUML.png)

## Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

## User Documentation

### Setup
- Create directory: `RemoteWork/serverFiles/`

### Run the Program
1. Start the server by running `ServerTest.java`
2. Start the client by running `ClientTest.java`

### Using the GUI
- **To retrieve a file**:
  - Enter file name
  - Click `RETRIEVE`
  - File content (if found) will load in the text area

- **To upload a file**:
  - Enter file name
  - Edit file contents in the text area
  - Click `UPLOAD`
  - File will be saved to the server directory

## Source Code
[View Code](https://github.com/bradenmiller22/SoftwareDesign/tree/main/RemoteWork/src)
