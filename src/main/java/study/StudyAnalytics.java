package study;

import com.mongodb.client.MongoCollection;
import financeapp.MongoDBConnection;
import org.bson.Document;

import javax.swing.*;

public class StudyAnalytics {

    public void show() {
        MongoCollection<Document> tasks =
                MongoDBConnection.getDatabase().getCollection("study");

        long total = tasks.countDocuments();
        long completed = tasks.countDocuments(new Document("completed", true));

        int percent = total == 0 ? 0 : (int)((completed * 100) / total);

        String bar = "█".repeat(percent / 10) + "░".repeat(10 - percent / 10);

        JOptionPane.showMessageDialog(null,
                "Completed: " + completed + " / " + total +
                        "\nProgress: " + bar + " " + percent + "%",
                "Study Analytics", JOptionPane.INFORMATION_MESSAGE);
    }
}
