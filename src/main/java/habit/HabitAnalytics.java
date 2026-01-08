package habit;

import com.mongodb.client.MongoCollection;
import financeapp.MongoDBConnection;
import org.bson.Document;

import javax.swing.*;

public class HabitAnalytics {

    public void show() {
        MongoCollection<Document> habits =
                MongoDBConnection.getDatabase().getCollection("habits");

        long total = habits.countDocuments();
        long completed = habits.countDocuments(
                new Document("completed", true)
        );

        int percent = total == 0 ? 0 : (int)((completed * 100) / total);

        String bar = "█".repeat(percent / 10) + "░".repeat(10 - percent / 10);

        JOptionPane.showMessageDialog(null,
                "Completed: " + completed + " / " + total +
                        "\nProgress: " + bar + " " + percent + "%");
    }
}
