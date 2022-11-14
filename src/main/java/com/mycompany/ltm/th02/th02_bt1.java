package com.mycompany.ltm.th02;

import java.util.Scanner;
import java.util.StringTokenizer;

public class th02_bt1 {
    public static void main(String[] args) {
        System.out.println("hay nhap vao chuoi tinh: ");
        Scanner scanner = new Scanner(System.in);
        String n= scanner.nextLine();

        StringTokenizer st = new StringTokenizer(n, "+-*/", true);
        float result = 0;
        float a = Float.parseFloat(st.nextToken());
        String operator = st.nextToken();
        float b = Float.parseFloat(st.nextToken());
        switch (operator) {
            case "+": result = a+b;break;
            case "-": result = a-b;break;
            case "*": result = a*b;break;
            case "/": result = a/b;break;
            default: break;
        }
        System.out.println(result);
    }
}
