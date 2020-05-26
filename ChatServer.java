import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatServer {
	
	private int port;
	
	private List<String> userNames = new ArrayList<String>();
	private List<UserThread> userThreads = new ArrayList<UserThread>();
	
	public ChatServer(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) {
		ChatServer cs = new ChatServer(9999);
		cs.execute();
	}

	private void execute() {
		try {
			ServerSocket serverSocket = new ServerSocket(9999);
			System.out.println("Server is running");
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected");
				UserThread newUser = new UserThread(socket, this);
				userThreads.add(newUser);
				newUser.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addUserName(String s) {
		this.userNames.add(s);
	}

	public void broadcast(String serverMessage, UserThread excludeUser) {
		for(UserThread aUser: userThreads) {
			if(aUser != excludeUser)
				aUser.sendMessage(serverMessage);
		}
	}

	public void privateMessage(String serverMessage, String userToSend) {
		if(this.userNames.contains(userToSend)){
			this.userThreads.get(this.userNames.indexOf(userToSend)).sendMessage(serverMessage);
		}
	}

	public void userLogout(UserThread thread){
		int userIndex = this.userThreads.indexOf(thread);
		this.broadcast(this.userNames.get(userIndex) + " is logged out", thread);
		this.userThreads.remove(userIndex);
		this.userNames.remove(userIndex);
	}

}
