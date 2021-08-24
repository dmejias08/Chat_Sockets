package com.app;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final int port = 9090;
    public static Socket server;

    public static void main(String[] args) throws IOException {
        server = new Socket("localhost",port);
        Thread client1 = new Thread(new Client1());
        Thread server1 = new Thread(new Server());

        client1.start();
        server1.start();
        App frame = new App();
        frame.frame();


    }
    public static int getPrice() {
        int newPrice;
        newPrice = Integer.parseInt(String.valueOf(Server.command_client));
        int total = (int) (newPrice + newPrice * (0.3));
        return total;
    }


    private static class Server implements Runnable{

        private static BufferedReader price;
        private static PrintWriter total;
        private static String command_client;


        @Override
        public void run() {

            try {
                // Read from client
                price = new BufferedReader(new InputStreamReader(server.getInputStream())); //  price from client

                //Send response to  client
                total = new PrintWriter(server.getOutputStream(), true); // sent the total to server

                while (true) {
                    command_client = price.readLine();
                    if (command_client.contains("quit")) {
                        break;
                    }
                    total.println(getPrice());
                }

                System.out.println("Disconnecting...");
                price.close();
                total.close();
                server.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    private static class Client1 implements Runnable{
        private static BufferedReader price;
        private static PrintWriter total;
        private static BufferedReader keyboard;
        private static String response;

        @Override
        public void run() {
            try {
                //Read from keyboard
                keyboard = new BufferedReader(new InputStreamReader(System.in)); // read info terminal price

                //Response
                price = new BufferedReader(new InputStreamReader(server.getInputStream())); //  price from server

                //Request
                total = new PrintWriter(server.getOutputStream(), true); // sent the total to server

                while (true) {
                    String request = keyboard.readLine();
                    total.println(request);
                    if (request.equals("quit")) {
                        break;
                    }
                    response = price.readLine(); // request servidor
                    System.out.println("El monto :" + response);
                }
                System.out.println("Disconecting");
                server.close();
                total.close();
                price.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }



        }
    }
}
