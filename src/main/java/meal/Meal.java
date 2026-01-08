package meal;

import java.time.LocalDate;

public class Meal {
    private String name;
    private int calories;
    private LocalDate date;

    public Meal(String name, int calories, LocalDate date) {
        this.name = name;
        this.calories = calories;
        this.date = date;
    }

    public String getName() { return name; }
    public int getCalories() { return calories; }
    public LocalDate getDate() { return date; }
}
