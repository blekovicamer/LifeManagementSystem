package financeapp;

import javax.swing.*;

public class Main {
    public static void main (String[] args){
        JFrame frame = new JFrame("Personal finance tracker");
        frame.setContentPane(new FinanceTrackerForm().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,500);
        frame.setVisible(true);
    }



}
