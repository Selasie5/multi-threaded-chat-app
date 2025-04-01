import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
  public class ChatServer {
    private static final int PORT = 8080;
    private static Set<ClientHandler> clients = new HashSet<>();

  public static void main(String[] args) throws IOException {
    try(ServerSocket serverSocket = new ServerSocket(PORT)){
      System.out.println("Chat server started on PORT: " + PORT);
      while(true){
        Socket socket = serverSocket.accept();
        ClientHandler clientHandler = new ClientHandler(socket);
        clients.add(clientHandler).start();
      }
    }
  }
}
