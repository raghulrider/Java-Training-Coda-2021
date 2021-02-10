package day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientClass {
	public static void main(String[] args) {
		Client.connect("localhost", 3333);
	}
}

class Client {

	private Client() {
	}

	private static Socket socket=null;
	private static BufferedReader keyIn;
	private static PrintWriter out;
	private static ExecutorService messageReceiver;

	public static void connect(String Domain, int PORT) {

		try {
			socket = Socket.class.getDeclaredConstructor(String.class, Integer.class)
					.newInstance(Domain, PORT);
			//socket = new Socket(Domain, PORT);
			System.out.println(">>");
			
			
			// Sub-thread to receive incoming messages
			IncMsgHandler handler = IncMsgHandler.class.getDeclaredConstructor(Socket.class).newInstance(socket);
			//IncMsgHandler handler = new IncMsgHandler(socket);
			
			messageReceiver = Executors.newSingleThreadExecutor();
			messageReceiver.execute(handler);

			// Sending message to server/other clients in main thread
			keyIn = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(socket.getOutputStream(), true);
			while (true && !socket.isClosed()) {
				String input = keyIn.readLine();
				if (input.equals("quit")) {
					break;
				} else {
					out.println(input);
					Thread.sleep(100);
					System.out.println(">>");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			try {
				System.out.println("Socket closed.");
				socket.close();
			} catch (IOException e) {
				System.out.println("Problem closing socket");
				e.printStackTrace();
			}
			messageReceiver.shutdown();
		}
	}
}

class IncMsgHandler implements Runnable {
	private Socket serverSocket;
	private BufferedReader in;
	public IncMsgHandler(Socket serverSocket) throws Exception {
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		try {
			while (true && !serverSocket.isClosed()) {
				in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				String serverMsg = in.readLine();
				if (serverMsg == null) {
					break;
				}
				System.out.println("Server : " + serverMsg);
			}
		} catch (Exception e) {
			if(e.getMessage().equals("Socket closed")) {
				System.out.println("Sub-Thread  : "+e.getMessage());
			}else {
				e.printStackTrace();
			}
		} finally {
			try {
				in.close();
				System.out.println("Server problem, disconnecting socket");
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}