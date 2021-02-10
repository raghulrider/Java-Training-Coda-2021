package day12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

public class ServerClass {
	public static void main(String[] args) {

		try {
			Server.createServer(3333, 3).listen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class Server {

	private static ServerSocket serverSocket;
	private static ArrayList<ClientHandler> clientList = new ArrayList<>();
	private static ExecutorService clientThreadHandler;
	private static ExecutorService indiviMsgHnadler;
	private int maxClientCount;
	private int PORT;
	private static Server server = null;

	private Server(int PORT, int maxClientCount) {
		this.PORT = PORT;
		this.maxClientCount = maxClientCount;
		clientThreadHandler = Executors.newFixedThreadPool(maxClientCount);
	}

	public static Server createServer(int PORT, int maxClientCount) throws Exception {
		if (server == null) {
			server = new Server(PORT, maxClientCount);
		}
		return server;
	}

	public void listen() {

		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("[SERVER] listening at port " + PORT);
			indiviMsgHnadler = Executors.newSingleThreadExecutor();
			indiviMsgHnadler.execute(new IndividualMessageSender(clientList));

			while (true) {
				Socket clientSocket = serverSocket.accept();
				ClientHandler clientHandler = new ClientHandler(clientSocket, clientList);
				clientList.add(clientHandler);
				clientThreadHandler.execute(clientHandler);
				System.out.println("[CLIENT] New client connected : " + clientSocket.getPort());
				if (clientList.size() > maxClientCount) {
					clientHandler.out.println("Server is busy now, will respond to you shortly!");
				}
				clientList.forEach(client -> {
					if (!client.getSocket().isConnected()) {
						clientList.remove(client);
						System.out.printf("Removed %s !\n", client.name);
					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			indiviMsgHnadler.shutdown();
			clientThreadHandler.shutdown();
		}
	}

}

class IndividualMessageSender implements Runnable {
	private ArrayList<ClientHandler> clients;
	private BufferedReader cmdIn;

	public IndividualMessageSender(ArrayList<ClientHandler> clients) {
		this.clients = clients;
		this.cmdIn = new BufferedReader(new InputStreamReader(System.in));
	}

	private static BiConsumer<String, ArrayList<ClientHandler>> clientMessageSender = ClientService::sendMessage;

	@Override
	public void run() {
		while (true) {
			try {
				while (true) {
					System.out.println("Enter command : ");
					String cmd = cmdIn.readLine();
					if (cmd.startsWith("send")) {
						clientMessageSender.accept(cmd, clients);
					}
					;
				}
			} catch (Exception e) {

			} finally {
				try {
					cmdIn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}

class ClientHandler implements Runnable {

	protected String name;
	private Socket socket; 
	private BufferedReader in;
	protected PrintWriter out;
	private ArrayList<ClientHandler> clients;

	private static Broadcaster messageBroadcaster = (message, my, clients) -> {
		int count = 0;
		ClientHandler me = null;
		for (ClientHandler client : clients) {
			if (client.name != my.name) {
				client.out.printf("((BROADCAST - %s)) -> %s\n", my.name, message);
				count++;
			} else {
				me = client;
			}

		}
		me.out.printf("Broadcast to %d clients out of %d clients success\n", count, (clients.size() - 1));

	};

	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws Exception {
		this.clients = clients;
		this.name = "client" + clientSocket.getPort();
		this.socket = clientSocket;
		this.out = new PrintWriter(clientSocket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	protected final Socket getSocket() {
		return this.socket;
	}

	@Override
	public void run() {
		try {
			while (true) {
				String message = in.readLine();
				if (message == null) {
					break;
				}
				if (message.equalsIgnoreCase("hi")) {
					out.printf("Hey %s !\n", name);
				} else if (message.startsWith("Broadcast")) {
					StringTokenizer st = new StringTokenizer(message, ":");
					st.nextToken();
					messageBroadcaster.broadcast(st.nextToken(), this, clients);
				} else {
					out.println("Sorry, I can't understand you.");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

@FunctionalInterface
interface Broadcaster {
	public void broadcast(String cmd, ClientHandler client, ArrayList<ClientHandler> clients);
}

@FunctionalInterface
interface ClientSender {
	public void sendMessage();
}

class ClientService {
	public static void sendMessage(String cmd, ArrayList<ClientHandler> clients) {
		String[] cmds = cmd.split(":");
		for (ClientHandler client : clients) {
			if (client.name.equals(cmds[1])) {
				client.out.println(cmds[2]);
			}
		}
	};
}
