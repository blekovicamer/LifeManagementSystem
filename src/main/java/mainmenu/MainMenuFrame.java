package mainmenu;

import financeapp.FinanceTrackerForm;
import javax.swing.*;
import habit.HabitTrackerFrame;
import meal.MealPlannerFrame;
import mood.MoodTrackerFrame;
import study.StudyPlannerFrame;
import auth.Theme;
import utils.PDFExporter;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    private String username;
    private String theme;
    private JPanel panel;

    public MainMenuFrame(String username, String theme) {
        this.username = username;
        this.theme = theme;

        setTitle("Main Menu - " + username);
        setSize(400, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.getColor(theme));
        getContentPane().add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 40, 10, 40); // top,left,bottom,right
        gbc.weightx = 1;


        JButton habitBtn = new JButton("Habit Tracker");
        JButton mealBtn = new JButton("Meal Planner");
        JButton moodBtn = new JButton("Mood Tracker");
        JButton studyBtn = new JButton("Study Planner");
        JButton financeBtn = new JButton("Finance Tracker");
        JButton logoutBtn = new JButton("Logout");
        JButton exportPdfBtn = new JButton("Export PDF");


        gbc.gridy = 0; panel.add(habitBtn, gbc);
        gbc.gridy = 1; panel.add(mealBtn, gbc);
        gbc.gridy = 2; panel.add(moodBtn, gbc);
        gbc.gridy = 3; panel.add(financeBtn, gbc);
        gbc.gridy = 4; panel.add(studyBtn, gbc);
        gbc.gridy = 5; panel.add(logoutBtn, gbc);
        gbc.gridy = 6; panel.add(exportPdfBtn, gbc);


        JLabel themeLabel = new JLabel("Select Theme:");
        JComboBox<String> themeCombo = new JComboBox<>(new String[]{"default", "dark", "light"});
        gbc.gridy = 7; panel.add(themeLabel, gbc);
        gbc.gridy = 8; panel.add(themeCombo, gbc);

        themeCombo.setSelectedItem(theme);

        themeCombo.addActionListener(e -> {
            this.theme = (String) themeCombo.getSelectedItem();
            applyTheme();
        });


        habitBtn.addActionListener(e -> new HabitTrackerFrame(theme).setVisible(true));
        mealBtn.addActionListener(e -> new MealPlannerFrame(theme).setVisible(true));
        moodBtn.addActionListener(e -> new MoodTrackerFrame(theme).setVisible(true));
        studyBtn.addActionListener(e -> new StudyPlannerFrame(theme).setVisible(true));

        financeBtn.addActionListener(e -> {
            JFrame frame = new JFrame("Finance Tracker");
            frame.setContentPane(new FinanceTrackerForm().getMainPanel());
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().setBackground(Theme.getColor(theme));
            frame.setVisible(true);
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
                    PDFExporter.exportAllTrackers(theme, fileToSave.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Export successful!\nSaved to: " + fileToSave.getAbsolutePath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error exporting PDF!");
                }
            }
        });
    }


    private void applyTheme() {
        panel.setBackground(Theme.getColor(theme));

        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setBackground(Theme.getColor(theme).brighter());
                comp.setForeground(Color.BLACK);
            } else if (comp instanceof JComboBox) {
                comp.setBackground(Color.WHITE);
                comp.setForeground(Color.BLACK);
            }
        }
        panel.repaint();
    }
}
