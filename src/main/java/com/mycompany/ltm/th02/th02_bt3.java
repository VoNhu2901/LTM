package com.mycompany.ltm.th02;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class th02_bt3 {
    public static void main(String[] args) throws FileNotFoundException {
        String url = "C:\\Users\\vonhu\\Desktop\\Nam4\\dictionary.txt";

        File file = new File(url);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashMap<String,String> dic = new HashMap<>();

        try {
            String line = reader.readLine();
            while (line != null) {
                StringTokenizer word  = new StringTokenizer(line, " ");
                dic.put(word.nextToken(), word.nextToken());
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BufferedReader.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BufferedReader.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(BufferedReader.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("hay nhap vao tu can dich: ");
        Scanner sc = new Scanner(System.in);
        String n = sc.nextLine();

        StringTokenizer searchword = new StringTokenizer(n, " ");
        LinkedHashMap<String, String> translated = new LinkedHashMap<>();
        LinkedHashMap<String, String> translated1 = new LinkedHashMap<>();

        while (searchword.hasMoreTokens()){
            String search = searchword.nextToken();

            for(Map.Entry<String, String> entry: dic.entrySet()){
                if(entry.getKey().equals(search)){
                    translated.put(search, entry.getValue());
                }else{
                    translated.put(search, "_");
                }

                if(entry.getValue().equals(search))
                    translated1.put(entry.getValue(), search);
                else
                    translated1.put("_", search);
            }
        }

        System.out.println("hay nhap vao tu ngon ngu muon chuyen: ");
        String language = sc.nextLine();
        switch (language) {
            case "en":
                for(Map.Entry<String, String> entry : translated1.entrySet()){
                    System.out.println(entry.getKey()+ " ");
                }
                System.out.println("end line.");
                for(Map.Entry<String, String> entry : translated1.entrySet()){
                    System.out.println(entry.getValue() + " ");
                }
                break;
            default:
                for(Map.Entry<String, String> entry: translated.entrySet()){
                    System.out.println(entry.getKey()+" ");
                }
                System.out.println("end line.");
                for(Map.Entry<String, String> entry : translated.entrySet()){
                    System.out.println(entry.getValue() + " ");
                }
                 break;
        }
    }
}
