package habit;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import financeapp.MongoDBConnection;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import auth.Theme;

public class HabitTrackerFrame extends JFrame {

    private JTextField habitField;
    private JTable habitTable;
    private DefaultTableModel model;
    private MongoCollection<Document> habitsCollection;

    // ---------- KONSTRUKTOR BEZ ARGUMENATA ----------
    public HabitTrackerFrame() {
        this("default"); // default tema ako se ne proslijedi
    }

    // ---------- KONSTRUKTOR S TEMOM ----------
    public HabitTrackerFrame(String theme) {
        setTitle("Habit Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Primjena teme
        getContentPane().setBackground(Theme.getColor(theme));

        habitsCollection = MongoDBConnection.getDatabase().getCollection("habits");

        // ---------- UI ----------
        JLabel title = new JLabel("Habit Tracker");
        title.setBounds(180, 10, 200, 30);
        add(title);

        habitField = new JTextField();
        habitField.setBounds(20, 50, 300, 30);
        add(habitField);

        JButton addBtn = new JButton("Add Habit");
        addBtn.setBounds(340, 50, 120, 30);
        add(addBtn);

        model = new DefaultTableModel();
        model.addColumn("Habit");
        model.addColumn("Completed");

        habitTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(habitTable);
        scrollPane.setBounds(20, 100, 440, 200);
        add(scrollPane);

        JButton completeBtn = new JButton("Mark Completed");
        completeBtn.setBounds(150, 320, 180, 30);
        add(completeBtn);

        // ---------- ACTIONS ----------
        addBtn.addActionListener(e -> {
            String habit = habitField.getText().trim();
            if (habit.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Habit cannot be empty!");
                return;
            }

            // Spremanje u MongoDB
            Document doc = new Document("name", habit)
                    .append("completed", false)
                    .append("date", LocalDate.now().toString());
            habitsCollection.insertOne(doc);

            // Dodavanje u tabelu
            model.addRow(new Object[]{habit, "No"});
            habitField.setText("");
        });

        completeBtn.addActionListener(e -> {
            int row = habitTable.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Select a habit first!");
                return;
            }

            String habitName = (String) model.getValueAt(row, 0);

            // Update MongoDB
            habitsCollection.updateOne(
                    new Document("name", habitName),
                    new Document("$set", new Document("completed", true))
            );

            model.setValueAt("Yes", row, 1);
        });

        // ---------- Load habits from MongoDB ----------
        loadHabitsFromDB();
    }

    private void loadHabitsFromDB() {
        for (Document doc : habitsCollection.find()) {
            String name = doc.getString("name");
            boolean completed = doc.getBoolean("completed", false);
            model.addRow(new Object[]{name, completed ? "Yes" : "No"});
        }
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
