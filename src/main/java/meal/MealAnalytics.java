package meal;

import com.mongodb.client.MongoCollection;
import financeapp.MongoDBConnection;
import org.bson.Document;

import javax.swing.*;

public class MealAnalytics {

    public void show() {
        MongoCollection<Document> meals =
                MongoDBConnection.getDatabase().getCollection("meals");

        long totalMeals = meals.countDocuments();
        int totalCalories = 0;

        for (Document doc : meals.find()) {
            totalCalories += doc.getInteger("calories", 0);
        }

        String message = "Total meals: " + totalMeals +
                "\nTotal calories: " + totalCalories;

        JOptionPane.showMessageDialog(null, message, "Meal Analytics", JOptionPane.INFORMATION_MESSAGE);
    }
}
