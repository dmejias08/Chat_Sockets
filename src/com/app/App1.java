package com.app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App1 extends JFrame implements ActionListener {

    public JPanel pane;
    public JButton btnSend;
    public JLabel labelPrice;
    public JLabel labelTax;
    public JLabel labelWeight;
    public JTextField textTax;
    public JTextField textWeight;
    public JTextField textPrice;
    public JLabel response;
    public String price = "0";
    public String tax = "0";
    public  String weight = "0";
    public String pack = "f0E0e0";
    public Boolean sendRequest = false;
    public Boolean end = false;


    public  App1(String title){

        setTitle(title);
        setVisible(true);
        setSize(300,400);
        pane = new JPanel();
        this.getContentPane().add(pane);
        pane.setLayout(null);

        btnSend = new JButton("Enviar");
        btnSend.setSize(150,50);
        btnSend.setLocation(50,200);
        btnSend.addActionListener(this);
        pane.add(btnSend);

        response = new JLabel("Response: ");
        response.setBounds(100, 40, 75,40);
        pane.add(response);

        textPrice = new JTextField();
        textPrice.setBounds(10,10,50,20);
        pane.add(textPrice);


        textWeight = new JTextField();
        textWeight.setBounds(10,40,50,20);
        pane.add(textWeight);


        textTax = new JTextField();
        textTax.setBounds(10,70,50,20);
        pane.add(textTax);

        setResizable(false);
        pane.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        price = textPrice.getText();
        tax = textTax.getText();
        weight = textWeight.getText();

        sendRequest = true;

        textPrice.setText(null);
        textTax.setText(null);
        textWeight.setText(null);


        this.pack = "f" + price + "E" + weight + "e" + tax;

        JOptionPane.showMessageDialog(null, pack);


    }
}
