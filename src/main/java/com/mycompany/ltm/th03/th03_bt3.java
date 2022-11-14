package com.mycompany.ltm.th03;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class th03_bt3 {
    public static void main(String[] args) throws FileNotFoundException {
        String url = "D:\\thuchanh\\ipaddress.txt";

        File file = new File(url);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        try {
            String line = reader.readLine();
            while (line != null) {
//                System.out.println(line);
                try {
                    InetAddress add = InetAddress.getByName(line);
                    boolean reachable = add.isReachable(10000);
                    if(reachable) {
                        System.out.println("IP " + line + " is reachable ");
                    }else {
                        System.out.println("IP " + line + " is not reachable ");
                    }

                }catch (UnknownHostException e) {
                    System.out.println("IP " + line + " is not valid");
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
