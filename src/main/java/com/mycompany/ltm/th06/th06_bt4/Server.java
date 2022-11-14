package com.mycompany.ltm.th06.th06_bt4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {
    private static Socket socket = null;
    private static ServerSocket server = null;
    private static BufferedWriter out = null;
    private static BufferedReader in = null;
    private static final String errorFormat = "Vui lòng nhập đúng định dạng";
    private static boolean isValid = true;

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

                    String res = xuLyPhepToan(line);

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

    private static String xuLyPhepToan(String line) {

        // Kiểm tra dựa trên + - * /, xem chuỗi này hợp lệ ko
        if (!isStringValid(new StringTokenizer(line, "+-*/", true))) return errorFormat;

        // Sau đó tách dựa trên + -
        // ví dụ : 123+45-6*3/4-3+2*5 --> 123, + , 45 , - , 6*3/4 , - , 3 , + , 2*5
        // --> nhân vs chia sẽ thành 1 nhóm
        StringTokenizer s = new StringTokenizer(line, "+-", true);
        Vector<String> congTru = new Vector<>();
        boolean isNextNum = true;
        while (s.hasMoreTokens()) {
            String check = s.nextToken();
            // check từng nhóm và tách tiếp dựa trên * /, nếu mà >2 nghĩa là 1 chuỗi phép tính * /, ngược lại chỉ là 123 hoặc +....
            StringTokenizer tmp = new StringTokenizer(check, "*/", true);
            if (tmp.countTokens() == 1) {

                String numOrOperator = tmp.nextToken();
//                System.out.println(numOrOperator + " " + isNextNum);
                if (isNumber(numOrOperator) && isNextNum) {
                    congTru.add(numOrOperator);
                    isNextNum = false;
                } else if (isOperator(numOrOperator) && !isNextNum) {
                    congTru.add(numOrOperator);
                    isNextNum = true;
                } else return errorFormat;

            } else {
                // thực hiện phép tính * / chia
                double sum = tinhTong(tmp);
                if (isValid) congTru.add(String.valueOf(sum));
                else return errorFormat;
                isNextNum = false;
            }
        }

        String congTruChuoi = "";
        for (int i = 0; i < congTru.size(); i++) {
            congTruChuoi += congTru.get(i);
        }
        System.out.println(congTruChuoi);

        double res = tinhTong(new StringTokenizer(congTruChuoi, "+-", true));
        if (isValid) return "Kết quả: " + String.valueOf(res);
        else return errorFormat;
    }

    private static boolean isStringValid(StringTokenizer s) {
        // false: ko hợp lệ, true : hợp lệ
        if (s.countTokens() < 3 || s.countTokens() % 2 == 0) return false;
        return true;
    }

    private static boolean isNumber(String n) {
        try {
            Double.parseDouble(n);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String n) {
        // false: không phải là dấu , true là dấu
        if (!n.equals("+") && !n.equals("-") && !n.equals("*") && !n.equals("/")) return false;
        return true;
    }

    private static double tinhTong(StringTokenizer s) {
        // Tính liên tiếp 1 chuỗi số : 123+45+7+8-2 hoặc 1*44/8*5
        double sum = 0;
        String n1 = s.nextToken();
        if (isNumber(n1)) sum += Double.parseDouble(n1);
        else {
            isValid = false;
            return sum;
        }

        while (s.hasMoreTokens()) {

            String operation = s.nextToken();
            String n2 = s.nextToken();

            if (!operation.equals("+") && !operation.equals("-") && !operation.equals("*") && !operation.equals("/")) {
                isValid = false;
                return sum;
            }
            if (!isNumber(n2)) {
                isValid = false;
                return sum;
            }

            switch (operation) {
                case "+": {
                    sum = sum + Double.parseDouble(n2);
                }
                ;
                break;

                case "-": {
                    sum = sum - Double.parseDouble(n2);
                }
                ;
                break;

                case "*": {
                    sum = sum * Double.parseDouble(n2);
                }
                ;
                break;

                case "/": {
                    sum = sum / Double.parseDouble(n2) / 1.0;
                }
                ;
                break;

            }
        }

        return sum;

    }
}
