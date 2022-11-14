package com.mycompany.ltm.th07;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Sample {
    private  String convertBinary(String input){
        String result="";
        String[] data= input.split(":");
        String x=data[0];
        String y=data[1];
        String number=data[2];
        String API="https://networkcalc.com/api/binary/"+number+"?from="+x+"&to="+y+"";
        try {
            Document doc = Jsoup.connect(API)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute()
                    .parse();
            JSONObject json = new JSONObject(doc.text());
            result = "Convert "+number+" from "+x+" to "+y+" = "  + json.get("converted").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private  String findHashKey(String input){
        String result="";
        String[] data= input.split(":");
        String action =data[0];
        String s=data[1];
        switch (action) {
            case "ENC":
                String APIENC="https://hashtoolkit.com/generate-hash/?text="+s+"";
//                result=this.getMD5(s);

                break;
            case "DEC":
                String APIDEC="https://hashtoolkit.com/decrypt-hash/?hash="+s+"";
                try {
                    Document doc = Jsoup.connect(APIDEC)
                            .method(Connection.Method.GET)
                            .ignoreContentType(true)
                            .execute()
                            .parse();
                    Elements e=doc.getElementsByAttributeValue("class","res-text");

                    result = e.text();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }


        return result;
    }
}
