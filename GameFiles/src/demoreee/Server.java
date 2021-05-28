package demoreee;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.net.DatagramSocket;

/**
 * The server that is used by the game to connect the players when they want to play multiplayer.
 */
public class Server{

	//Vector to store the active clients
	static Vector<ClientHandler> activePlayers = new Vector<>();

	//stores the number of players (clients)
	static int noPlayers = 0;

	//Create a LevelData object
	static LevelData lvl1 = new LevelData();

	private static DatagramSocket socket4435;
	private static DatagramSocket socket4437;

	private static ObjectOutputStream oos1 = null;
	private static ObjectOutputStream oos2 = null;

	public static void main(String[] args) throws IOException{
		System.out.println("Server started");


		//Create TCP socket for listening on port 1234
		ServerSocket ss = new ServerSocket(1234);

		Socket s;

		//Initialise sockets for sending data
		socket4435 = new DatagramSocket(4435);
		socket4437 = new DatagramSocket(4437);

		//Generate the first level
		lvl1.genLevel();

		while (noPlayers < 2) {
			//Accept the incoming request
			s = ss.accept();

			System.out.println("New Player request received: " + s);

			//Create a TCP output stream to send the levelData
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());

			ObjectOutputStream oos = createOos(dos);
			System.out.println("Creating a new handler for this player"); 

			if (noPlayers == 0) {
				oos1 = oos;
			}
			else if(noPlayers == 1) {
				oos2 = oos;
			}

			// Create a new handler object for handling this request 
			ClientHandler clientHandler = new ClientHandler(s,"player" + noPlayers, oos, lvl1, socket4435, socket4437); 

			//Add this player to active player list 
			activePlayers.add(clientHandler); 
			System.out.println("Added player" + noPlayers + " to active player list"); 

			//Increment noPlayers for new players 
			noPlayers++;
		}
		
		oos1.writeInt(57);
		oos2.writeInt(57);
		 
		Thread thread = new Thread(activePlayers.get(0)); 
		Thread thread2 = new Thread(activePlayers.get(1)); 
		
		thread.start(); 
		thread2.start();
		ss.close();
	}

	
	
	/**
	 * A function to create an object output stream. Used to send the level data.
	 * @param dos - The data output stream used to create the object output stream
	 * @return - The object output stream created or null
	 */
	private static ObjectOutputStream createOos(DataOutputStream dos) {
		for (int i = 0; i<5; i++) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(dos);
				return (oos);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Failed to create object output stream");
		return null;
	}


}

