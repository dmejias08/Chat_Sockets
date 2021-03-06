package com.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class creates the interface, in which the chat between the server and the client occurs.
 * class Client and class Server use an instance of this class
 * @version 1 27/08/2021
 * @author Diana Mejías Hernández
 */

public class App1 extends JFrame implements ActionListener {

    public JPanel pane;
    public JButton btnSend;
    public JButton btnExit;
    public JLabel labelPrice;
    public JLabel labelTax;
    public JLabel labelWeight;
    public JTextField textTax;
    public JTextField textWeight;
    public JTextField textPrice;
    public JLabel total;
    public String price = "0";
    public String tax = "0";
    public String weight = "0";
    public String pack = "f0E0e0";
    public Boolean sendRequest = true;
    public Boolean end = false;

    /**
     * This constructor is where the interface and its components were created
     *
     * @param title Set the title that the interface is going to have
     */

    public App1(String title) {

        setTitle(title);
        setVisible(true);
        setSize(300, 400);
        setBackground(Color.getColor("#bad5ff"));
        pane = new JPanel();
        this.getContentPane().add(pane);
        pane.setLayout(null);
        pane.setBackground(Color.decode("#bad5ff"));

        btnSend = new JButton("Enviar");
        btnSend.setSize(150, 50);
        btnSend.setLocation(50, 200);
        btnSend.addActionListener(this);
        pane.add(btnSend);


        total = new JLabel("Respuesta aparecerá aquí");
        total.setBounds(100, 100, 200, 40);
        pane.add(total);

        textPrice = new JTextField();
        textPrice.setBounds(100, 15, 60, 25);
        pane.add(textPrice);

        textWeight = new JTextField();
        textWeight.setBounds(100, 50, 60, 25);
        pane.add(textWeight);

        textTax = new JTextField();
        textTax.setBounds(100, 80, 60, 25);
        pane.add(textTax);

        labelPrice = new JLabel("Precio: ");
        labelPrice.setBounds(10, 15, 70, 25);
        pane.add(labelPrice);

        labelWeight = new JLabel("Peso: ");
        labelWeight.setBounds(10, 50, 70, 25);
        pane.add(labelWeight);

        labelTax = new JLabel("Impuesto: ");
        labelTax.setBounds(10, 80, 70, 25);
        pane.add(labelTax);

        setResizable(false);
        pane.repaint();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * this method gets the numbers(prices, tax and weight)  from the interface
     *
     * @param e Defines the event of button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
//        try{

        if (e.getSource() == btnSend) {
            try {

                price = textPrice.getText();
                tax = textTax.getText();
                weight = textWeight.getText();


                int checkPrice = Integer.parseInt(price);
                int checkTax = Integer.parseInt(tax);
                int checkWeight = Integer.parseInt(weight);

                textPrice.setText(null);
                textTax.setText(null);
                textWeight.setText(null);

                this.pack = "f" + price + "E" + weight + "e" + tax;
            } catch (NumberFormatException n){
            this.pack = "f0E0e0";
            textPrice.setText(null);
            textTax.setText(null);
            textWeight.setText(null);

            JOptionPane.showMessageDialog(null, "Debe ingresar números");
        }
    }
////        JOptionPane.showMessageDialog(null, pack);


        }
    }

