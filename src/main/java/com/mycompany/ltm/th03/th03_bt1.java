package com.mycompany.ltm.th03;

import java.net.InetAddress;
import java.util.Scanner;

public class th03_bt1 {
    public static void main(String[] args) {
        System.out.println("hay nhap vào domain name:");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();

        while(true){
            if(name.equals("exit")){
                System.exit(0);
            }
            try {
                InetAddress address = InetAddress.getByName(name);
                System.out.println(address);
            }catch(Exception e){
                System.out.println("Can not find "+ name);
                System.out.println("hay nhap vào domain name khac:");
                name = sc.nextLine();
            }
        }
    }
}
