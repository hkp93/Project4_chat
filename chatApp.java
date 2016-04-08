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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class chatApp implements ActionListener {
    
    // Here we will add all the required components of the chat application
    private JFrame frame = new JFrame("Project 4 - Chat Application");
    private JTextArea textArea = new JTextArea();
    private JLabel online = new JLabel("Currently Online");
    private JTextArea clientDisplay = new JTextArea();
    private JTextField textField = new JTextField(30);
    private JButton sendButton = new JButton("Send");
    // to send the stream to  the other clients
    private ObjectOutputStream sendStream;
    // to get the stream from the other clients
    private ObjectInputStream getStream;
    // establishing a server
    private ServerSocket CentralServer;
    // setting up the connection between computers
    private Socket connection;
    
    // constructor for the chatApp
    public chatApp() {
        
        //FRAME FOR THE DISPLAY
        frame.setSize(600, 375);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // creates a new panel to add all the fields in the jframes
        Panel p1 = new Panel();
        
        // Adds the send button with the action listener here
        sendButton.addActionListener(this);
        // adds the text field to view the messages exchanged
        textArea.setEditable(false);
        textField.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(430, 275));
        
        //adds the text to view all the clients(current online users)
        clientDisplay.setEditable(false);
        JScrollPane areaScrollPane1 = new JScrollPane(clientDisplay);
        areaScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane1.setPreferredSize(new Dimension(120, 200));
        online.setPreferredSize(new Dimension(120,40));
        // Adds all the fields in the panel
        p1.add(areaScrollPane);
        //add a label to the client display
        //p1.add(online);
        p1.add(areaScrollPane1);
        p1.add(textField);
        p1.add(sendButton);
        // adds the panel to the frame
        frame.add(p1);
        frame.setVisible(true);
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
    
    @Override
    // HERE WE ONLY HAVE ONE ACTION LISTNER WHICH WILL BE LOCATED ON THE SEND
    //BUTTON WHUCH READ THE MESSAGE FROM THE TEXT BOX AND DISPLAYS ON THE SCREEN
    public void actionPerformed(ActionEvent arg0) {
        String message = textField.getText();
        textArea.append("UserName: "+ message + "\n");
        textField.setText("");
    }
    
}