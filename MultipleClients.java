import java.net.InetAddress;

public class MultipleClients {

	public String name;
	public InetAddress addre;
	public int port;
	private final int ID;
	public int attempt =0;
	
	public MultipleClients(String name , InetAddress address , int port,final int ID){
		this.name = name;
		this.addre = address;
		this.port = port;
		this.ID =ID;
	}
	public int getID(){
		return ID;
	}
}
