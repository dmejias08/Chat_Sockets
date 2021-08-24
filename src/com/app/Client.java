package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final int port = 9090;
    public static Socket server;
    public static App frame;

    public static void main(String[] args) throws IOException {
        server = new Socket("localhost",port);
        Thread client1 = new Thread(new Client1());
        Thread server1 = new Thread(new Server());

        client1.start();
        server1.start();
        frame = new App();
        frame.frame();


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

                while (App.sendRequest) {
                    command_client = price.readLine();
                    if (command_client.contains("quit")) {
                        break;
                    }
                    total.println(getResponse());
                }

                if (App.end) {
                    System.out.println("Disconnecting...");
                    price.close();
                    total.close();
                    server.close();
                }
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
//                keyboard = new BufferedReader(new InputStreamReader(System.in)); // read info terminal price

                //Response
                price = new BufferedReader(new InputStreamReader(server.getInputStream())); //  price from server

                //Request
                total = new PrintWriter(server.getOutputStream(), true); // sent the total to server

                while (App.sendRequest) {
                    String request = frame.pack;
                    if(request.equals(null)){
                        continue;
                    }else{
                        total.println(request);
                        if (request.equals("quit")) {
                            break;
                        }
                        response = price.readLine(); // request servidor
                        System.out.println("El monto :" + response);
                    }
                    }

                if (App.end) {
                    System.out.println("Disconecting");
                    server.close();
                    total.close();
                    price.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }



        }
    }


    public static double getResponse() {
        String pack = Server.command_client;
        int length = pack.length();
        int cont = 0;
        int newPrice = 0;
        int newWeight = 0;
        int newTax = 0;
        double total = 0;
        String price = "";
        String weight = "";
        String tax = "";
        for (int i= 0; i < length; i++){

            if (pack.charAt(i) == 'E') {
                price = pack.substring(1,i);
                System.out.println(price);
                cont = i;

            }else if (pack.charAt(i) == 'e') {
                weight = pack.substring(cont+1, i);
                tax = pack.substring(i+1, length);
                System.out.println(weight+"  "+tax);
            }
        }
        newPrice = Integer.parseInt(price);
        newTax = Integer.parseInt(tax);
        newWeight = Integer.parseInt(weight);

        total = (newPrice*newTax/100) + (newWeight*0.15);

        return total;
    }
}
