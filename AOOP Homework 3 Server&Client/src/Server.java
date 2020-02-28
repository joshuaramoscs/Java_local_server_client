import java.net.*;
import java.io.*;

public class Server extends Thread {
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(20000);
	}

	public void run() {
		while (true) {
			try {
				// Connect to server.
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Just connected to client " + server.getRemoteSocketAddress());

				// Create server inputs and outputs.
				DataInputStream in = new DataInputStream(server.getInputStream());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				
				
				boolean goodbye = false;
				String input = "";
				
				while(!goodbye) {
					input = in.readUTF();
					System.out.println("Client said: " + input);
					if (input.toLowerCase().contentEquals("what time is it?")) {
						out.writeUTF("The time is: " + java.time.LocalTime.now());
					} else if (input.toLowerCase().contentEquals("goodbye")) {
						goodbye = true;
						out.writeUTF(
								"Thank you for connecting to server " + server.getLocalSocketAddress() + " Goodbye!");
					} else {
						out.writeUTF("Please ask \"What time is it?\" or type \"Goodbye\"");
					}
					
				}
				server.close();

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				System.out.println("Server closing down. :)");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		int port = 6000;
		try {
			Thread t = new Server(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}