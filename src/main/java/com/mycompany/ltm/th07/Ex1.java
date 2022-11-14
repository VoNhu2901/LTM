package com.mycompany.ltm.th07;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ex1 {


    public static void main(String[] args) {
        String hostname = "djxmmx.net";
        int port = 17;
        try {
            InetAddress add = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
            //send UDP packet to djxmmx.net
            DatagramPacket dpsend = new DatagramPacket(new byte[1], 1, add, port);
            socket.send(dpsend);
            //receive UDP packet
            byte [] buffer = new byte[512];
            DatagramPacket dpreceive = new DatagramPacket(buffer,buffer.length);
            socket.receive(dpreceive);
            String data = new String(buffer, 0, dpreceive.getLength());
            System.out.println(data);
            socket.close();

        }catch (IOException e){e.printStackTrace();}
    }



}
