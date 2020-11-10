
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameThreadPoolServer implements Constants{

	private Socket aSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private int numOfConnections;
	private Player xPlayer, oPlayer;
	private String xName, oName;
	private ExecutorService pool;
    //private Board board;
	public GameThreadPoolServer() {
		try {
			serverSocket = new ServerSocket(9898);
			pool = Executors.newFixedThreadPool(8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//board = new Board();
	}

	public void runServer() {
        System.out.println("Server is running..."); 
		try {
			while (true) {
				aSocket = serverSocket.accept();
				System.out.println("Connection " + (numOfConnections + 1) + " accepted by the server!");
				socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
				socketOut = new PrintWriter(aSocket.getOutputStream(), true);
				
				numOfConnections++;
				
				if (numOfConnections % 2 == 1) {
					//board.setSocket(socketOut);
					//board.display();
					socketOut.println("You are Xplayer. What is the name for X player?");
					xName = socketIn.readLine();
					xPlayer = new Player(xName, LETTER_X);
				    xPlayer.setSocket(socketIn, socketOut);
					socketOut.println("Waiting for your opponent to connect");
				}
				else if(numOfConnections % 2 == 0) {
					//board.setSocket(socketOut);
					//board.display();
					socketOut.println("You are oPlayer. Your opponent is " + xName + ", What is the name for O player?");
					oName = socketIn.readLine();
				    oPlayer = new Player(oName, LETTER_O);
				    xPlayer.getSocketOut().println("connected opponent " + oName);
				    oPlayer.setSocket(socketIn, socketOut);
				    Game theGame = new Game(xPlayer, oPlayer);
				    
					pool.execute(theGame);
					System.out.println("New game instance started in one thread");
				}
			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		pool.shutdown();
		
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		GameThreadPoolServer myServer = new GameThreadPoolServer();
		myServer.runServer();
	}
}
