package main;


import controller.Controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static int clientsCounter = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {

            serverSocket = new ServerSocket(1026);
            while (true) {
                Socket client = serverSocket.accept();
                clientsCounter++;
                Runnable runnable = new Controller(clientsCounter, client);
                Thread thread = new Thread(runnable);
                thread.start();


            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
            serverSocket.close();
            System.out.println("Client " + clientsCounter + " disconnected");
        }
    }

}