package chenillardServer;

import java.io.IOException;

public class ChenillardServer {

	static Server server; 
	
	public static void main(String[] args) throws InterruptedException, IOException
	{
		int srvPort = Integer.valueOf(args[0]);
		int clients[];
		
		server = new Server(srvPort);
		
		while(server.lastClient == false)
		{
			if (server.waitClientConnection())
			{
				clients[];
			}
		}
		
		System.out.println("Sortie du While");
	}

}
