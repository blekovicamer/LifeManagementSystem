package meal;

import com.mongodb.client.MongoCollection;
import financeapp.MongoDBConnection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class MealPlannerFrame extends JFrame {

    private JTextField mealField;
    private JTextField caloriesField;
    private JTable mealTable;
    private DefaultTableModel model;
    private MongoCollection<Document> mealsCollection;

    public MealPlannerFrame() {
        setTitle("Meal Planner");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        mealsCollection = MongoDBConnection.getDatabase().getCollection("meals");

        // ---------- UI ----------
        JLabel title = new JLabel("Meal Planner");
        title.setBounds(180, 10, 200, 30);
        add(title);

        mealField = new JTextField();
        mealField.setBounds(20, 50, 200, 30);
        add(mealField);

        caloriesField = new JTextField();
        caloriesField.setBounds(230, 50, 100, 30);
        add(caloriesField);

        JButton addBtn = new JButton("Add Meal");
        addBtn.setBounds(340, 50, 120, 30);
        add(addBtn);

        model = new DefaultTableModel();
        model.addColumn("Meal");
        model.addColumn("Calories");
        model.addColumn("Date");

        mealTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(mealTable);
        scrollPane.setBounds(20, 100, 440, 200);
        add(scrollPane);

        JButton analyticsBtn = new JButton("Show Analytics");
        analyticsBtn.setBounds(150, 320, 180, 30);
        add(analyticsBtn);

        // ---------- ACTIONS ----------

        addBtn.addActionListener(e -> {
            String mealName = mealField.getText().trim();
            String caloriesText = caloriesField.getText().trim();

            if (mealName.isEmpty() || caloriesText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields!");
                return;
            }

            int calories;
            try {
                calories = Integer.parseInt(caloriesText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Calories must be a number!");
                return;
            }

            // Spremanje u MongoDB
            Document doc = new Document("name", mealName)
                    .append("calories", calories)
                    .append("date", LocalDate.now().toString());
            mealsCollection.insertOne(doc);

            // Dodavanje u tabelu
            model.addRow(new Object[]{mealName, calories, LocalDate.now().toString()});
            mealField.setText("");
            caloriesField.setText("");
        });

        analyticsBtn.addActionListener(e -> {
            new MealAnalytics().show();
        });

        loadMealsFromDB();
    }

    private void loadMealsFromDB() {
        for (Document doc : mealsCollection.find()) {
            String name = doc.getString("name");
            int calories = doc.getInteger("calories", 0);
            String date = doc.getString("date");
            model.addRow(new Object[]{name, calories, date});
        }
    }
}
