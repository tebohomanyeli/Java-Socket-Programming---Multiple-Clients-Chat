# Server Breakdown

### 1. ServerSocket Object & Constructor:
The ServerSocket class provides a way to **listen** for incoming connections on a given port, and the Server constructor 
takes the **ServerSocket** object as a parameter to initialize it with a pre-initialized socket connection.

```java
public class Server
{
    // ---------------------------------------------------------Attributes
    private ServerSocket serverSocket; //ServerSocket object

    // ---------------------------------------------------------Constructors
    public Server(ServerSocket serverSocket) {this.serverSocket = serverSocket;}
}
```


### 2. startServer() Method:
```java
public void startServer(){
    try{
        while(!serverSocket.isClosed()){
            Socket socket = serverSocket.accept();
            System.out.println("A new client has connected");  
            ClientHandler clientHandler = new ClientHandler(socket);

            // Create a new thread to execute the clientHandler instance
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }catch (IOException error){closeServerSocket();}
}


public void closeServerSocket(){
    try{
        serverSocket.close();
    }catch (IOException closeError){closeError.printStackTrace();}
}
```
\
The **startServer()** method is responsible for starting the server and accepting client connections.

The **while** loop continuously checks if the **serverSocket** is still open and waits for incoming connections 
from clients using the **accept()** method of the **ServerSocket** class. The **accept()** method blocks until a 
client connects to the server and returns a new **Socket** object that represents the client connection.
```java
Socket socket = serverSocket.accept();
```

\
Once a client connection is established, the server prints a confirmation message to the console.
```java
System.out.println("A new client has connected");
```

\
A new instance of the **ClientHandler** class is created to handle the client connection. Each **ClientHandler** 
instance is responsible for communicating with a client and implements the **Runnable** interface. The **Runnable** 
interface defines a single method called **run()** which contains the code that will be executed when the thread 
is started.
```java
ClientHandler clientHandler = new ClientHandler(socket);
```

\
A new thread is created to execute the **ClientHandler** instance. A thread is a separate flow of control that can 
execute code concurrently with other threads. In this case, we want to execute the code in the **ClientHandler** 
instance concurrently with the code in the **startServer()** method.

To create a new thread, we first create an instance of the **Thread** class and pass in the **ClientHandler** 
instance as a parameter.

```java
Thread thread = new Thread(clientHandler);
```

\
We then call the **start()** method on the **Thread** object to start the thread. The **start()** method is 
responsible for calling the **run()** method on the **ClientHandler** instance.
```java
thread.start();
```

\
The **closeServerSocket()** method is responsible for closing the serverSocket if it is open, catching any 
**IOException** that may be thrown.

```java
public void closeServerSocket(){
    try{
        serverSocket.close();
    }catch (IOException closeError){closeError.printStackTrace(); }
}
```

\
Overall, this code sets up a loop to continuously listen for incoming client connections, and once a connection
is established, it prints a confirmation message, creates a new instance of the **ClientHandler** class to handle
the client connection, and spawns a new thread to execute the **ClientHandler** instance concurrently with the
**startServer()** method. If an **IOException** occurs, the **closeServerSocket()** method is called to close
the serverSocket.

---


### 3. Main Method [RUN]
This is the **main()** method of the program, responsible for creating an instance of the **Server** class, 
initializing it with a new ServerSocket object, and starting the server using the **startServer()** 
method of the **Server** class.

```java
public static void main(String[] args) throws IOException{
    // Create a new ServerSocket object with port number 1234
    ServerSocket serverSocket = new ServerSocket(1234);

    // Create a new instance of the Server class with the ServerSocket object as a parameter
    Server server = new Server(serverSocket);
    
    // Start the server by calling the startServer() method of the Server instance
    server.startServer();
}
```
In this code, the **ServerSocket** object is initialized with the port number 1234, which is the port on which 
the server will listen for incoming connections from clients.

A new instance of the **Server** class is created with the **ServerSocket** object as a parameter, and the 
**startServer()** method of the **Server** class is called to start the server and listen for incoming connections.

If an **IOException** occurs while creating the **ServerSocket** object or starting the server, it will be 
thrown by the method and handled by the calling method or caught by a **try-catch** block in the calling code.