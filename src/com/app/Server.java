package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static ServerSocket connector;
    public static ServerSocket connector2;
    private static int port = 9090;
    private static int port2 = 9999;
    public static Socket socket;
    public static Socket socket2;
    private static App1 frame;
    public static void main(String[] args) throws IOException {
        frame = new App1("Cliente 2");

        connector = new ServerSocket(port);
        connector2 = new ServerSocket(port2);
        System.out.println("Server is waiting for connection...");
        socket = connector.accept();
        socket2 = connector2.accept();
        System.out.println("Clientes conectado");

        Thread server = new Thread(new Server1());
        Thread client = new Thread(new Client());

        client.start();
        server.start();


    }



    private static class Client implements Runnable  {

        private  static BufferedReader keyboard;
        private  static BufferedReader in;
        private static PrintWriter out;



        @Override
        public void run() {
            try{

                // Send request to server

                out = new PrintWriter(socket2.getOutputStream(),true);

                // Get response
                in = new BufferedReader(new InputStreamReader(socket2.getInputStream()));

                while (true) {
                    if (frame.sendRequest) {
                        String request = frame.pack;
                        if (request.equals(null) == false){
                            out.println(request);
                            String response = in.readLine();
                            frame.total.setText("Monto: "+ response);

                        }

                    }
                    else{
                        System.out.println("Disconnecting...");
                        out.close();
                        in.close();
                        connector.close();
                        socket2.close();
                        break;
                    }


                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }




        }
    }

    private static class Server1 implements Runnable{
        private static BufferedReader in;
        private static PrintWriter out;
        private static String command_client;

        @Override
        public void run() {

            try {
                // Read from client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //  price from client

                //Send response to  client
                out = new PrintWriter(socket.getOutputStream(), true); // sent the total to server

                while (true) {
                    if (frame.sendRequest){
                        command_client = in.readLine();
//                        System.out.println("Pedido cliente 1: " + command_client);
                        if (command_client.equals(null) == false) {
                            out.println(getResponse());
                        }else {
                           continue;
                        }
                }
                else {
                    System.out.println("Disconnecting...");
                    in.close();
                    out.close();
                    connector.close();
                    socket.close();
                    break;
                }
            }
            }
            catch (IOException e) {
                e.printStackTrace();
            }




        }
    }
    static double getResponse() {
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
//                System.out.println(price);
                cont = i;

            }else if (pack.charAt(i) == 'e') {
                weight = pack.substring(cont+1, i);
                tax = pack.substring(i+1, length);
//                System.out.println(weight+"  "+tax);
            }


        }
        newPrice = Integer.parseInt(price);
        newTax = Integer.parseInt(tax);
        newWeight = Integer.parseInt(weight);

        total = (newPrice*newTax/100) + (newWeight*0.15);

        return total;
    }
}
