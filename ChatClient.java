import java.io.IOException;
import java.net.Socket;

public class ChatClient {
	private ReadThread readThread;
	private WriteThread writeThread;

	public static void main(String[] args) {
		new ChatClient().execute();
	}

	public ReadThread getReadThread(){return this.readThread;}
	public WriteThread getWriteThread(){return this.writeThread;}

	private void execute() {
		try {
			Socket socket = new Socket("localhost", 9999);
			System.out.println("Connected to chat server");
			this.readThread = new ReadThread(socket, this);
			this.writeThread = new WriteThread(socket, this);
			this.readThread.start();
			this.writeThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
