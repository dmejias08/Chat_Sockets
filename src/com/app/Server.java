package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static ServerSocket connector;
    private static int port = 9090;
    public static Socket socket;
    public static void main(String[] args) throws IOException {

        connector = new ServerSocket(port);
        System.out.println("Server is waiting for connection...");
        socket = connector.accept();
        System.out.println("Cliente conectado");

        Thread server = new Thread(new Server1());
        Thread client = new Thread(new Client());

        client.start();
        server.start();
        App frame = new App();
        frame.frame();


    }



    private static class Client implements Runnable  {

        private  static BufferedReader keyboard;
        private  static BufferedReader in;
        private static PrintWriter out;



        @Override
        public void run() {
            try{
                //read from keyboard
                keyboard = new BufferedReader(new InputStreamReader(System.in));

                // Send request to server

                out = new PrintWriter(socket.getOutputStream(),true);

                // Get response
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {
                    String request = keyboard.readLine();
                    out.println(request);
                    if (request.contains("quit")) {
                        break;
                    }
                    String response = in.readLine();
                    System.out.println("El monto :"+ response);

                }

                System.out.println("Disconnecting...");
                out.close();
                in.close();
                connector.close();
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }




        }
    }

    private static class Server1 implements Runnable{
        private static BufferedReader price;
        private static PrintWriter total;
        private static String command_client;

        @Override
        public void run() {

            try {
                // Read from client
                price = new BufferedReader(new InputStreamReader(socket.getInputStream())); //  price from client

                //Send response to  client
                total = new PrintWriter(socket.getOutputStream(), true); // sent the total to server

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
                connector.close();
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }




        }
    }
    public static int getPrice() {
        int newPrice;
        newPrice = Integer.parseInt(String.valueOf(Server1.command_client));
        int total = (int) (newPrice + newPrice * (0.3));
        return total;
    }


}
