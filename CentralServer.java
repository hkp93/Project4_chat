/********************************************************************************
 * PROJECT 4 : NETWORK CHAT APPLICATION
 * PATNERS : HENVY PATEL & JANKI PATEL
 * DESCRIPTION: CentralServer.java : This file Establishes the central
 * 				connection between the clients by streaming
 * 			    and sockets for the program as well  as the basic GUI for
 *              running this application is established within
 *              this class.
 ********************************************************************************/
import java.awt.Dimension;
import java.awt.Panel;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class CentralServer{
    
    // Here we will add all the required components of the chat application
    private JFrame frame = new JFrame("Project 4 - Chat Application");
    private JTextArea textArea = new JTextArea();
    private JLabel online = new JLabel("Currently Online");
    private JTextArea clientDisplay = new JTextArea();
    private JLabel askName = new JLabel("Enter your username:");
    private JTextField username = new JTextField(10);
    private JTextField textField = new JTextField(30);
    private JButton sendButton = new JButton("Send");
    private JButton connectButton = new JButton("Connect");
    // to send the stream to  the other clients
    private ObjectOutputStream sendStream;
    // to get the stream from the other clients
    private ObjectInputStream getStream;
    // establishing a server
    private ServerSocket CentralServer;
    // setting up the connection between computers
    private Socket connection;
    Boolean connected;
    // constructor for the chatApp
    public CentralServer() {
        RunServer();
    }
    
    //Once the GUI is set we now will establish our connection
    // Class : RunServer -
    public void RunServer(){
        
        try{
            // 6789 - port number , where we want to connect
            // 100 - backlog , number of people can wait
            CentralServer = new ServerSocket(6789,100);
            while(true){
                try{
                    // here we will connect and have a chat
                    System.out.println("Waiting for Connection");
                    waitForConnection(); // To-do :waits for a user to connect
                    setupStream(); // To-do :initializing the streaming
                    chatting(); // to-do : while we are chatting
                }catch(EOFException c){
                    // this will be encountered when the stream ends
                    showMessage("\n Connection End !\n ");
                }finally{ // make sure we close the socket once we are done
                    closeApp();//closeCrap();
                }
            }
        }catch(IOException c){
            c.printStackTrace();
        }
        
    }
    
    // Method : waitforconnection - this method will wait for the connection to be
    //            established between the clients and the server
    private void waitForConnection() throws IOException{
        showMessage("Current Status : Waiting for a valid connection!\n");
        connection = CentralServer.accept();
        showMessage("Current Status : Connected to " + connection.getInetAddress().getHostName());
    }
    
    // Method : setupStream : set up the Stream with the connection found
    private void setupStream() throws IOException{
        // setup the output stream
        sendStream = new ObjectOutputStream(connection.getOutputStream());
        sendStream.flush();
        getStream = new ObjectInputStream(connection.getInputStream());
        showMessage("Current Status : Stream Setup Complete \n");
    }
    // Method : chatting - the method in which the actual chatting takes
    //      place
    private void chatting() throws IOException{
        String message = "Connected...." ;
        sendMessage(message);
        canType(true);
        // continue a conversation until the client wants to
        do{
            try{
                message = (String)getStream.readObject();
                showMessage("\n" +message);
                
                System.out.println("server: "+ message);
            }catch(ClassNotFoundException r){
                showMessage("\n Not a valid object");
            }
        }while(!message.equals("END"));
    }
    
    
    //Method : closeApp - Close the application(streams and socket) when done with it
    private void closeApp(){
        showMessage("\n Current Status :Closing the connections \n");
        canType(false);
        try{
            sendStream.close();
            getStream.close();
            connection.close();
            
        }catch(IOException f){
            f.printStackTrace();
        }
    }
    
    // Method : sendMessage : sends the message to the client
    private void sendMessage(String w) throws IOException{
        try{
            sendStream.writeObject("SERVER - " + w);
            sendStream.flush();
            showMessage("\n SERVER - " + w);
            
        }catch(IOException z){
            textArea.append("\n Message not send\n");
        }
    }
    
    // Method : showMessage: shows the message on the text feild
    private void showMessage(final String text){
        SwingUtilities.invokeLater(
                                   new Runnable(){
        				public void run(){
                            textArea.append(text);
                        }
        }
                                   );
    }
    
    // Method canType : allows the user to type
    private void canType(final boolean tof){
        SwingUtilities.invokeLater(
                                   new Runnable(){
        				public void run(){
                            textField.setEditable(tof);
                        }
        }
                                   );
        
    }
}
