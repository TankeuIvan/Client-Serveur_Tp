package tp_prog_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServeurChat extends Thread{
	protected String nomClient = "Inconnu";
	protected int nombreClient=0;
	protected boolean isActive = true;
	protected List<ThreadClient> listeClient = new ArrayList<ThreadClient>();  //Liste de Clients
	
	public static void main(String[] args) {
		new ServeurChat().start();

	}
	
	@Override
	public void run() {
		super.run();
		try {
			ServerSocket ss = new ServerSocket(1234);
			System.out.println("Demarrage du serveur chat...");
			while(isActive) {
				Socket socketClient = ss.accept();
				nombreClient++;
				ThreadClient threadClient = new ThreadClient(socketClient, nombreClient, nomClient);
				listeClient.add(threadClient);
				threadClient.start();
				
			} 
		}catch (IOException e) { e.printStackTrace();
			}
		
		
	}
	
	public class ThreadClient extends Thread{
		private Socket socketClient;
		private int nombreClient;
		private String nomClient = "Inconnu";
		
		public ThreadClient(Socket socket, int nombreClient, String nomClient) {
			super();
			this.socketClient = socket;
			this.nombreClient = nombreClient;
		}
		
		public void broadCast(String message, Socket socketClient) {
			try {
				for(ThreadClient client : listeClient) {
					if(client.socketClient!=socketClient && client.nomClient!="Inconnu") {
						PrintWriter printWriter = new PrintWriter(client.socketClient.getOutputStream(),true);
						printWriter.println(message);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			super.run();
			
			try {
				
				try {
					InputStream is = socketClient.getInputStream();
					InputStreamReader isr =  new InputStreamReader(is);
					BufferedReader br = new BufferedReader (isr);
					PrintWriter pw = new PrintWriter(socketClient.getOutputStream(), true);
					
					
					pw.println("~Bienvenu dans le chat client "+nombreClient+". Entrez votre nom: ");
					System.out.println("### "+nomClient+" Ip: "+socketClient.getRemoteSocketAddress()+" Tente de se connecter ");
					nomClient = "@"+br.readLine().toString();
					
					System.out.println("~Connexion établie avec "+nomClient+" : Client "+nombreClient+", Ip: "+socketClient.getRemoteSocketAddress());
					broadCast("~"+nomClient+" vient de rejoindre le chat !",socketClient);
						
					while(true) {
						String requeteClient = br.readLine().toString();
						String messageClient = "\n"+nomClient+": "+requeteClient;		
						broadCast(messageClient,socketClient);
						System.out.println("[!]Nouveau message de "+nomClient);	
					}
				} catch (NullPointerException e) {
					System.out.println("### "+nomClient+" Ip: "+socketClient.getRemoteSocketAddress()+" a quitté le serveur. ");
					if(nomClient!="Inconnu") broadCast("~"+nomClient+" vient de quitter le chat !",socketClient);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
		
	

}
