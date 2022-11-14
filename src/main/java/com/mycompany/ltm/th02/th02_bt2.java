package com.mycompany.ltm.th02;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class th02_bt2 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        System.out.println("hay nhap vào chuoi ky tu:");
        Scanner sc = new Scanner(System.in);
        String n = sc.nextLine();

        StringTokenizer st = new StringTokenizer(n, " ");
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        String word = "";
        while(st.hasMoreTokens()) {
            word = st.nextToken();
            System.out.println(word);
            if(linkedHashMap.get(word.toLowerCase())== null) {
                linkedHashMap.put(word.toLowerCase(), word);
            }
        }
        for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
            System.out.print(entry.getValue() + " ");
        }
    }
}
