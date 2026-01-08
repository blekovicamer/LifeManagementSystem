package auth;

import mainmenu.MainMenuFrame;
import financeapp.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;

public class AccountDetailsFrame extends JFrame {

    private User currentUser;
    private MainMenuFrame mainMenu;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> themeCombo;
    private JButton saveBtn;

    public AccountDetailsFrame(User user, MainMenuFrame mainMenu) {
        this.currentUser = user;
        this.mainMenu = mainMenu;

        setTitle("Account Details");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel themeLabel = new JLabel("Theme:");

        usernameField = new JTextField(user.getUsername());
        passwordField = new JPasswordField();
        themeCombo = new JComboBox<>(new String[]{"default", "dark", "light"});
        themeCombo.setSelectedItem(user.getTheme());

        saveBtn = new JButton("Save Changes");

        usernameLabel.setBounds(20, 20, 100, 25);
        usernameField.setBounds(120, 20, 180, 25);

        passwordLabel.setBounds(20, 60, 100, 25);
        passwordField.setBounds(120, 60, 180, 25);

        themeLabel.setBounds(20, 100, 100, 25);
        themeCombo.setBounds(120, 100, 180, 25);

        saveBtn.setBounds(100, 150, 150, 30);

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(themeLabel);
        add(themeCombo);
        add(saveBtn);

        saveBtn.addActionListener(e -> saveChanges());
    }

    private void saveChanges() {
        String newUsername = usernameField.getText().trim();
        String newPassword = new String(passwordField.getPassword()).trim();
        String newTheme = (String) themeCombo.getSelectedItem();

        if (newUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty!");
            return;
        }

        MongoCollection<Document> users = MongoDBConnection.getDatabase().getCollection("users");

        // check if new username exists and it's not the current one
        Document existingUser = users.find(Filters.eq("username", newUsername)).first();
        if (existingUser != null && !newUsername.equals(currentUser.getUsername())) {
            JOptionPane.showMessageDialog(this, "Username already taken!");
            return;
        }

        Document updateFields = new Document("username", newUsername)
                .append("theme", newTheme);

        if (!newPassword.isEmpty()) {
            updateFields.append("password", newPassword);
            currentUser.setPassword(newPassword);
        }

        users.updateOne(Filters.eq("username", currentUser.getUsername()), new Document("$set", updateFields));

        currentUser.setUsername(newUsername);
        currentUser.setTheme(newTheme);

        // Update MainMenu
        mainMenu.applyTheme();
        mainMenu.setTitle("Main Menu - " + newUsername);

        JOptionPane.showMessageDialog(this, "Account updated successfully!");
        dispose();
    }
}
