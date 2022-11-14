package com.mycompany.ltm.th03;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class th03_bt2 {
    public static void main(String[] args) throws IOException{
        // TODO Auto-generated method stub

        String url = "C:\\Users\\vonhu\\Desktop\\Nam4\\domainname.txt";

        File file = new File(url);
        BufferedReader reader = new BufferedReader(new FileReader(file));


        try {
            String line = reader.readLine();
            while (line != null) {
//                System.out.println(line);
                try {
                    InetAddress add = InetAddress.getByName(line);
                    System.out.println("Domain name " + line + " has IP " + add.getHostAddress());
                }catch (UnknownHostException e) {
                    System.out.println("Domain name " + line + " is not valid");
                }
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
                // file.close();
            } catch (IOException ex) {
                Logger.getLogger(BufferedReader.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}
