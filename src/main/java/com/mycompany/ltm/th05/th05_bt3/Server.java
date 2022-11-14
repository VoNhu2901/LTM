package com.mycompany.ltm.th05.th05_bt3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket socket = null;
    private static ServerSocket server = null;
    private static BufferedWriter out = null;
    private static BufferedReader in = null;


    public static void main(String[] args) {
        try {
            server = new ServerSocket(5000);
            System.out.println("Server đã kết nối");
            System.out.println("Đang đợi client.....");
            socket = server.accept();
            System.out.println("Client accepted!");
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                try {
                    String line = in.readLine();
                    System.out.println("Server received : " + line);

                    if (line.equals("bye")) break;

                    String res = tinhSoPi(line);

                    out.write(res);
                    out.newLine();
                    out.flush();

                    System.out.println("Gửi cho client thành công!");


                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
            System.out.println("Closing connection");
            // Đóng kết nối
            in.close();
            out.close();
            socket.close();
            server.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private static String tinhSoPi(String line) {
        // Set bán kính đồng thời là cạnh của hình vuông
        long n = Long.parseLong(line);
        int r = 3979;
        long count = 0;

        for (long i = 0; i < n; i++) {
            // random tọa độ x,y tương ứng vs 1 điểm
            double x = Math.random() * r; //tu 0 -> r
            double y = Math.random() * r;

            // Tính khoảng cách từ tâm trục Oxy là (0;0) cho tới điểm vừa random, xem điểm đó nằm trong hình tròn ko
            // d = căn((x2-x1)^2 + (y2-y1)^2)
            double d = Math.sqrt(x*x+y*y);

            if(d<=r) count++;
        }
        double res=count*4.0/n;
        System.out.println(res);

        return String.valueOf(res);
    }
}
