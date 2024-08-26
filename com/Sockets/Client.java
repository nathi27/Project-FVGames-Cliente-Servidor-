package com.Sockets;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendRequest(String request) {
        out.println(request);
    }

    public String getResponse() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        socket.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 12345);
            client.sendRequest("LOGIN 12345678 password");
            String response = client.getResponse();
            System.out.println("Server response: " + response);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
