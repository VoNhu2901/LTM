package com.mycompany.ltm.th04.th04_bt1;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;
    private static BufferedReader stdIn = null;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            while(true){
                //client nhan data tu keyboard va gui vao stream -> server
                System.out.println("Client input: ");
                String line = stdIn.readLine();

                out.write(line);
                out.newLine();
                out.flush();
                if(line.equals("bye"))
                    break;

                //client nhan phan hoi tu server
                line = in.readLine();
                System.out.println("Client received: " + line);
            }
            System.out.println("Client close connection");
            in.close();
            out.close();
            stdIn.close();
            socket.close();

        }catch (IOException e) {
            System.err.println(e);
        }
    }
}
