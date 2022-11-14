package com.mycompany.ltm.th05.th05_bt2;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ex2 {
    public static void main(String[] args) {
        String ip = "203.162.1.2";
        String api = "http://ip-api.com/json/";
        try{
            Document doc = Jsoup.connect(api+ip)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute()
                    .parse();
            JSONObject json = new JSONObject(doc.text());
            System.out.println(json.get("status"));
            System.out.println(json.get("country"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
