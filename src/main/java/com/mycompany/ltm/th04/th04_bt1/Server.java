package com.mycompany.ltm.th04.th04_bt1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(5000);
            System.out.println("Server start ...");

            socket = server.accept();
            System.out.println("Client "+ socket.getInetAddress()+" connect...");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while(true){
                //server nhan data tu client qua stream
                String line = in.readLine();
                if(line.equals("bye"))break;
                System.out.println("Server received: "+line);

                //server phan hoi ve client
                StringBuilder newLine = new StringBuilder();
                newLine.append(line);
                line = newLine.reverse().toString();

                out.write(line);
                out.newLine();
                out.flush();
            }
            System.out.println("Server closed connection");
            in.close();
            out.close();
            socket.close();
            server.close();
        }catch(IOException e){
            System.err.println(e);
        }
    }
}
