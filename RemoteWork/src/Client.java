import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

/**
 * Client connects to ServerSocket and allows for retrieving and uploading files to/from Server
 * Uses GUI to allow for typing file names and contents to retrieve and upload
 * @author bmiller38
 */
public class Client extends JFrame{
    /**
     * Text Field for user input for a file name
     */
    private JTextField nameField = new JTextField(20);//user input field for filename
    /**
     * Text area for displaying file contents
     */
    private JTextArea fileArea = new JTextArea(30,50);//test area to display file contents
    /**
     * Button for retrieving a file from a server
     */
    private JButton retrieveButton = new JButton("Retrieve");//button for retrieving file from server
    /**
     * Button for uploading a file to the server
     */
    private JButton uploadButton = new JButton("Upload");//button for uploading file to server

    /**
     * Constructor to build and initialize the GUI
     */
    public Client(){

        super("Client");//title of GUI

        JPanel topPanel = new JPanel();//filename text field
        topPanel.add(nameField);//uses panel to organize components

        JPanel buttonPanel = new JPanel();//action buttons
        buttonPanel.add(retrieveButton);//panel to organize
        buttonPanel.add(uploadButton);

        JScrollPane displayPane = new JScrollPane(fileArea);//use a scrolling component for the text field

        setLayout(new BorderLayout());//create main window layout
        add(displayPane, BorderLayout.CENTER);//file area in the center
        add(topPanel, BorderLayout.NORTH);//filename at the top
        add(buttonPanel, BorderLayout.SOUTH);//buttons on the bottom

        //add listeners to buttons so when pressed it goes to sendCommand
        retrieveButton.addActionListener(e -> sendCommand("RETRIEVE"));//add an action listener to button for "RETRIEVE"
        uploadButton.addActionListener(e -> sendCommand("UPLOAD"));//add action listener to button for UPLOAD

        setSize(600, 400);//set window size
        setVisible(true);//show window
        setResizable(true);//allow resizing
    }

    /**
     * Sends a command to the server to either retrieve or upload a file
     * @param command "RETRIEVE" to get a file, "UPLOAD" to send a file
     */
    public void sendCommand(String command){
        //Socket creates a connection to localhost and port 23630
        //used for two-way communication of sending/recieving responses
        try(Socket socket = new Socket("localhost", 23630);
            //PrintWriter sends text commands to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //BufferedReader reads text responses from the server line-by-line
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){//more efficient than scanner

            String fileName = nameField.getText().trim();//get file name input

            if(fileName.isEmpty()){//check if no filename
                //if no file name send a dialog box saying the error and enter a file
                JOptionPane.showMessageDialog(this, "Please enter a file name!");
                return;
            }

            out.println(command);//send command first  to the server (it looks for command first)
            out.println(fileName);//send filename second to the server

            if("RETRIEVE".equals(command)){//check for what command is
                String response = in.readLine();//read in what the server sent back (OK or ERROR)

                if("OK".equals(response)){//from server
                    fileArea.setText("");//clear contents of the file area
                    String line;
                    while(!(line = in.readLine()).equals("END")){//read lines until END
                        fileArea.append(line + "\n");//display line by line to file area in GUI
                    }
                }else{
                    JOptionPane.showMessageDialog(this, response);//show error message
                }
            } else if ("UPLOAD".equals(command)) {//if upload from command
                //send all information in the file area back to server for the server to read
                for(String line:fileArea.getText().split("\n")){
                    out.println(line);//send to server to read
                }
                out.println("END");//mark end of file
                String response = in.readLine();//read what the server sent back from upload
                JOptionPane.showMessageDialog(this, response);//display succes and or failure
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+ e.getMessage());//connection error to server
        }
    }
}
