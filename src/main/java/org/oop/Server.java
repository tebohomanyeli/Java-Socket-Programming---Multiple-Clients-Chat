package org.oop;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    // ---------------------------------------------------------Attributes
    private ServerSocket serverSocket; //ServerSocket object


    // ---------------------------------------------------------Constructors
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // ---------------------------------------------------------Methods
    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start(); //This methode runs the program..... bur
            }
        }catch (IOException error){closeServerSocket();}
    }

    public void closeServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }

        }catch (IOException closeError){
            closeError.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
