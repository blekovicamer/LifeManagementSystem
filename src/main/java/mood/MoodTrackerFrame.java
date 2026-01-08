package mood;

import com.mongodb.client.MongoCollection;
import financeapp.MongoDBConnection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import auth.Theme;

public class MoodTrackerFrame extends JFrame {

    private JTextField moodField;
    private JTextField noteField;
    private JTable moodTable;
    private DefaultTableModel model;
    private MongoCollection<Document> moodsCollection;

    // ---------- KONSTRUKTOR BEZ ARGUMENATA ----------
    public MoodTrackerFrame() {
        this("default");
    }

    // ---------- KONSTRUKTOR S TEMOM ----------
    public MoodTrackerFrame(String theme) {
        setTitle("Mood Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Primjena teme
        getContentPane().setBackground(Theme.getColor(theme));

        moodsCollection = MongoDBConnection.getDatabase().getCollection("moods");

        // ---------- UI ----------
        JLabel title = new JLabel("Mood Tracker");
        title.setBounds(180, 10, 200, 30);
        add(title);

        moodField = new JTextField();
        moodField.setBounds(20, 50, 150, 30);
        add(moodField);

        noteField = new JTextField();
        noteField.setBounds(180, 50, 180, 30);
        add(noteField);

        JButton addBtn = new JButton("Add Mood");
        addBtn.setBounds(370, 50, 100, 30);
        add(addBtn);

        model = new DefaultTableModel();
        model.addColumn("Mood");
        model.addColumn("Note");
        model.addColumn("Date");

        moodTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(moodTable);
        scrollPane.setBounds(20, 100, 440, 200);
        add(scrollPane);

        JButton analyticsBtn = new JButton("Show Analytics");
        analyticsBtn.setBounds(150, 320, 180, 30);
        add(analyticsBtn);

        // ---------- ACTIONS ----------
        addBtn.addActionListener(e -> {
            String mood = moodField.getText().trim();
            String note = noteField.getText().trim();

            if (mood.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mood cannot be empty!");
                return;
            }

            // Spremanje u MongoDB
            Document doc = new Document("mood", mood)
                    .append("note", note)
                    .append("date", LocalDate.now().toString());
            moodsCollection.insertOne(doc);

            // Dodavanje u tabelu
            model.addRow(new Object[]{mood, note, LocalDate.now().toString()});
            moodField.setText("");
            noteField.setText("");
        });

        analyticsBtn.addActionListener(e -> new MoodAnalytics().show());

        loadMoodsFromDB();
    }

    private void loadMoodsFromDB() {
        for (Document doc : moodsCollection.find()) {
            String mood = doc.getString("mood");
            String note = doc.getString("note");
            String date = doc.getString("date");
            model.addRow(new Object[]{mood, note, date});
        }
    }
    public DefaultTableModel getModel() {
        return model;
    }
}
