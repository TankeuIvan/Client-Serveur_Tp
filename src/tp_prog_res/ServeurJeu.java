package tp_prog_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServeurJeu extends Thread{
	private int nombreClient=0;
	private boolean isActive = true;
	private boolean jeuFin = false;
	private int nombreSecret;
	private String gagnant;
	
	public static void main(String[] args) {
		new ServeurJeu().start();

	}
	
	@Override
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(1234);
			nombreSecret = new Random().nextInt(1000);
			System.out.println("Bienvenu au jeu de devinette ! Les clients doivent tenter de deviner le nombre secret: "+nombreSecret);
			while(isActive) {
				Socket socketClient = ss.accept();
				nombreClient++;
				new ThreadServeur(socketClient, nombreClient).start();
				
			} 
		}catch (IOException e) { e.printStackTrace();
			}
		
		
	}
	
	public class ThreadServeur extends Thread{
		private Socket socketClient;
		private int nombreClient;
		
		public ThreadServeur(Socket socket, int nombreClient) {
			super();
			this.socketClient = socket;
			this.nombreClient = nombreClient;
		}
		
		@Override
		public void run() {
			super.run();
			
			try {
					
					InputStream is = socketClient.getInputStream();
					InputStreamReader isr =  new InputStreamReader(is);
					BufferedReader br = new BufferedReader (isr);
					PrintWriter pw = new PrintWriter(socketClient.getOutputStream(), true);
					String ipClient = socketClient.getRemoteSocketAddress().toString();
					
					pw.println("Bienvenu client numero "+nombreClient);
					System.out.println("Connexion avec le client "+nombreClient+" : "+ipClient);
					
					pw.println("ESSAYEZ DE DEVINER LE NOMBRE SECRET.");
					
					while(true) {
						String req = br.readLine().toString();
						boolean isRequeteClientInteger = req.matches("-?\\d+");
						
						if(isRequeteClientInteger == true) {
							int requeteClient = Integer.parseInt(req);
							System.out.println("~Client "+nombreClient+" Tentative: "+requeteClient);
							if(jeuFin == false) {
								if(requeteClient > nombreSecret) {
									pw.println("\nC'est moins..");
								}else if(requeteClient < nombreSecret)
								{ pw.println("\nC'est plus..");
								}else {
									pw.println("\nBRAVO ! VOUS AVEZ TROUVE LE NOMBRE SECRET : "+nombreSecret);
									gagnant = "~Client "+nombreClient;
									System.out.println("LE Client "+gagnant+"  A TROUVE LE NOMBRE ");
									jeuFin=true; 
								}		
							
							}else{
								pw.println("\nJEU TERMINE ! "+gagnant+" A GAGNE LA PARTIE");
								
							}
						}else{
								pw.println("\nVEUILLEZ ENTRER UN NOMBRE ENTIER");
						}
						
						
					}
					
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
		
	

}
