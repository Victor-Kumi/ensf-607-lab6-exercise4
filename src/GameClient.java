
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClient {
	
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private BufferedReader stdIn;
	private String line;
	private String response;
	public GameClient (String serverName, int portNumber) {
		
		try {
			aSocket = new Socket (serverName, portNumber);
			stdIn = new BufferedReader (new InputStreamReader (System.in));
			socketIn = new BufferedReader (new InputStreamReader (aSocket.getInputStream()));
			socketOut = new PrintWriter (aSocket.getOutputStream(), true);
		}catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void communicate () {
		provideServerWithName();
		while (!response.equals("GAME OVER")) {
			try {
				//response = "";
				response = socketIn.readLine();  
				if (!response.equals("GAME OVER"))
				    System.out.println(response);
				if (response.contains("enter the row")) {
					line = stdIn.readLine();
					socketOut.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 	
		}
		closeSocket ();	
	}
	
	private void closeSocket () {
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void provideServerWithName() {
		line = "";
		response = "";
		try {
			//response = socketIn.readLine();
			//System.out.println(response);
			response = socketIn.readLine();
			if (response != null) {
			    System.out.println(response);
			    line = stdIn.readLine();
			    socketOut.println(line);
			}    
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
//	private void provideServerWithName() {
//		line = "";
//		response = "";
//		while (!response.contains("Waiting")) {
//		try { 
//			response = socketIn.readLine();
//			//System.out.println(response);
//			//response = socketIn.readLine();
//			if (response != null) {
//			    System.out.println(response);
//			    if (response.contains("You are")) {
//			    line = stdIn.readLine();
//			    socketOut.println(line);
//			    }
//			}    
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		}
//		response = "";
//	}
	
	public static void main (String [] args) throws IOException {
		
		GameClient aClient = new GameClient ("localhost", 9898);
		aClient.communicate();
		
	}
}

