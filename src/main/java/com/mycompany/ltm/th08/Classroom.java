package com.mycompany.ltm.th08;

//class Student extends Thread{
//    String fullName;
//    int age;
//    public Student(String fullName, int age) {
//        this.fullName = fullName;
//        this.age = age;
//    }
//
//    public void run(){
//        for(int i = 0; i <=age; i++){
//            System.out.println("My name is "+ fullName + " - i="+i+"/"+age);
//        }
//    }
//}

class Student implements Runnable {
    String fullName;
    int age;
    public Student(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }

    public void run(){
        for(int i = 0; i <=age; i++){
            System.out.println("My name is "+ fullName + " - i="+i+"/"+age);
        }
    }
}

public class Classroom{
    public static void main(String[] args) {
        Student sd1 = new Student("Nhu", 21);
        Student sd2 = new Student("Son", 20);
        Thread t1 = new Thread(sd1);
        Thread t2 = new Thread(sd2);
        t1.start();
        t2.start();
    }
}
