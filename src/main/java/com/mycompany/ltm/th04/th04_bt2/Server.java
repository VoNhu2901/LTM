package com.mycompany.ltm.th04.th04_bt2;

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
            System.out.println("Server start...");
            socket = server.accept();
            System.out.println("Client " + socket.getInetAddress() + " connect...");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                //server nhan data tu client qua stream
                String line = in.readLine();
                if (line.equals("bye")) break;
                System.out.println("Server received: " + line);

                //server phan hoi ve cho client qua stream
                int num = Integer.parseInt(line);
                int num2 = 0, num3 = 0;
                if (isPerfect(num)) {
                    out.write(line);
                    out.newLine();
                    out.flush();
                } else {
                    int flag = 0;
                    int flag1 = 0;
                    int temp = num;
                    do {
                        --temp;
                        if (isPerfect(temp) == true) {
                            flag = 1;
                            num2 = temp;
                        }
                    } while (temp > 5 && flag == 0);

                    int temp1 = num;

                    do {
                        ++temp1;
                        if (isPerfect(temp1) == true) {
                            flag1 = 1;
                            num3 = temp1;
                        }
                    } while (flag1 == 0 && temp1 < 10000);

                    int kq1 = num - num2;
                    int kq2 = num3 - num;
                }

                int kq1 = num - num2;
                int kq2 = num3 - num;

                if (kq1 < kq2 && kq1 != 0) {
                    String line1 = String.valueOf(num2);
                    out.write(line1 + " là số hoàn hảo gần nhất");
                } else if (kq1 > kq2 && kq2 != 0) {
                    String line2 = String.valueOf(num3);
                    out.write(line2 + " là số hoàn hảo gần nhất");
                } else if (kq1 > kq2 && kq2 == 0) {
                    String line1 = String.valueOf(num2);
                    out.write(line1 + " là số hoàn hảo gần nhất");
                }
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

    public static boolean isPerfect(int n) {
        int sum = 0;
        for (int i = 1; i < n; i++) {
            if (n % i == 0) sum += i;
        }
        if (sum == n) return true;
        return false;
    }
}
