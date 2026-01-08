package mainmenu;

import financeapp.FinanceTrackerForm;
import javax.swing.*;
import habit.HabitTrackerFrame;
import meal.MealPlannerFrame;
import mood.MoodTrackerFrame;
import study.StudyPlannerFrame;
import auth.Theme; // import Theme klase

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

        // ---------- DUGMAD ----------
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
        financeBtn.setBounds(60, 220, 180, 40);
        studyBtn.setBounds(60, 280, 180, 40);
        logoutBtn.setBounds(60, 340, 180, 40);

        add(habitBtn);
        add(mealBtn);
        add(moodBtn);
        add(financeBtn);
        add(studyBtn);
        add(logoutBtn);

        // ---------- ACTIONS ----------
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
            frame.getContentPane().setBackground(Theme.getColor(theme)); // primjena teme
            frame.setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new auth.LoginFrame().setVisible(true);
        });

        // Primjena teme na MainMenuFrame
        applyTheme();
    }

    private void applyTheme() {
        getContentPane().setBackground(Theme.getColor(theme)); // koristi Theme klasu
    }
}
