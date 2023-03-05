package com.example.client.hendler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class SessionHandler implements Serializable {
    private static SessionHandler singleton = new SessionHandler();
    private int port = 1026;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public static SessionHandler getInstance() {
        return singleton;
    }

    public Socket getSocket() {
        return socket;
    }

    public static int roleID;
    public static int userId;

    public void connection() throws IOException {
        socket = new Socket("127.0.0.1", 1026);
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());



    }

}
