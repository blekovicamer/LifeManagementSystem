package mainmenu;

import financeapp.FinanceTrackerForm;

import javax.swing.*;

public class MainMenuFrame extends JFrame {

    private String username;
    private String theme;

    public MainMenuFrame(String username, String theme) {
        this.username = username;
        this.theme = theme;

        setTitle("Main Menu - " + username);
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton financeBtn = new JButton("Finance Tracker");
        JButton logoutBtn = new JButton("Logout");

        financeBtn.setBounds(60, 50, 180, 40);
        logoutBtn.setBounds(60, 120, 180, 40);

        add(financeBtn);
        add(logoutBtn);

        financeBtn.addActionListener(e -> {
            JFrame frame = new JFrame("Finance Tracker");
            frame.setContentPane(new FinanceTrackerForm().getMainPanel());
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });


        logoutBtn.addActionListener(e -> {
            dispose();
            new auth.LoginFrame().setVisible(true);
        });

        applyTheme();
    }

    private void applyTheme() {
        if (theme.equals("dark")) {
            getContentPane().setBackground(java.awt.Color.DARK_GRAY);
        }
        // kasnije dodaješ ostale teme (BONUS)
    }
}
