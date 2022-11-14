package com.mycompany.ltm.bt02;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Locale;
import java.util.StringTokenizer;

public class UDP_Server {
    public static int buffsize = 512;
    public static int port = 1234;
    public static String myKey = "964ca471-0a3d-4bb4-b655-f8c3979b330c";
    public static String path = "https://api.airvisual.com/v2/";
    public static JSONArray jsonArray;


    public static String getData(String input) {

        String data = "";

        StringTokenizer st = new StringTokenizer(input, ";");
        int numberToken = st.countTokens();
        try {
            switch (numberToken) {
                case 1:  //hello or Vietnam
                    String key = st.nextToken();

                    StringBuilder sb1 = new StringBuilder();
                    if (key.toLowerCase().equals("hello")) {
                        String api1 = "";
                        api1 = path + "countries?key=" + myKey;
                        callApi(api1);
                        sb1.append("\n" + "List country: " + "\n");
                        data = showData("country");
                    } else {
                        String api1b = "";
                        api1b = path + "states?country=" + key + "&key=" + myKey;
                        callApi(api1b);
                        sb1.append("\n" + "List state: " + "\n");
                        data = sb1.toString() + showData("state");
                    }
                    break;
                case 2:  //Vietnam;Hanoi
                    String country2 = st.nextToken(); //Vietnam
                    String state2 = st.nextToken();   //Hanoi

                    StringBuilder sb2 = new StringBuilder();
                    String api2 = "";
                    api2 = path + "cities?state=" + state2 + "&country=" + country2 + "&key=" + myKey;
                    callApi(api2);
                    sb2.append("\n" + "List city: " + "\n");
                    data = sb2.toString() + showData("city");
                    break;
                case 3:  //Vietnam;Hanoi;Tay ho
                    String country3 = st.nextToken(); //Vietnam
                    String state3 = st.nextToken();   //Hanoi
                    String city3 = st.nextToken();    //Tay ho

                    StringBuilder sb3 = new StringBuilder();
                    String api3 = "";
                    api3 = path + "city?city=" + city3 + "&state=" + state3 + "&country=" + country3 + "&key=" + myKey;

                    Document doc = Jsoup.connect(api3)
                            .method(Connection.Method.GET)
                            .ignoreContentType(true)
                            .execute()
                            .parse();
                    JSONObject json = new JSONObject(doc.text());
                    JSONObject dataObject = (JSONObject) json.get("data");
                    JSONObject current = (JSONObject) dataObject.get("current");
                    JSONObject pollution = (JSONObject) current.get("pollution");
                    data = pollution.toString();

                    sb3.append("\n" + "AQI is: ");
                    sb3.append(pollution.get("aqius"));
                    data = sb3.toString();
                    break;
                default:
                    return "Incorrect syntax!!!";
            }
        } catch (IOException e) {
            switch (numberToken) {
                case 1:
                    data = "country is not found";
                    break;
                case 2:
                    data = "state is not found";
                    break;
                case 3:
                    data = "city is not found";
                    break;
                default:
                    return "Incorrect syntax!!!";
            }
        }


        return data;
    }

    public static void callApi(String apiValue) throws IOException {
        Document doc = Jsoup.connect(apiValue)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .execute()
                .parse();
        JSONObject json = new JSONObject(doc.text());
        jsonArray = (JSONArray) json.get("data");
    }

    public static String showData(String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            String country = (String) obj.get(key);
            sb.append(country);
            sb.append(" , ");
        }
        return sb.toString();

    }

    public static void main(String[] args) {
        DatagramSocket socket;
        DatagramPacket dpreceive, dpsend;
        try {
            socket = new DatagramSocket(port);
            dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
            while (true) {
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
                System.out.println("Server received: " + tmp + " from " +
                        dpreceive.getAddress().getHostAddress() + " at port " + socket.getLocalPort());
                if (tmp.equals("bye")) {
                    System.out.println("server socket closed");
                    socket.close();
                    break;
                }
                //Sent back to client
                tmp = getData(tmp);
                dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                        dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("Server send back: " + tmp + " to client");
                socket.send(dpsend);

            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
