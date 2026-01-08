package study;

import java.time.LocalDate;

public class StudyTask {
    private String subject;
    private String task;
    private boolean completed;
    private LocalDate date;

    public StudyTask(String subject, String task, boolean completed, LocalDate date) {
        this.subject = subject;
        this.task = task;
        this.completed = completed;
        this.date = date;
    }

    public String getSubject() { return subject; }
    public String getTask() { return task; }
    public boolean isCompleted() { return completed; }
    public LocalDate getDate() { return date; }
}
