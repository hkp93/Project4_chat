/********************************************************************************
 * PROJECT 4 : NETWORK CHAT APPLICATION
 * PATNERS : HENVY PATEL & JANKI PATEL
 * DESCRIPTION: chatApp.java : This file contains the main for the program as well 
 *             as the basic GUI for running this application is established within
 *             this class.
 ********************************************************************************/
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
 
public class chatApp implements ActionListener {
	
		// Here we will add all the required components of the chat application
        private JFrame frame = new JFrame("Project 4 - Chat Application");
        private JTextArea textArea = new JTextArea();
        private JLabel online = new JLabel("Currently Online");
        private JTextArea clientDisplay = new JTextArea();
        private JTextField textField = new JTextField(30);
        private JButton sendButton = new JButton("Send");
       
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
                JScrollPane areaScrollPane = new JScrollPane(textArea);
                areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                areaScrollPane.setPreferredSize(new Dimension(430, 275));
                
                //adds the text to view all the clients
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
       
        public static void main(String[] args) {
                new chatApp();
        }
 
        @Override
        // HERE WE ONLY HAVE ONE ACTION LISTNER WHICH WILL BE LOCATED ON THE SEND
        //BUTTON WHUCH READ THE MESSAGE FROM THE TEXT BOX AND DISPLAYS ON THE SCREEN
        public void actionPerformed(ActionEvent arg0) {
                String message = textField.getText();
                textArea.append(message + "\n");
                textField.setText("");
        }
 
}
