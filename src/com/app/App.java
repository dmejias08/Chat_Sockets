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
    public String price = "0";
    public String tax = "0";
    public  String weight = "0";
    public String pack= "0";
    public static Boolean sendRequest = true;
    public static Boolean end = false;


    public  App() {
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                price = textPrice.getText();
                tax = textTax.getText();
                weight = textWeight.getText();


                sendRequest = false;

//                textPrice.setText(null);
//                textTax.setText(null);
//                textWeight.setText(null);

                pack = "f"+price + "E" + weight +"e" + tax;

                JOptionPane.showMessageDialog(null, pack);



            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                end = true;
                sendRequest = false;
            }
        });


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void frame(String title){
        JFrame frame = new JFrame(title);
        frame.setContentPane(new App().JPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
