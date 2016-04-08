/********************************************************************************
 * PROJECT 4 : NETWORK CHAT APPLICATION
 * PATNERS : HENVY PATEL & JANKI PATEL
 * DESCRIPTION:MAIN.JAVA : contains the main for the program
 ********************************************************************************/
import java.io.IOException;
import java.net.*;

import javax.swing.JFrame;

public class MAIN {
	public static void main(String[] args){
//		try {
//			ServerSocket ss = new ServerSocket(6789);
//			while(true)
//			{
//				CentralServer client1 = new CentralServer(ss.accept());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		CentralServer server = new CentralServer();
		//client1.RunServer();
		
	}
}
