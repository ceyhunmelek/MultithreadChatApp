import java.net.Socket;
import java.io.*;
import java.util.regex.*;

public class UserThread extends Thread {

    private Socket socket;
    private ChatServer server;
    PrintWriter writer = null;

    public UserThread(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.server = chatServer;
    }


    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            String userName = reader.readLine();
            server.addUserName(userName);
            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);


            String clientMessage;
            do {
                clientMessage = reader.readLine();
                Pattern privateMessagePattern = Pattern.compile("^\\[(.*)\\](.+)");
                Matcher messageMatcher = privateMessagePattern.matcher(clientMessage);
                if(clientMessage.equals("[exit]")){
                    server.userLogout(this);
                    socket.close();
                } else if (messageMatcher.matches() && !messageMatcher.group(1).equals(userName)) {
                    server.privateMessage("[" + userName + "] (pm) : " + messageMatcher.group(2), messageMatcher.group(1));
                } else if (clientMessage.length() > 0) {
                    serverMessage = "[" + userName + "] : " + clientMessage;
                    server.broadcast(serverMessage, this);
                }
            } while (!clientMessage.equals("[exit]"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void sendMessage(String serverMessage) {
        writer.println(serverMessage);
    }


}
