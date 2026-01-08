package auth;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import financeapp.MongoDBConnection;
import mainmenu.MainMenuFrame;
import org.bson.Document;

import javax.swing.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        usernameField.setBounds(80, 20, 150, 25);
        passwordField.setBounds(80, 60, 150, 25);
        loginButton.setBounds(30, 110, 100, 30);
        registerButton.setBounds(150, 110, 100, 30);

        add(new JLabel("Username:")).setBounds(10, 20, 70, 25);
        add(new JLabel("Password:")).setBounds(10, 60, 70, 25);

        add(usernameField);
        add(passwordField);
        add(loginButton);
        add(registerButton);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterFrame().setVisible(true);
        });
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if(username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password");
            return;
        }

        MongoCollection<Document> usersCollection =
                MongoDBConnection.getDatabase().getCollection("users");

        Document userDoc = usersCollection.find(Filters.eq("username", username)).first();

        if(userDoc != null && password.equals(userDoc.getString("password"))) {
            // Create a User object
            User user = new User(
                    userDoc.getString("username"),
                    userDoc.getString("password"),
                    userDoc.getString("theme")
            );

            // Open MainMenuFrame with User object
            dispose();
            new MainMenuFrame(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }
}
