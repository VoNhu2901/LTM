package com.mycompany.ltm.th05.th05_bt2;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

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

                    String res = xuLyLenh(line);

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

    private static String xuLyLenh(String line){
        String res="";
        if(line.equals("hello")){
            try {
                URL whatismyip = new URL("http://checkip.amazonaws.com");
                BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
                String ipPublic = in.readLine();

                // get tất cả ip của máy server
                InetAddress[] ip = InetAddress.getAllByName(InetAddress.getLocalHost().getCanonicalHostName());
                String ipPrivate = ip[(ip.length-1)/2].getHostAddress(); // ip của máy hiện tại sẽ nằm ở giữa

                res+="IP Public: " + ipPublic + ". IP Private: " + ipPrivate;

                return res;

            } catch (MalformedURLException ex) {
                System.out.println("Lỗi không thể connect: " + ex);
            } catch (IOException ex) {
                System.out.println("Lỗi không thể đọc : " + ex);
            }

        }

        StringTokenizer s = new StringTokenizer(line, " ", false);

        if(s.countTokens()==2 && "req".equals(s.nextToken())){
            String api = "http://ip-api.com/json/";
            String ip=s.nextToken();
            try{
                Document doc = Jsoup.connect(api+ip)
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .execute()
                        .parse();
                JSONObject json = new JSONObject(doc.text());

                StringBuilder sb = new StringBuilder();
                sb.append(json.get("city"));
                sb.append(" - ");
                sb.append(json.get("country"));
                sb.append(" - ");
                sb.append(json.get("timezone"));
                res = sb.toString();
                return res;

            }catch(IOException e){
                e.printStackTrace();
            }
        }

        return "Nhập sai cú pháp ( req [ip] hoặc hello )!";
    }


}
