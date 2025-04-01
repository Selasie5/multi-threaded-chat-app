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
    catch(IOException e){
      e.printStackTrace();
    }
  }
  static void broadcast(String message, ClientHandler sender){
  for(ClientHandler client: clients){
    if(client != sender){
      client.sendMessage(message);
    }
  }
  }
  static void removeClient(ClientHandler clientHandler){
    clients.remove(clientHandler);
  }
  }
  static class ClientHandler implements Runnable{
    private Socket socket;
    public PrintWriter out;
    public BufferedReader in;
    public String username;

    public ClientHandler(Socket socket){
      this.socket = socket;
    }
    public void run  (){
      try{
        in  = new BufferedReader((new InputStreamReader(socket.getInputStream())));
        out  = new PrintWriter(socket.getOutputStream(), true);

        out.println("Enter your name: ");
        username = in.readLine();
       ChatServer.broadcast(username + " has joined the chat application", this);

       String message;
       while((message = in.readLine())!= null){
         System.out.println(username + " : " + message);
         ChatServer.broadcast(username + " : " + message, this);
       }
      } catch (IOException e) {
        System.out.println(username + "disconnected");
        throw new RuntimeException(e);
      }
      finally {
        try {
          socket.close();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        ChatServer.removeClient(this);
        }
      }
      void sendMessage(String message){
      out.println(message);
    }
  }
}
