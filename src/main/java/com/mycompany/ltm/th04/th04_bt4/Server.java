package com.mycompany.ltm.th04.th04_bt4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(5000);
            System.out.println("Server started...");
            socket = server.accept();
            System.out.println("Client " + socket.getInetAddress() + " connected...");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Random generater = new Random();
            int value = generater.nextInt(101);
            int dem = 0;

            while (true) {
                // Server nhận dữ liệu từ client qua stream
                String line = in.readLine();
                if (line.equals("bye")) {
                    break;
                }
                System.out.println("Server received: " + line);

                int num = Integer.parseInt(line);
                if (num < value) {
                    out.write(line.toString() + " nhỏ hơn số đang có");
                    dem++;
                } else if (num > value) {
                    out.write(line.toString() + " lớn hơn số đang có");
                    dem++;
                } else if (num == value) {
                    out.write(line.toString() + " Đã tìm thấy rồi");
                    String line2 = String.valueOf(dem);
                    out.write(" Client đã đoán " + dem + " lần");
                    out.newLine();
                    out.flush();
                    break;
                }
                out.newLine();
                out.flush();
            }
            System.out.println("Server closed connection");
            // Đóng kết nối
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
