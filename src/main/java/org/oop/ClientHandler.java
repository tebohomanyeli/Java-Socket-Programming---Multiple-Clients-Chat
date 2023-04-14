package org.oop;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    // ---------------------------------------------------------Global Class List
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    // ---------------------------------------------------------Attributes
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    // ---------------------------------------------------------Constructor
    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this); // this refers to the ClientHandler object
            broadcastMessage("SERVER: " +clientUsername+ " has entered the chat!");
        }catch(IOException error){closeEverything(socket, bufferedReader, bufferedWriter);}
    }//-----end.

    @Override
    public void run(){ //Every run on this method is ran on a separate thread.
        String messageFromClient;

        while (socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            }catch (IOException error) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }//-----end.


    public void broadcastMessage(String messageToSend){
        for (ClientHandler client : clientHandlers){
            try {
                if (!client.clientUsername.equals(clientUsername)){
                    client.bufferedWriter.write(messageToSend);
                    client.bufferedWriter.newLine(); // equivalent to pressing enter
                    client.bufferedWriter.flush(); // manually closing the bufferWriter. The messages we write are not big enough to fill the buffer, so we have to close or flush it manually
                }
            }catch (IOException error){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }//-----end.


    public void removeClientHandler(){ //A method to indicate that the user has left the chat.... so we remove the ClientHandler
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " +clientUsername+ " has left the chat!");
    }//-----end.


    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();

        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }//-----end.

}
