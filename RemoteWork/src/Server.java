import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Server handles incoming client connections
 * Allows file retrieval/upload from/to a server
 * Uses swing to create a simple window for displaying server activity
 * @author bmiller38
 */
public class Server extends JFrame {
    /**
     * Area to show messages to the server
     */
    private JTextArea displayArea;//area to show server messages
    // set up GUI and DatagramSocket

    /**
     * Constructor to set up GUI components and initialize
     */
    public Server()
    {
        super("Server");//title of gui

        displayArea = new JTextArea(); //create displayArea for messages
        displayArea.setEditable(false);//cant edit - read only
        add(new JScrollPane(displayArea), BorderLayout.CENTER);//scrollable bar in the display
        setSize(400, 300); // set size of window
        setVisible(true); // show window

    }

    /**
     * Continuously waits for client connections and processes the commands
     * Checks for retrieve or upload buttons from the client
     */
    public void waitForPackets(){
        displayMessage("Waiting for connection\n");//initial message

        //ServerSocket is a type of socket that listens and waits for a client connection on a port
        try(ServerSocket serverSocket = new ServerSocket(23630)){//uses port 23630, server socket stays open waiting for new clients
            while(true){//loop forever continuously accepting new client connections ("upload/retrieve")

                //new socket object is created representing the connection to that specific client
                try(Socket socket = serverSocket.accept();//accept a connection request from a client

                    //uses InputStreamReader (which reads bytes from socket) and then decodes them into characters using bufferedreader
                    //BufferedrRader makes reading text from socket by buffering input, more effiecent than scanner at reading files
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//this allows full lines to be read from client

                    //PrintWriter wraps output stream of socket allowing text to be sent back to client
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true))//formats text to be sent to client, waits for after println from client
                //all of these within try block ^^^^ for catching errors in file
                {

                    displayMessage("Connected: " + socket.getInetAddress().getHostName() +"\n");//show client information

                    String command = in.readLine();//read command of "retrieve or upload" from BufferedReader class
                    String file = in.readLine();//read file name from clien
                    File serverFile = new File("oral_exam2/RemoteWork/serverFiles/" + file);//create a file object representing file on server

                    if("RETRIEVE".equals(command)){
                        if(serverFile.exists()){//check if file exists
                            out.println("OK");//sent to client file is good

                            //send file content line-by-line to the client to show
                            Files.lines(serverFile.toPath()).forEach(out::println);

                            out.println("END");//end of file signal
                            displayMessage("Sent contents of " + file + "\n");//show message in server
                        }else{
                            out.println("ERROR: file not found");//file is not found to client
                            displayMessage("Requested file not found: "+ file + "\n");//show in server
                        }
                    } else if ("UPLOAD".equals(command)) {//if upload
                        //printWriter allows for easy writing of strings
                        try(PrintWriter fileOut = new PrintWriter(new FileWriter(serverFile))){//open a local file for writing to from the file object made earlier
                            String line;
                            while(!(line = in.readLine()).equals("END")){//read every line that was sent by the client
                                fileOut.println(line);//write line to file
                            }
                            out.println("Successfully uploaded");//send to client
                            displayMessage("Recieved/sent file: "+file+"\n");//show in server
                        }
                    }
                }catch (IOException e){
                    //handle client error/disconnect
                    displayMessage("Client error: " +e.getMessage()+"\n");
                }
            }
        }catch (IOException e){
            //handle server error
            displayMessage("Server failed: " + e.getMessage() + "\n");
        }
    }

    /**
     * Update GUI from a background thread
     * @param messageToDisplay Message to send to textarea of server
     */
    private void displayMessage(final String messageToDisplay)
    {
        SwingUtilities.invokeLater(//waits for all threads to be done before sending
                new Runnable()
                {
                    public void run() // updates displayArea on GUI
                    {
                        displayArea.append(messageToDisplay); // display message
                    }
                }
        );
    }
}
