import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {

	public static void main(String[] args) {
		String serverName = "localhost";
		int port = 6000;
		try {
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);
			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			
			Scanner userInput = new Scanner(System.in);
			System.out.println("Ask the server: \"What time is it?\" or type \"Goodbye\" to exit.");
			String line = "";
			
			while(!line.toLowerCase().contentEquals("goodbye")) {
				line = userInput.nextLine();
				out.writeUTF(line);
				System.out.println("Server says: " + in.readUTF());
			}
			client.close();
			userInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
