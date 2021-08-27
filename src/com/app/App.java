package com.app;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends Frame implements ActionListener {
    public JTextArea response;
    public JTextField textTax;
    public JTextField textWeight;
    public JButton btnSend = new JButton("Enviar");
    public JTextField textPrice;
    public JLabel labelPrice;
    public JLabel labelWeight;
    public JLabel labelTax;
    public javax.swing.JPanel JPanel;
    private JButton btnExit;
    public String price = "0";
    public String tax = "0";
    public  String weight = "0";
    public String pack = "f0E0e0";
    public static Boolean sendRequest = true;
    public static Boolean end = false;



    public  App() {
        btnSend.setBounds(10,15,40,30);
        btnSend.addActionListener(this);

//        this.pack = "f0E0e2";
//        btnSend.addActionListener(new ActionListener() {
//            String pack = "f0E0e2";
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                price = textPrice.getText();
//                tax = textTax.getText();
//                weight = textWeight.getText();
//
//                sendRequest = true;
//
//                textPrice.setText(null);
//                textTax.setText(null);
//                textWeight.setText(null);
//
//
//
//                pack = "f" + price + "E" + weight + "e" + tax;
//
//                JOptionPane.showMessageDialog(null, pack);
//
//
//
//            }
//        });


//        btnExit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                end = true;
//                sendRequest = false;
//            }
//        });


    }

    private void createUIComponents() {

        JPanel.add(btnSend);


    }

    public void frame(String title){
        JFrame frame = new JFrame(title);
        frame.setContentPane(new App().JPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()== btnSend) {
            price = textPrice.getText();
            tax = textTax.getText();
            weight = textWeight.getText();

            sendRequest = false;

            textPrice.setText(null);
            textTax.setText(null);
            textWeight.setText(null);


            this.pack = "f" + price + "E" + weight + "e" + tax;

            JOptionPane.showMessageDialog(null, pack);

        }
    }
}
