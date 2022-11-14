package com.mycompany.ltm.th08.TCP_multithread;

import java.io.*;
import java.net.Socket;

public class TCP_multi implements Runnable {
    private Socket socket;

    public TCP_multi(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Client "+socket.toString()+" accepted");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String input = "";
            while(true){
                input = in.readLine();
                System.out.println("Server received: "+input+" from "+socket.toString());
                if(input.equals("bye")) break;
            }
            System.out.println("Closed socket for client "+ socket.toString());
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
