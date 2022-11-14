package com.mycompany.ltm.bt01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Server {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    private static HashMap<String, String> diction = new HashMap<>();

    public static void main(String[] args) {
        docfile();
        try {
            server = new ServerSocket(5000);
            System.out.println("Server started...");
            socket = server.accept();
            System.out.println("Client " + socket.getInetAddress() + " connected...");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                // Server nhận dữ liệu từ client qua stream
                String[] data = in.readLine().split(";");
//				String line = in.readLine();
                String line = data[0];

                if (line.equals("bye"))
                    break;
                System.out.println("Server received: " + line);
                switch (line.toUpperCase()) {
                    case "ADD":
                        String x = null;
                        String y = null;
                        if (data.length == 3) {
                            x = data[1];
                            y = data[2];
                            boolean checkAdd = false;
                            for (String key : diction.keySet()) {

                                if (key.toLowerCase().equals(x.toLowerCase())) {
                                    checkAdd = true;
                                    out.write("Đã tồn tại trong từ điển");
                                    out.newLine();
                                    out.flush();
                                    break;
                                }
                            }
                            // chưa tồn tại
                            if (!checkAdd) {
                                // thêm vào hashMap
                                diction.putIfAbsent(x, y);
                                // thêm vào file
                                File file = new File("dictionary.txt");
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                FileWriter fw = new FileWriter(file, true);
                                BufferedWriter bw = new BufferedWriter(fw);
                                PrintWriter pw = new PrintWriter(bw);
                                pw.write(x + ";" + y + "\n");
                                pw.flush();
                                out.write("Thêm thành công");
                                out.newLine();
                                out.flush();
                            }
                            break;
                        } else {
                            out.write("Sai cú pháp");
                            out.newLine();
                            out.flush();
                            break;
                        }

                    case "DEL":
                        String d = null;
                        if (data.length == 2) {
                            d = data[1];
                            boolean checkDEL = false;
                            // kiểm tra tư có tồn tại không
                            for (String key : diction.keySet()) {
                                if (key.equals(d)) {
                                    diction.remove(key);
                                    File inputFile = new File("dictionary.txt");
                                    File tempFile = new File("myTempFile.txt");
                                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                                    String currentLine;
                                    while ((currentLine = reader.readLine()) != null) {
                                        if (currentLine.split(";")[0].equals(d))
                                            continue;
                                        writer.write(currentLine + System.getProperty("line.separator"));
                                    }
                                    writer.close();
                                    reader.close();
                                    inputFile.delete();
                                    tempFile.renameTo(inputFile);

                                    out.write("Xóa thành công");
                                    out.newLine();
                                    out.flush();
                                    checkDEL = true;
                                    break;
                                }
                            }
                            if (!checkDEL) {
                                out.write("Không có từ cần xóa trong từ điển");
                                out.newLine();
                                out.flush();
                            }
                            break;
                        } else {
                            out.write("Sai cú pháp");
                            out.newLine();
                            out.flush();
                            break;
                        }

                    default:
                        //
                        boolean checkKeyENG = false;
                        for (String key : diction.keySet()) {
                            if (line.equalsIgnoreCase(key)) {
                                String value = diction.get(key);
                                out.write(value);
                                out.newLine();
                                out.flush();
                                checkKeyENG = true;
                                break;
                            }
                        }

                        if (!checkKeyENG) {
                            for (String key : diction.keySet()) {
                                String value = diction.get(key);
                                if (line.equalsIgnoreCase(value)) {
                                    out.write(key);
                                    out.newLine();
                                    out.flush();
                                    checkKeyENG = true;
                                    break;
                                }
                            }
                        }

                        if (!checkKeyENG) {
                            out.write("Không tìm thấy từ cần tra cứu bạn ơi ");
                            out.newLine();
                            out.flush();
                        }
                }
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

    private static String removeAccent(String s) {
        // TODO Auto-generated method stub
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    private static void docfile() {
        // TODO Auto-generated method stub
        try {
            File myObj = new File("dictionary.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(";");
                String key = data[0];
                String velue = data[1];
                diction.putIfAbsent(key, velue);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
