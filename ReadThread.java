import java.net.*;
import java.io.*;

public class ReadThread extends Thread{
	private BufferedReader reader;
	private Socket socket;
	private ChatClient client;
	public ReadThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;
		
		InputStream input;
		try {
			input = this.socket.getInputStream();
			this.reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void run() {
		while(!this.client.getReadThread().isInterrupted()) {
			try {
				String response = this.reader.readLine();
				System.out.println("\n" + response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
