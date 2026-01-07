package financeapp;

import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FinanceTrackerForm {

    private JPanel mainPanel;
    private JTextField amountField;
    private JTextField descriptionField;
    private JComboBox<String> typeCombo;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton exportButton;
    private JTable transactionTable;
    private JLabel incomeLabel;
    private JLabel expeseLabel;
    private JLabel balanceLabel;

    private TransactionManager manager;
    private ObjectId selectedTransactionId = null;

    public FinanceTrackerForm() {
        manager = new TransactionManager();

        // ----------------- INICIJALIZACIJA KATEGORIJA -----------------
        String[] categories = {"Plata", "Hrana", "Racuni", "Zabava", "Prijevoz", "Ostalo"};
        typeCombo.setModel(new DefaultComboBoxModel<>(categories));

        // ----------------- UCITAVANJE TABELA I SUMARY -----------------
        loadDataIntoTable();
        updateSummary();

        // ----------------- ADD -----------------
        addButton.addActionListener(e -> {
            try {
                String type = (String) typeCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Description cannot be empty");
                    return;
                }

                manager.addTransaction(new Transaction(type, amount, description));
                clearFields();
                loadDataIntoTable();
                updateSummary();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Amount must be a number");
            }
        });

        // ----------------- SELECT ROW -----------------
        transactionTable.getSelectionModel().addListSelectionListener(e -> {
            int row = transactionTable.getSelectedRow();
            if (row >= 0) {
                Transaction t = manager.getAllTransactions().get(row);
                selectedTransactionId = t.getId();
                typeCombo.setSelectedItem(t.getType());
                amountField.setText(String.valueOf(t.getAmount()));
                descriptionField.setText(t.getDescription());
            }
        });

        // ----------------- UPDATE -----------------
        updateButton.addActionListener(e -> {
            if (selectedTransactionId == null) {
                JOptionPane.showMessageDialog(null, "Niste odabrali transakciju!");
                return;
            }
            try {
                String type = (String) typeCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Opis ne može biti prazan!");
                    return;
                }

                manager.updateTransaction(new Transaction(selectedTransactionId, type, amount, description));
                JOptionPane.showMessageDialog(null, "Transakcija ažurirana!");
                clearFields();
                selectedTransactionId = null;
                loadDataIntoTable();
                updateSummary();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Iznos mora biti broj!");
            }
        });

        // ----------------- DELETE -----------------
        deleteButton.addActionListener(e -> {
            int row = transactionTable.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Niste odabrali transakciju!");
                return;
            }
            Transaction t = manager.getAllTransactions().get(row);
            int confirm = JOptionPane.showConfirmDialog(null, "Jeste li sigurni da želite izbrisati ovu transakciju?", "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                manager.deleteTransaction(t.getId());
                JOptionPane.showMessageDialog(null, "Transakcija izbrisana!");
                loadDataIntoTable();
                updateSummary();
            }
        });

        // ----------------- EXPORT -----------------
        exportButton.addActionListener(e -> {
            ArrayList<Transaction> transactions = manager.getAllTransactions();
            double totalIncome = manager.getTotalIncome();
            double totalExpense = manager.getTotalExpense();
            double balance = totalIncome - totalExpense;

            Map<String, Double> expenseByCategory = new HashMap<>();
            for (Transaction t : transactions) {
                if ("Rashod".equalsIgnoreCase(t.getMainType())) {
                    expenseByCategory.put(t.getType(), expenseByCategory.getOrDefault(t.getType(), 0.0) + t.getAmount());
                }
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save export file");
            int userSelection = fileChooser.showSaveDialog(mainPanel);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try (java.io.PrintWriter writer = new java.io.PrintWriter(fileToSave)) {

                    writer.println("╔═══════════════════════════╗");
                    writer.printf("    Ukupni prihod: %.2f%n", totalIncome);
                    writer.printf("    Ukupni rashod: %.2f%n", totalExpense);
                    writer.printf("    Stanje: %.2f%n", balance);
                    writer.println("   Rashodi po kategorijama:");

                    for (String cat : expenseByCategory.keySet()) {
                        writer.printf(" %s: %.2f%n", cat, expenseByCategory.get(cat));
                    }

                    writer.println("╚═══════════════════════════╝");

                    JOptionPane.showMessageDialog(null, "Export završen: " + fileToSave.getAbsolutePath());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Greška pri exportu: " + ex.getMessage());
                }
            }
        });
    }

    private void clearFields() {
        typeCombo.setSelectedIndex(0);
        amountField.setText("");
        descriptionField.setText("");
    }

    private void loadDataIntoTable() {
        ArrayList<Transaction> list = manager.getAllTransactions();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Vrsta");
        model.addColumn("Iznos");
        model.addColumn("Opis");

        for (Transaction t : list) {
            model.addRow(new Object[]{t.getType(), t.getAmount(), t.getDescription()});
        }

        transactionTable.setModel(model);
    }

    private void updateSummary() {
        double income = manager.getTotalIncome();
        double expense = manager.getTotalExpense();
        double balance = income - expense;

        incomeLabel.setText("Income: " + income);
        expeseLabel.setText("Expense: " + expense);
        balanceLabel.setText("Balance: " + balance);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {

    }
}
