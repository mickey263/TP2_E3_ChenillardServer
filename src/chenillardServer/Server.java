package chenillardServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Serveur basique UDP
 */
public class Server
{

	private int SrvPort;
	public boolean lastClient = false;
	public int clientIndex;
	public int clientPort;
	DatagramSocket socket;
	InetSocketAddress adrDest;
	
	Server(int SrvPort) throws IOException {
		// Initialize SrvPort
		this.SrvPort = SrvPort;
		this.lastClient = false;
		open();
	}
	
	public void open() throws IOException
	{
		//
		System.out.println("Demarrage du serveur ...");
		
		// Le serveur se declare aupres de la couche transport
		System.out.println(SrvPort);
		
		//Creation de la socket
		socket = new DatagramSocket(null);
		socket.bind(new InetSocketAddress(SrvPort));
	}
	
	public boolean waitClientConnection() throws IOException
	{
		// Attente du premier message
		byte[] bufR = new byte[2048];
		DatagramPacket dpR = new DatagramPacket(bufR, bufR.length);
		socket.receive(dpR);
		String message = new String(bufR, dpR.getOffset(), dpR.getLength());
		System.out.println("Message recu = "+message);
		
		Pattern p1 = Pattern .compile("I[0-9]{1,}");
		Pattern p2 = Pattern .compile("P[0-9]{4}");
		Matcher m1 = p1.matcher(message);
		Matcher m2 = p2.matcher(message);

		while (m1.find())
			clientIndex = Integer.valueOf(message.substring(m1.start()+1, m1.end()));
		while (m2.find())
			clientPort  = Integer.valueOf(message.substring(m2.start()+1, m2.end()));
		
		// Emission d'un message en retour
		byte[] bufE = new String("ACK").getBytes();
		DatagramPacket dpE = new DatagramPacket(bufE, bufE.length, 
				dpR.getAddress(),dpR.getPort());
		socket.send(dpE);
		System.out.println("Message envoye = ACK");
		return true;
	}
	
	public void close() throws IOException
	{
		// Fermeture de la socket
		socket.close();
		System.out.println("Arret du serveur .");
	}
		
}
