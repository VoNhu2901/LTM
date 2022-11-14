package com.mycompany.ltm.th05.th05_bt4;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

                    String res = checkTaxInfo(line);

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

    public static String checkTaxInfo(String line){
        String res = "";
        String url = "https://masothue.com/Search/?q="+line+"&type=auto&force-search=1";

        try{
            Connection.Response con = Jsoup.connect(url)
                    .method(Connection.Method.GET)  //request API bang phuong thuc Get
                    .execute();  //thuc thi request

//            neu CMND ton tai Ma so thue thi newurl la link dc website redirect den; ngc lai newurl = url
            String newurl = con.url().toString();

//            lenh duoi day chi dc phep chay neu CMND co Ma so thue; ngc lai kh lay dc thong tin
            Document doc = Jsoup.connect(newurl)
                    .method(Connection.Method.GET)
                    .execute()  //thuc thi connect
                    .parse();  //chuyen Response -> Document

//            lay tat ca element trong code HTML newurl dua tren attribute & value. De lam dc thi phai phan tich trc code HTML
            Elements elementList = doc.getElementsByAttributeValue("class", "copy");
            StringBuilder sb = new StringBuilder();
            for(Element e: elementList){
                sb.append(e.text() + " - ");
            }
            res=sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
