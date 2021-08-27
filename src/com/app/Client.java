package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final int port = 9090;
    public static final int port2 = 9999;
    public static Socket server;
    public static Socket server2;
    public static App1 frame1;

    public static void main(String[] args) throws IOException {
        frame1 = new App1("Cliente 1");
//        frame1.frame("Cliente 1");

        server = new Socket("localhost",port);
        server2 = new Socket("localhost",port2);

        Thread client1 = new Thread(new Client1());
        Thread server1 = new Thread(new Server2());

        client1.start();
        server1.start();



    }

    private static class Server2 implements Runnable{

        private static BufferedReader price;
        private static PrintWriter total;
        private static String command_client;


        @Override
        public void run() {

            try {
                // Read from client
                price = new BufferedReader(new InputStreamReader(server2.getInputStream())); //  price from client

                //Send response to  client
                total = new PrintWriter(server2.getOutputStream(), true); // sent the total to server

                while (true) {
                    if (frame1.sendRequest) {
                        command_client = price.readLine();
                        if (command_client.contains("quit")) {
                            break;
                        }
                        total.println(getResponse());
                    }


                    if (frame1.end) {
                        System.out.println("Disconnecting...");
                        price.close();
                        total.close();
                        server.close();
                        break;
                    }
//                    System.out.println("Estoy en ciclo ");
//                    break;
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

                while (true) {
                    if (frame1.sendRequest) {
                        System.out.println("ESTOY EN EL CICLO DE CIELNTE EN CLIENTE ");
                        String request = frame1.pack;
                        System.out.println("el pedido es:"+request);
                        if (request.equals(null) == false) {
                            total.println(request);
                            response = price.readLine(); // request servidor
                            System.out.println("El monto :" + response);
                            frame1.response.setText(response);
                        } else {
                            continue;
                        }

                    }


                if (frame1.end) {
                    System.out.println("Disconecting");
                    server.close();
                    total.close();
                    price.close();
                    break;
                }
            }

            }
            catch (IOException e){
                e.printStackTrace();
            }



        }
    }




    static double getResponse() {
        String pack = Server2.command_client;

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
            }else{
                price = "0";
                tax = "0";
                weight = "0";
            }
        }
        newPrice = Integer.parseInt(price);
        newTax = Integer.parseInt(tax);
        newWeight = Integer.parseInt(weight);

        total = (newPrice*newTax/100) + (newWeight*0.15);

        return total;
    }
}
