package auth;

public class User {
    private String username;
    private String theme;

    public User(String username, String theme) {
        this.username = username;
        this.theme = theme;
    }

    public String getUsername() {
        return username;
    }

    public String getTheme() {
        return theme;
    }
}
