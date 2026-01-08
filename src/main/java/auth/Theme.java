package auth;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Theme {

    public static Map<String, Color> themes = new HashMap<>();

    static {
        themes.put("green", new Color(144, 238, 144));
        themes.put("blue", new Color(173, 216, 230));
        themes.put("pink", new Color(255, 182, 193));
        themes.put("orange", new Color(255, 165, 0));
        themes.put("dark", Color.DARK_GRAY);
        themes.put("cyberpunk", new Color(255, 20, 147));
    }

    public static Color getColor(String themeName) {
        return themes.getOrDefault(themeName, Color.LIGHT_GRAY);
    }
}
