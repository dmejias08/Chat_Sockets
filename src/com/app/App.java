package com.app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    public JTextArea response;
    public JButton btnSend;
    public JTextField textTax;
    public JTextField textWeight;
    public JTextField textPrice;
    public JLabel labelPrice;
    public JLabel labelWeight;
    public JLabel labelTax;
    public javax.swing.JPanel JPanel;
    private JButton btnExit;
    public String price;
    public String tax;
    public  String weight;
    public String pack;
    public static Boolean sendRequest = false;
    public static Boolean end = false;


    public App() {
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendRequest = true;
                price = textPrice.getText();
                tax = textTax.getText();
                weight = textWeight.getText();



                pack = "f"+price + "E" + weight +"e" + tax;

//                textPrice.setText(null);
//                textWeight.setText(null);
//                textTax.setText(null);


            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end = true;
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void frame(){
        JFrame frame = new JFrame("Cliente");
        frame.setContentPane(new App().JPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
