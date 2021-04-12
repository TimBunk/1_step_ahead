package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetwerkConnection {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private static volatile NetwerkConnection netwerkConnection;
    private NetwerkConnection(){}

    public static NetwerkConnection getInstance(){
    if (netwerkConnection == null){
        synchronized (NetwerkConnection.class){
            netwerkConnection = new NetwerkConnection();
        }
    }
    return netwerkConnection;
    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public String getMessage() throws IOException {
       return in.readLine();
    }
}
