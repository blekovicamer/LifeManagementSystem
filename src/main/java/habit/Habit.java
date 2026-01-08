package habit;

import java.time.LocalDate;

public class Habit {
    private String name;
    private boolean completed;
    private LocalDate date;

    public Habit(String name, boolean completed, LocalDate date) {
        this.name = name;
        this.completed = completed;
        this.date = date;
    }

    public String getName() { return name; }
    public boolean isCompleted() { return completed; }
    public LocalDate getDate() { return date; }
}
