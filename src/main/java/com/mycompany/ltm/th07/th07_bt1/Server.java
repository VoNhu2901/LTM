package com.mycompany.ltm.th07.th07_bt1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Server {
    public static int buffsize = 512;
    public static int port = 1234;

    public static void main(String[] args) {
        DatagramPacket dpreceive, dpsend;
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(port);
            dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
            while (true){
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
                System.out.println("Server received: " + tmp + " from "
                        + dpreceive.getAddress().getHostAddress() + " at port "
                        + socket.getLocalPort());
                if (tmp.equals("bye")) {
                    System.out.println("Server socket closed");
                    socket.close();
                    break;
                }
                //Uppercase, sent back to client
                StringBuilder str=new StringBuilder(tmp);
                tmp=str.reverse().toString();
                dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("Server sent back "+tmp+" to client");
                socket.send(dpsend);
            }
        }catch (IOException e) {e.printStackTrace();}
    }
}
