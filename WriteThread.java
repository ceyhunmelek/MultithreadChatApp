import java.net.*;
import java.io.*;
public class WriteThread extends Thread {
	private Socket socket;
	private ChatClient client;
	private PrintWriter writer;
	public WriteThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;
		OutputStream output;
		try {
			output = socket.getOutputStream();
			this.writer = new PrintWriter(output, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		Console console = System.console();
		String userName = console.readLine("Enter your username : ");
		writer.println(userName);
		String text;
		do {
			text = console.readLine("[" + userName + "]: ");
			writer.println(text);
		}while(!text.equals("[exit]"));
		
		try {
			client.getReadThread().interrupt();
			client.getReadThread().getReader().close();
			this.writer.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
