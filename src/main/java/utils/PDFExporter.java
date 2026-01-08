package utils;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import habit.HabitTrackerFrame;
import meal.MealPlannerFrame;
import mood.MoodTrackerFrame;
import study.StudyPlannerFrame;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;

public class PDFExporter {


    public static void exportAllTrackers(String theme, String path) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(new File(path));
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Life Management System Report\n\n"));

        // ---------- Habit Tracker ----------
        HabitTrackerFrame habitFrame = new HabitTrackerFrame(theme);
        document.add(new Paragraph("Habit Tracker:"));
        addTableDataToPDF(habitFrame.getModel(), document);

        // ---------- Meal Planner ----------
        MealPlannerFrame mealFrame = new MealPlannerFrame(theme);
        document.add(new Paragraph("\nMeal Planner:"));
        addTableDataToPDF(mealFrame.getModel(), document);

        // ---------- Mood Tracker ----------
        MoodTrackerFrame moodFrame = new MoodTrackerFrame(theme);
        document.add(new Paragraph("\nMood Tracker:"));
        addTableDataToPDF(moodFrame.getModel(), document);

        // ---------- Study Planner ----------
        StudyPlannerFrame studyFrame = new StudyPlannerFrame(theme);
        document.add(new Paragraph("\nStudy Planner:"));
        addTableDataToPDF(studyFrame.getModel(), document);

        document.close();
    }

    private static void addTableDataToPDF(DefaultTableModel model, Document document) {
        for (int i = 0; i < model.getRowCount(); i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < model.getColumnCount(); j++) {
                row.append(model.getColumnName(j))
                        .append(": ")
                        .append(model.getValueAt(i, j))
                        .append("  ");
            }
            document.add(new Paragraph(row.toString()));
        }
    }
}
