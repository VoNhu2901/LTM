package com.mycompany.ltm.th03;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class th03_bt4 {
    public static void main(String[] args) throws IOException {
        InetAddress add = InetAddress.getLocalHost();
        StringTokenizer str = new StringTokenizer(add.getHostAddress(), ".", true);
        int count = 0;
        String net = "";
        while(count < 6) {

            String num = str.nextToken();
            net = net + num;
            count +=1;
        }
        System.out.println(net);
//        try {
//            for(int i = 1; i < 255; i++) {
//                InetAddress address = InetAddress.getByName(net + i);
//                boolean reachable = address.isReachable(10000);
//                if(reachable) {
//                    System.out.println("IP " + net + i + " is reachable ");
//                }else {
//                    System.out.println("IP " + net + i + " is not reachable ");
//                }
//            }
//        }catch (UnknownHostException e) {
//            System.out.println("Domain name is not valid");
//        }

    }
}
