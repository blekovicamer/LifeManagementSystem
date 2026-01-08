package study;

import com.mongodb.client.MongoCollection;
import financeapp.MongoDBConnection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class StudyPlannerFrame extends JFrame {

    private JTextField subjectField;
    private JTextField taskField;
    private JTable taskTable;
    private DefaultTableModel model;
    private MongoCollection<Document> studyCollection;

    public StudyPlannerFrame() {
        setTitle("Study Planner");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        studyCollection = MongoDBConnection.getDatabase().getCollection("study");

        JLabel title = new JLabel("Study Planner");
        title.setBounds(180, 10, 200, 30);
        add(title);

        subjectField = new JTextField();
        subjectField.setBounds(20, 50, 150, 30);
        add(subjectField);

        taskField = new JTextField();
        taskField.setBounds(180, 50, 180, 30);
        add(taskField);

        JButton addBtn = new JButton("Add Task");
        addBtn.setBounds(370, 50, 100, 30);
        add(addBtn);

        model = new DefaultTableModel();
        model.addColumn("Subject");
        model.addColumn("Task");
        model.addColumn("Completed");
        model.addColumn("Date");

        taskTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBounds(20, 100, 440, 200);
        add(scrollPane);

        JButton completeBtn = new JButton("Mark Completed");
        completeBtn.setBounds(20, 320, 180, 30);
        add(completeBtn);

        JButton analyticsBtn = new JButton("Show Analytics");
        analyticsBtn.setBounds(250, 320, 180, 30);
        add(analyticsBtn);

        // ---------- ACTIONS ----------
        addBtn.addActionListener(e -> {
            String subject = subjectField.getText().trim();
            String task = taskField.getText().trim();
            if (subject.isEmpty() || task.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields!");
                return;
            }

            Document doc = new Document("subject", subject)
                    .append("task", task)
                    .append("completed", false)
                    .append("date", LocalDate.now().toString());
            studyCollection.insertOne(doc);

            model.addRow(new Object[]{subject, task, "No", LocalDate.now().toString()});
            subjectField.setText("");
            taskField.setText("");
        });

        completeBtn.addActionListener(e -> {
            int row = taskTable.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Select a task first!");
                return;
            }

            String taskName = (String) model.getValueAt(row, 1);
            studyCollection.updateOne(
                    new Document("task", taskName),
                    new Document("$set", new Document("completed", true))
            );

            model.setValueAt("Yes", row, 2);
        });

        analyticsBtn.addActionListener(e -> new StudyAnalytics().show());

        loadTasksFromDB();
    }

    private void loadTasksFromDB() {
        for (Document doc : studyCollection.find()) {
            String subject = doc.getString("subject");
            String task = doc.getString("task");
            boolean completed = doc.getBoolean("completed", false);
            String date = doc.getString("date");
            model.addRow(new Object[]{subject, task, completed ? "Yes" : "No", date});
        }
    }
}
