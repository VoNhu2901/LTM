package com.mycompany.ltm.th05.th05_bt4;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTML;
import java.io.IOException;

public class ex4 {
    public static void main(String[] args) {
        String url = "https://masothue.com/Search/?q=300301302&type=auto&force-search=1";
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
            for(Element e: elementList){
                System.out.println(e.text());
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
