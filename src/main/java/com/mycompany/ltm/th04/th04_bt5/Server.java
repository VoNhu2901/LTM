package com.mycompany.ltm.th04.th04_bt5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    public static void main(String[] args) throws IOException {
        server = new ServerSocket(5000);
        System.out.println("Server start...");
        socket = server.accept();
        System.out.println("Client " + socket.getInetAddress() + " connected...");
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        while (true) {
            String line = in.readLine();
            if (line.equals("bye")) break;
            System.out.println("Server received: " + line);

            out.write(line);
            out.newLine();
            out.close();
        }
        System.out.println("Server closed connection");
        // Đóng kết nối
        in.close();
        out.close();
        socket.close();
        server.close();
    }
}
