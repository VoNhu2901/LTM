package com.mycompany.ltm.bt02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDP_Client {
    public static int destPort = 1234;
    public static String hostname = "localhost";

    public static void main(String[] args) {
        DatagramPacket dpsend, dpreceive;
        DatagramSocket socket;
        InetAddress add;
        Scanner stdIn;
        try {
            add = InetAddress.getByName(hostname);
            socket = new DatagramSocket();
            stdIn = new Scanner(System.in);
            while (true) {
                System.out.println("Client input: ");
                String tmp = stdIn.nextLine();
                byte[] data = tmp.getBytes();
                dpsend = new DatagramPacket(data, data.length, add, destPort);
                System.out.println("Client sent " + tmp + " to " + add.getHostAddress()
                        + " from port " + socket.getLocalPort());
                socket.send(dpsend);
                if (tmp.equals("bye")) {
                    System.out.println("Client socket closed");
                    stdIn.close();
                    socket.close();
                    break;
                }
                //Get response from server
                dpreceive = new DatagramPacket(new byte[512], 512);
                socket.receive(dpreceive);
                tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
                System.out.println("Client get: " + tmp + " from server");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
