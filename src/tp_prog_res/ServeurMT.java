package tp_prog_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMT extends Thread{
	private int nombreClient=0;
	private boolean isActive = true;
	
	public static void main(String[] args) {
		new ServeurMT().start();

	}
	
	@Override
	public void run() {
		super.run();
		try {
			ServerSocket ss = new ServerSocket(1234);
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
				pw.println("Bienvenu client numero "+nombreClient);
				System.out.println("Connexion avec le client "+nombreClient+" : "+socketClient.getRemoteSocketAddress());
				
				while(isActive) {
					String requetClient = br.readLine().toString();
					pw.println(requetClient.length());
				}
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
		
	

}
