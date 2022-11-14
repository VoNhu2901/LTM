package com.mycompany.ltm.th04.th04_bt3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(5000);
            System.out.println("Server start...");
            socket = server.accept();
            System.out.println("Client " + socket.getInetAddress() + " connect");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                String line = in.readLine();
                if (line.equals("bye")) break;
                System.out.println("Server received: " + line);

                //server phan hoi ve client
                int n = Integer.parseInt(line);
                List<Integer> listNumbers = analystToPrime2(n);
                int size = listNumbers.size();
                String delim = " x ";
                StringBuilder sb = new StringBuilder();

                int i=0;
                while(i<size-1){
                    sb.append(listNumbers.get(i));
                    sb.append(delim);
                    i++;
                }
                sb.append(listNumbers.get(i));

                String res = sb.toString();
                out.write(res);
                out.newLine();
                out.flush();

            }
            System.out.println("Server closed connection");
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static List<Integer> analystToPrime(int n){
        int i=2;
        List<Integer> listNumbers = new ArrayList<Integer>();
        while(n>1){
            if(n%i==0){
                n/=i;
                listNumbers.add(i);
            }else{
                i++;
            }
        }
        if(listNumbers.isEmpty()) listNumbers.add(n);
        return listNumbers;
    }

    public static List<Integer> analystToPrime2(int n){
        int i=2;
        List<Integer> listNumbers = new ArrayList<Integer>();
        while (n>1){
            if(n%i==0){
                n/=i;
                listNumbers.add(i);
            }else{
                i++;
            }
        }
        if(listNumbers.isEmpty()) listNumbers.add(n);
        return listNumbers;
    }

}
