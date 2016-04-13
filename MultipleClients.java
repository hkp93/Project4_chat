import java.net.InetAddress;

public class MultipleClients{
    
    public String name;
    public InetAddress addr;
    public static int port;
    protected int ID;
    public int attempt =0;
    
    public MultipleClients(){}
    
    public MultipleClients(String name , InetAddress address , int port, int ID){
        this.name = name;
        this.addr = address;
        this.port = port;
        this.ID =ID;
        
        System.out.println(name + "  " + address + "  " + port + "  " + ID);
    }
    
    public int getID(){
        return ID;
    }
    public int getPort()
    {
        return port;
    }
    
    public InetAddress getIp()
    {
        return addr;
    }
    
    public String getName()
    {
        return name;
    }
}
