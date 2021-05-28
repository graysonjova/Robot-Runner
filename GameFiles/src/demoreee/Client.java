package demoreee;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Client connects to the server and communicates by sending and receiving updates.
 */
public class Client{

	//Port for TCP
	final static int ServerPort = 1234;
	private static int waitTime = 100;

	private byte[] buf = new byte[320];
	int sendPort = -10;
	private DatagramPacket packet;
	private DataInputStream dis = null;
	private ObjectInputStream ois = null;
	private DatagramSocket socketSend = null;
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private String[] curLvl = null;

	DataOutputStream dos = null;
	private DatagramSocket readSocket = null;

	public Client(String playerMode) throws UnknownHostException, IOException{
		InetAddress ip = InetAddress.getByName("localhost");
		System.out.println("Client started");
		Socket socket = new Socket(ip,ServerPort);
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		createOis();
		if (playerMode.equals("multiplayer")) {
			waitForPlayer2();
		}

		//Thread to send the location of the current player
		Thread sendLocation = new Thread(new Runnable() {
			public void run() {
				initSendSocket();
				double sendx = 0;
				double sendy = 0;
				while (true) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					//Get the x and y coordinates
					try {
						sendx = (Main.player1.entity.getTranslateX());
						sendy = (Main.player1.entity.getTranslateY());

						//Put the bytes of the coordinates into arrays
						byte[] xByte = ByteBuffer.allocate(8).putDouble(sendx).array();
						byte[] yByte = ByteBuffer.allocate(8).putDouble(sendy).array();

						//Write the two arrays to the output stream
						writeBytes(xByte, yByte);

						//Convert the output stream to a byte array called buf
						buf = outputStream.toByteArray();
						outputStream.reset();

						//Create a datagram packet with the message, message length, destination address and destination port
						packet = new DatagramPacket(buf,buf.length, ip, sendPort);

						sendPacket(packet);
						//Thread.sleep(waitTime);
					}
					catch(NullPointerException e){
					}

				}
			}
		});

		//Create a thread to read the location of the other player
		Thread readLocation = new Thread(new Runnable() {
			public void run() {
				readPort();
				sendLocation.start();
				waitForLevel();
				System.out.println("Read the level");
				initReadSocket();
				DatagramPacket packet = new DatagramPacket(buf, 16);
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						readSocket.receive(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					byte[] receivedData = packet.getData();
					byte[] xBytes = new byte[8];
					System.arraycopy(receivedData, 0, xBytes, 0, 8);
					byte[] yBytes = new byte[8];
					System.arraycopy(receivedData, 8, yBytes, 0, 8);
					double oppXdouble = ByteBuffer.wrap(xBytes).getDouble();
					double oppYdouble = ByteBuffer.wrap(yBytes).getDouble();
					try {
						Main.oppPlayer.entity.setTranslateX(oppXdouble);
						Main.oppPlayer.entity.setTranslateY(oppYdouble);
					}
					catch (NullPointerException e) {
						System.out.println("readLocation error");
					}
				}
			}
		});
		readLocation.start();
	}

	/**Read an incoming UDP port from the server
	 * @return The port number received
	 */
	private int readPort() {
		Boolean waitingForPort = true;
		System.out.println("Waiting for port");
		while (waitingForPort) {
			try {
				sendPort = ois.readInt();
				System.out.println("Sending to " + sendPort);
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sendPort;
	}

	/**Creates the Object Input Stream using the data input stream
	 * 
	 */
	private void createOis() {
		//Defensive programming
		for (int i = 0; i<10; i++) {
			try {
				ois = new ObjectInputStream(dis);
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Object Input Stream not created");
	}

	/**Writes bytes to the output stream
	 * @param xByte - The x coordinate of the player as a byte array
	 * @param yByte - The y coordinate of the player as a byte array
	 */
	private void writeBytes(byte[] xByte, byte[] yByte){
		try {
			outputStream.write(xByte);
			outputStream.write(yByte);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**Sends a UDP data packet to the server
	 * @param packet - The packet to be sent
	 */
	private void sendPacket(DatagramPacket packet) {
		try {
			socketSend.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**Initialises the socket used to send data to the server.
	 * 
	 */
	private void initSendSocket() {
		try {
			socketSend = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	/** Waits for the level to be sent from the server. When the level has been received it is set.
	 * 
	 */
	private void waitForLevel() {
		Boolean waitingForLvl = true;
		while(waitingForLvl) {
			try {
				curLvl = (String[]) ois.readObject();
				Main.setLvl(curLvl);
				break;
			}
			catch (NullPointerException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}

	/** Initialises the socket used to read from the server.
	 * 
	 */
	public void initReadSocket(){
		try {
			readSocket = new DatagramSocket(sendPort+1);
			System.out.println("Reading from" + (sendPort +1));
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	/** Waits until both players are ready before starting the multiplayer game.
	 * 
	 */
	public void waitForPlayer2() {
		Boolean waiting = true;
		int ready = 0;
		while (waiting) {
			try {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//ready = ois.readUTF();
				ready = ois.readInt();
				System.out.println("message from server: " + ready);
				if (ready == 57) {
					waiting = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}