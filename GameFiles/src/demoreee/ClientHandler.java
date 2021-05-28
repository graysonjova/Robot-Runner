package demoreee;

//import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
//import java.util.Arrays;

class ClientHandler implements Runnable 
{  
	//String for name of thread e.g player0
	private String name; 
	private DatagramSocket socket4435;
	private DatagramSocket socket4437;
	public ObjectOutputStream oos = null;

	//LevelData object called lvlGen
	public LevelData lvlGen;

	Socket s; 

	boolean isPlaying; 
	private static byte[] buf = new byte[16];

	DatagramSocket curRec = null;
	DatagramSocket curSend = null;
	DatagramSocket oppRec = null;
	DatagramSocket oppSend = null;
	int curRecInt = 0;
	int curSendInt = 0;
	int oppRecInt = 0;
	int oppSendInt = 0;
	
	static DatagramPacket received = new DatagramPacket (buf, buf.length);

	/** Constructor. There is one ClientHandler per player running on the server.
	 * @param s The Socket
	 * @param name The name of the client e.g. player1
	 * @param oos ObjectsOutputStream that sends output to the client
	 * @param lvlGen The level generating
	 * @param socket4435 The socket of player one
	 * @param socket4437 The socket of player two
	 */
	public ClientHandler(Socket s, String name, ObjectOutputStream oos, LevelData lvlGen, DatagramSocket socket4435, DatagramSocket socket4437) {
		this.s = s; 
		this.oos = oos; 
		this.name = name;
		this.lvlGen = lvlGen;
		this.socket4435 = socket4435;
		this.socket4437 = socket4437;
		this.isPlaying=true;
	} 

	/**Sends the level using the provided object output stream.
	 * @param oos Object output stream to send the level on.
	 * @return "Success" or "Failure"
	 */
	private String sendLevel(ObjectOutputStream oos) {
		//Get the current level
		for (int i = 0; i<5; i++) {
			String[] curLvl = lvlGen.getLevel();
			try {
				oos.writeObject(curLvl);
				return ("Success");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		System.out.println("Failed to send the level");
		return ("Failure");
	}

	/**Creates a UDP datagram socket. Tries 5 times in case it fails. As part of defensive programming.
	 * @return a new DatagramSocket
	 */
	private DatagramSocket createDatagramSocket() {
		for (int i = 0; i<5; i++) {
			try {
				return new DatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Failed to create datagram socket");
		return null;
	}
	
	/**Converts an int to a byte array.
	 * @param value - The int to convert.
	 * @return The byte array
	 */
	public static byte[] intToByteArray(int value) {
		return new byte[] {
				(byte) (value >> 24),
				(byte) (value >> 16),
				(byte) (value >> 8),
				(byte)value};
		
	}

	/**The main server loop.
	 * @param sendFrom - The port sent from.
	 * @param sendIntPort - The port number to send to.
	 */
	private void serverLoop(DatagramSocket sendFrom, int sendIntPort) {
		Boolean bool = true;
		int i = -100;
		byte[] bytei = intToByteArray(i);
		while (bool == true) {
			try {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				curRec.receive(received);
				byte[] receivedByte = received.getData();
				received = new DatagramPacket(received.getData(), received.getLength(), received.getAddress(), sendIntPort);
				sendFrom.send(received);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}

	/**Communicates through TCP to organise the UDP ports before the game starts.
	 * @param oos - TCP object output stream already set up
	 * @param name - The name of the client.
	 * @return "Success" or "failure"
	 */
	private String organisePorts(ObjectOutputStream oos, String name) {
		if (name.equals("player0")) {
			try {
				oos.writeInt(4435);
			} catch (IOException e) {
				e.printStackTrace();
				return ("Failure");
			}
			
			//Socket to receive data from the current player
			curRec = socket4435;
			
			//Socket number of the socket to send data to the opponent
			oppSendInt = 4438;
			return ("Success");
		}
		else if (name.equals("player1")) {
			try {
				oos.writeInt(4437);
			} catch (IOException e) {
				e.printStackTrace();
				return ("Failure");
			}
			curRec = socket4437;
			oppSendInt = 4436;
			return ("Success");
		}
		else {
			return ("Failure");
		}
	}
	
	/**
	 * Changes the level in the clientHandler
	 */
	private static void changeLvl() {
		int i = -100;
		byte[] bytei = intToByteArray(i);
		received.setData(bytei);
	}

	/**Organises ports, sends the level and then starts the server loop.
	 *
	 */
	@Override
	public void run() { 		
		DatagramSocket sendFrom = null;
		sendFrom = createDatagramSocket();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Assign each player to a port
		organisePorts(oos, name);
		
		while (true) {
			sendLevel(oos);
			//Server loop receives data and forwards it to opponent
			serverLoop(sendFrom, oppSendInt);
		}
		

	}

}
