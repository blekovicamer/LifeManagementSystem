package mainmenu;

import financeapp.FinanceTrackerForm;
import javax.swing.*;
import habit.HabitTrackerFrame;
import meal.MealPlannerFrame;
import mood.MoodTrackerFrame;
import study.StudyPlannerFrame;
import auth.Theme;
import auth.User;
import utils.PDFExporter;
import auth.AccountDetailsFrame;


import java.awt.*;

public class MainMenuFrame extends JFrame {

    private User currentUser;
    private JPanel panel;

    public MainMenuFrame(User user) {
        this.currentUser = user;

        setTitle("Main Menu - " + user.getUsername());
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---------- Panel with GridBagLayout ----------
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.getColor(user.getTheme()));
        getContentPane().add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // buttons expand
        gbc.insets = new Insets(10, 10, 10, 10); // padding
        gbc.weightx = 1;
        gbc.weighty = 1;

        // ---------- Buttons ----------
        JButton habitBtn = new JButton("Habit Tracker");
        JButton mealBtn = new JButton("Meal Planner");
        JButton moodBtn = new JButton("Mood Tracker");
        JButton studyBtn = new JButton("Study Planner");
        JButton financeBtn = new JButton("Finance Tracker");
        JButton accountBtn = new JButton("Account Details");
        JButton exportPdfBtn = new JButton("Export PDF");
        JButton logoutBtn = new JButton("Logout");

        JButton[] buttons = {habitBtn, mealBtn, moodBtn, studyBtn, financeBtn, accountBtn, exportPdfBtn, logoutBtn};

        // ---------- Add buttons in a 2x4 grid ----------
        int cols = 2;
        int row = 0, col = 0;
        for (JButton btn : buttons) {
            gbc.gridx = col;
            gbc.gridy = row;
            panel.add(btn, gbc);
            col++;
            if (col >= cols) {
                col = 0;
                row++;
            }
        }

        // ---------- Theme selector ----------
        JLabel themeLabel = new JLabel("Select Theme:");
        JComboBox<String> themeCombo = new JComboBox<>(new String[]{"default", "dark", "light"});
        themeCombo.setSelectedItem(user.getTheme());

        gbc.gridx = 0;
        gbc.gridy = row + 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        panel.add(themeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = row + 1;
        gbc.gridwidth = 1;
        panel.add(themeCombo, gbc);

        themeCombo.addActionListener(e -> {
            currentUser.setTheme((String) themeCombo.getSelectedItem());
            applyTheme();
        });

        // ---------- Button actions ----------
        habitBtn.addActionListener(e -> new HabitTrackerFrame(currentUser.getTheme()).setVisible(true));
        mealBtn.addActionListener(e -> new MealPlannerFrame(currentUser.getTheme()).setVisible(true));
        moodBtn.addActionListener(e -> new MoodTrackerFrame(currentUser.getTheme()).setVisible(true));
        studyBtn.addActionListener(e -> new StudyPlannerFrame(currentUser.getTheme()).setVisible(true));

        financeBtn.addActionListener(e -> {
            JFrame frame = new JFrame("Finance Tracker");
            frame.setContentPane(new FinanceTrackerForm().getMainPanel());
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().setBackground(Theme.getColor(currentUser.getTheme()));
            frame.setVisible(true);
        });

        accountBtn.addActionListener(e -> {
            AccountDetailsFrame accountFrame = new AccountDetailsFrame(currentUser, this);
            accountFrame.setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new auth.LoginFrame().setVisible(true);
        });

        exportPdfBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save PDF");
            fileChooser.setSelectedFile(new java.io.File("LifeManagementReport.pdf"));
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try {
                    PDFExporter.exportAllTrackers(currentUser.getTheme(), fileToSave.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Export successful!\nSaved to: " + fileToSave.getAbsolutePath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error exporting PDF!");
                }
            }
        });

        applyTheme();
    }

    public void applyTheme() {
        panel.setBackground(Theme.getColor(currentUser.getTheme()));

        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setBackground(Theme.getColor(currentUser.getTheme()).brighter());
                comp.setForeground(Color.BLACK);
            } else if (comp instanceof JComboBox) {
                comp.setBackground(Color.WHITE);
                comp.setForeground(Color.BLACK);
            }
        }
        panel.revalidate();
        panel.repaint();
    }
}
