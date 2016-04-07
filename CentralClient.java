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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
 
 
public class CentralClient implements ActionListener {
	
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

        public CentralClient() {
        		
        		//FRAME FOR THE DISPLAY
                frame.setSize(600, 375);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // creates a new panel to add all the fields in the jframes
                Panel p1 = new Panel();
               
                connected = false;
                // Adds the send button with the action listener here
                sendButton.addActionListener(this);
               // Adds the connect button with the action listener here
                connectButton.addActionListener(this);
                // adds the text field to view the messages exchanged
                textArea.setEditable(false);
                textField.setEditable(false);
    			sendButton.setEnabled(false);
    			username.setEditable(true);
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
                p1.add(askName);
                p1.add(username);
                p1.add(textField);
                p1.add(sendButton);
                p1.add(connectButton);
                // adds the panel to the frame
                frame.add(p1);
                frame.setVisible(true);
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
        
        // HERE WE ONLY HAVE ONE ACTION LISTNER WHICH WILL BE LOCATED ON THE SEND
        //BUTTON WHUCH READ THE MESSAGE FROM THE TEXT BOX AND DISPLAYS ON THE SCREEN
        @Override public void actionPerformed(ActionEvent arg0) {
            String message = textField.getText();
            //Get username 
            String nameInput = nameInput = username.getText();

            if(nameInput.equals(null))
            	nameInput = "Anonymous";
            
            textArea.append(nameInput + ": "+ message + "\n");
	        textField.setText("");
	        username.setEditable(false);
	        
	        if(connected && arg0.getSource() == sendButton)
            {
            	System.out.println("clicked on send button.");
            	doSendMessage(message);
            }
            if(arg0.getSource() == connectButton)
            {
            	System.out.println("clicked on connect button.");
            	doManageConnection();
            }
        }
        
        public void doSendMessage(String msg)
        {
        	try{
        		//send out the message read from the client input
	    		sendStream.writeObject(msg);
	        	sendStream.flush();
	        	System.out.println(msg);
        	}
        	catch(IOException e){System.err.println(e);}
        }
        
        public void doManageConnection()
        {
        	if(connected == false)
        	{
        		try{
        			//get ip address
        			InetAddress ip = InetAddress.getLocalHost();
        			//open a socket
	        		Socket echoSocket = new Socket(ip, 6789 );
	        		System.out.println(echoSocket);
	            	sendStream = new ObjectOutputStream(echoSocket.getOutputStream());
	            	sendStream.flush();
	            	
	                getStream = new ObjectInputStream(echoSocket.getInputStream());
	                //enable message input field
        			sendButton.setEnabled(true);
	                textField.setEditable(true);
	                connected = true;
        			connectButton.setText("Disconnect");
        		}
        		catch (UnknownHostException e) {
                	System.err.println("Unknown Host: " + e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        	}
        	else
        	{
        		try{
        			getStream.close();
        			sendStream.close();
        			sendButton.setEnabled(false);
        			connected = false;
        			connectButton.setText("Connect");
        		}
        		catch(IOException e){System.err.println("Error closing Socket " + e);}
        	}
        }
        
        //Main function for Client
        public static void main(String[] args){
    		CentralClient client = new CentralClient();
    		
    	}
}