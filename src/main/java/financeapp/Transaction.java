package financeapp;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Transaction {

    private ObjectId id;
    private String type; // kategorija: Plata, Hrana, Racuni, Zabava, Prijevoz, Ostalo
    private double amount;
    private String description;

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public Transaction(ObjectId id, String type, double amount, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public String getMainType() {
        // Plata je prihod, ostalo rashod
        if ("Plata".equalsIgnoreCase(type)) {
            return "Prihod";
        } else {
            return "Rashod";
        }
    }

    public Document toDocument() {
        return new Document("Vrsta", type)
                .append("GlavnaVrsta", getMainType())
                .append("Iznos", amount)
                .append("Opis", description);
    }

    public ObjectId getId() { return id; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    public void setType(String type) { this.type = type; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
}
