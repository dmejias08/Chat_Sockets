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
    private static App frame;
    public static void main(String[] args) throws IOException {

        connector = new ServerSocket(port);
        System.out.println("Server is waiting for connection...");
        socket = connector.accept();
        System.out.println("Cliente conectado");

        Thread server = new Thread(new Server1());
        Thread client = new Thread(new Client());

        client.start();
        server.start();
        frame = new App();
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
//                keyboard = new BufferedReader(new InputStreamReader(System.in));

                // Send request to server

                out = new PrintWriter(socket.getOutputStream(),true);

                // Get response
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (App.sendRequest) {
                    String request = frame.pack;
                    if(request.equals(null)){
                        continue;
                    }
                    else {
                        out.println(request);
                        if (request.contains("quit")) {
                            break;
                        }
                        String response = in.readLine();
                        System.out.println("El monto :"+ response);

                    }
                }
                if (App.end) {
                    System.out.println("Disconnecting...");
                    out.close();
                    in.close();
                    connector.close();
                    socket.close();
                }
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
                    connector.close();
                    socket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }




        }
    }
    public static double getResponse() {
        String pack = Server1.command_client;
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
