package mainmenu;

import financeapp.FinanceTrackerForm;
import javax.swing.*;
import habit.HabitTrackerFrame;
import meal.MealPlannerFrame;
import mood.MoodTrackerFrame; // import Mood Tracker
import study.StudyPlannerFrame;


public class MainMenuFrame extends JFrame {

    private String username;
    private String theme;

    public MainMenuFrame(String username, String theme) {
        this.username = username;
        this.theme = theme;

        setTitle("Main Menu - " + username);
        setSize(300, 500); // povećano zbog dodatnog dugmeta
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton habitBtn = new JButton("Habit Tracker");
        JButton mealBtn = new JButton("Meal Planner");
        JButton moodBtn = new JButton("Mood Tracker");
        JButton studyBtn = new JButton("Study Planner");
        JButton financeBtn = new JButton("Finance Tracker");
        JButton logoutBtn = new JButton("Logout");

        // Podesi pozicije dugmadi
        habitBtn.setBounds(60, 40, 180, 40);
        mealBtn.setBounds(60, 100, 180, 40);
        moodBtn.setBounds(60, 160, 180, 40);
        studyBtn.setBounds(60, 280, 180, 40);
        financeBtn.setBounds(60, 220, 180, 40);
        logoutBtn.setBounds(60, 340, 180, 40);

        add(habitBtn);
        add(mealBtn);
        add(moodBtn);  // dodano dugme u GUI
        add(financeBtn);
        add(studyBtn);
        add(logoutBtn);

        // ---------- ACTIONS ----------

        habitBtn.addActionListener(e -> new HabitTrackerFrame().setVisible(true));
        mealBtn.addActionListener(e -> new MealPlannerFrame().setVisible(true));
        moodBtn.addActionListener(e -> new MoodTrackerFrame().setVisible(true));
        studyBtn.addActionListener(e -> new StudyPlannerFrame().setVisible(true));


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
    }
}
