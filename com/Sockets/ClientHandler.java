package com.Sockets;

import com.Conexion.UsuarioDAO;
import com.Modelo.Usuario;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String inputLine;

            // Lee las solicitudes del cliente y responde
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                String response = handleRequest(inputLine);
                out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String handleRequest(String request) {
        // Aquí es donde se integra con los controladores que ya has creado
        if (request.startsWith("LOGIN")) {
            // Ejemplo de manejo de login
            String[] parts = request.split(" ");
            String cedula = parts[1];
            String password = parts[2];
            return login(cedula, password);
        }

        // Puedes agregar más casos para manejar otras solicitudes como CRUD, compras, etc.

        return "Unknown request";
    }

    private String login(String cedula, String password) {
        // Integra con tu controlador de Login
        Usuario usuario = new UsuarioDAO().obtenerPorCedula(cedula);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return "Login successful: " + usuario.getNombre();
        }
        return "Login failed";
    }
}
