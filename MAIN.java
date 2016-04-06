/********************************************************************************
 * PROJECT 4 : NETWORK CHAT APPLICATION
 * PATNERS : HENVY PATEL & JANKI PATEL
 * DESCRIPTION:MAIN.JAVA : contains the main for the program
 ********************************************************************************/
import javax.swing.JFrame;

public class MAIN {
	public static void main(String[] args){
		CentralServer client1 = new CentralServer();
		client1.RunServer();
	}
}
