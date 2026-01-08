package mood;

import java.time.LocalDate;

public class MoodEntry {
    private String mood;
    private String note;
    private LocalDate date;

    public MoodEntry(String mood, String note, LocalDate date) {
        this.mood = mood;
        this.note = note;
        this.date = date;
    }

    public String getMood() { return mood; }
    public String getNote() { return note; }
    public LocalDate getDate() { return date; }
}
