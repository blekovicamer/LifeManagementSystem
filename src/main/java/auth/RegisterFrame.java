package auth;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import financeapp.MongoDBConnection;
import org.bson.Document;
import utils.PasswordValidator;


import javax.swing.*;

public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> themeBox;
    private JButton registerButton;

    public RegisterFrame() {
        setTitle("Register");
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        themeBox = new JComboBox<>(new String[]{"green", "blue", "pink", "orange", "dark", "cyberpunk"});
        registerButton = new JButton("Register");

        usernameField.setBounds(80, 20, 150, 25);
        passwordField.setBounds(80, 60, 150, 25);
        themeBox.setBounds(80, 100, 150, 25);
        registerButton.setBounds(80, 150, 150, 30);

        add(new JLabel("Username:")).setBounds(10, 20, 70, 25);
        add(new JLabel("Password:")).setBounds(10, 60, 70, 25);
        add(new JLabel("Theme:")).setBounds(10, 100, 70, 25);

        add(usernameField);
        add(passwordField);
        add(themeBox);
        add(registerButton);

        registerButton.addActionListener(e -> registerUser());
    }

    private void registerUser() {
        MongoCollection<Document> users =
                MongoDBConnection.getDatabase().getCollection("users");

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String theme = themeBox.getSelectedItem().toString();

        // Provjera praznih polja
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and Password cannot be empty!");
            return;
        }

        // VALIDACIJA PASSWORDA
        if (!utils.PasswordValidator.isValid(password)) {
            JOptionPane.showMessageDialog(this,
                    "Password must contain:\n" +
                            "- at least 8 characters\n" +
                            "- at least 1 uppercase letter\n" +
                            "- at least 1 lowercase letter\n" +
                            "- at least 1 number");
            return;
        }

        // Provjera da li username već postoji
        if (users.find(Filters.eq("username", username)).first() != null) {
            JOptionPane.showMessageDialog(this, "Username already exists!");
            return;
        }

        // Spremanje korisnika u MongoDB
        Document userDoc = new Document("username", username)
                .append("password", password)
                .append("theme", theme);
        users.insertOne(userDoc);

        // Kreiranje User objekta
        User userObj = new User(username, password, theme);

        // Otvaranje Main Menu
        dispose();
        new mainmenu.MainMenuFrame(userObj).setVisible(true);
    }
}
