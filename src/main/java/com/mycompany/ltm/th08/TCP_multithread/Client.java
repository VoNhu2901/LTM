package com.mycompany.ltm.th08.TCP_multithread;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket = null;

    public static void main(String[] args) throws IOException {
        try {
            String host = "localhost";
            int port = 1234;
            socket = new Socket(host, port);
            System.out.println("Client connected");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String input;
            do {
                System.out.println("Client sent: ");
                input = stdIn.readLine();
                out.write(input + "\n");
                out.flush();
            } while (!input.equals("bye"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(socket != null){
                socket.close();
                System.out.println("Client sockeet closed");
            }
        }
    }
}
