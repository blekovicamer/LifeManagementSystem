package financeapp;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class TransactionManager {

    private final MongoCollection<Document> collection;

    public TransactionManager() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("transactions");
    }

    public void addTransaction(Transaction t) {
        if (t != null) {
            collection.insertOne(t.toDocument());
        }
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> list = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();

                ObjectId id = doc.getObjectId("_id");
                String type = doc.getString("Vrsta");
                Double amount = doc.getDouble("Iznos");
                String description = doc.getString("Opis");

                if (type == null) type = "Unknown";
                if (amount == null) amount = 0.0;
                if (description == null) description = "";

                Transaction t = new Transaction(id, type, amount, description);
                list.add(t);
            }
        }
        return list;
    }

    public void deleteTransaction(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }

    public void updateTransaction(Transaction t) {
        Document updated = new Document("Vrsta", t.getType())
                .append("GlavnaVrsta", t.getMainType())
                .append("Iznos", t.getAmount())
                .append("Opis", t.getDescription());

        collection.updateOne(new Document("_id", t.getId()), new Document("$set", updated));
    }

    public double getTotalIncome() {
        double total = 0;
        for (Transaction t : getAllTransactions()) {
            if ("Prihod".equalsIgnoreCase(t.getMainType())) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public double getTotalExpense() {
        double total = 0;
        for (Transaction t : getAllTransactions()) {
            if ("Rashod".equalsIgnoreCase(t.getMainType())) {
                total += t.getAmount();
            }
        }
        return total;
    }
}
