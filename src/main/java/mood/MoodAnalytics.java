package mood;

import com.mongodb.client.MongoCollection;
import financeapp.MongoDBConnection;
import org.bson.Document;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class MoodAnalytics {

    public void show() {
        MongoCollection<Document> moods =
                MongoDBConnection.getDatabase().getCollection("moods");

        long totalEntries = moods.countDocuments();
        Map<String, Integer> moodCount = new HashMap<>();

        for (Document doc : moods.find()) {
            String mood = doc.getString("mood");
            moodCount.put(mood, moodCount.getOrDefault(mood, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Total entries: ").append(totalEntries).append("\n\n");
        for (Map.Entry<String, Integer> entry : moodCount.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Mood Analytics", JOptionPane.INFORMATION_MESSAGE);
    }
}
