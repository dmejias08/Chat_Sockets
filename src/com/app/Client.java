package com.app;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A class who connects to server through a socket-connection
 * @version 1, 27/08/2021
 * @author Diana Mejías Hernández, 2020077281
 */
public class Client {
    public static final int port = 9090;
    public static final int port2 = 9999;
    public static Socket server;
    public static Socket server2;
    public static App1 frame1;

    public static void main(String[] args) throws IOException {


        frame1 = new App1("Cliente 1");

        server = new Socket("localhost",port);
        server2 = new Socket("localhost",port2);

        Thread client1 = new Thread(new Client1());
        Thread server1 = new Thread(new Server2());

        client1.start();
        server1.start();



    }

    /**
     * A private class in Client. It can read and send a response to Server
     */
    private static class Server2 implements Runnable{

        private static BufferedReader in;
        private static PrintWriter out;
        private static String command_client;


        @Override
        public void run() {

            try {
                // Read from client
                in = new BufferedReader(new InputStreamReader(server2.getInputStream())); //  price from client

                //Send response to  client
                out = new PrintWriter(server2.getOutputStream(), true); // sent the total to server

                while (true) {
                    if (frame1.sendRequest) {
                        command_client = in.readLine();
                        out.println(getResponse());

                    }
                    else{
                        System.out.println("Disconnecting...");
                        in.close();
                        out.close();
                        server2.close();
                        break;
                    }

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * A private class in Client who can send a request to server and read a response
     */
    private static class Client1 implements Runnable {
        private static BufferedReader in;
        private static PrintWriter out;
        private static String response;

        @Override
        public void run() {
            try {

                //Response from server
                in = new BufferedReader(new InputStreamReader(server.getInputStream())); //  price from server

                //Request to server
                out = new PrintWriter(server.getOutputStream(), true); // sent the total to server

                while (true) {
                    if (frame1.sendRequest) {
//                        System.out.println("ESTOY EN EL CICLO DE CIELNTE EN CLIENTE ");
                        String request = frame1.pack;
//                        System.out.println("Hago pedido a Server1: "+request);
                        if (request.equals(null) == false) {
                            out.println(request);
                            response = in.readLine(); // request servidor
//                            System.out.println("El monto :" + response);
                            frame1.total.setText("Monto: " + response);
//                            frame1.pack = "0";
                        }

                    } else {
                        System.out.println("Disconecting");
                        server.close();
                        in.close();
                        out.close();
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }




    /**
     * A method that reads a string that contains numbers, divides it into each number as an integer.
     * @return the result of an operation that contains the numbers that were in the string.
     */

    static double getResponse() {
//        try {
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
            for (int i = 0; i < length; i++) {

                if (pack.charAt(i) == 'E') {
                    price = pack.substring(1, i);
//                System.out.println(price);
                    cont = i;

                } else if (pack.charAt(i) == 'e') {
                    weight = pack.substring(cont + 1, i);
                    tax = pack.substring(i + 1, length);
//                System.out.println(weight+"  "+tax);
                }
            }
            newPrice = Integer.parseInt(price);
            newTax = Integer.parseInt(tax);
            newWeight = Integer.parseInt(weight);

            total = (newPrice * newTax / 100) + (newWeight * 0.15);

            return total;
//        }finally {
//            JOptionPane.showMessageDialog(null, "Debe ingresar numeros");
//        }
    }
}
