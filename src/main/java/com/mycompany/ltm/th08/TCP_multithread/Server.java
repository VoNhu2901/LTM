package com.mycompany.ltm.th08.TCP_multithread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static int port = 1234;
    public static int numThreads = 2;
    private static ServerSocket server = null;

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        try {
            server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            while (true) {
                Socket socket = server.accept();
                executor.execute(new TCP_multi(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) server.close();
        }
    }
}
