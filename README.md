# Life Management System

## Project Description
The Life Management System is a Java desktop application that allows users to track various aspects of their life. The application provides:

- **Habit Tracker** – track habits and mark them as completed
- **Meal Planner** – record meals and calories
- **Mood Tracker** – track moods with optional notes
- **Study Planner** – track study tasks and their completion status
- **Finance Tracker** – manage financial transactions

The application also allows users to **change themes**, **export all tracker data to a PDF report**, and features a **responsive GUI** that adjusts to window size.

---

## Technologies
- Java 17+
- Swing GUI
- MongoDB (data storage)
- iText 7 (PDF generation)
- Maven (dependency management)

---

## Features

1. **Login & User Preferences**
   - Each user can select a theme (`default`, `dark`, `light`)
   - Theme applies to all trackers and main menu

2. **Habit Tracker**
   - Add habits
   - Mark habits as completed
   - Save data to MongoDB

3. **Meal Planner**
   - Add meals with calorie information
   - Display in a table
   - Save data to MongoDB

4. **Mood Tracker**
   - Add moods with optional notes
   - Display in a table
   - Save data to MongoDB

5. **Study Planner**
   - Add subjects and tasks
   - Mark tasks as completed
   - Display in a table
   - Save data to MongoDB

6. **Finance Tracker**
   - Record financial transactions
   - Opens in a separate window

7. **Export PDF**
   - Export all tracker data to a PDF file
   - User can select the save location

8. **Theme Selector**
   - Dropdown in the main menu to change the theme
   - Theme applies immediately

---

## Requirements

- Java 17 or higher
- Maven
- MongoDB server (local or remote)
- iText 7 dependency in Maven

---

## Project Setup

1. **Clone the repository**
```bash
git clone https://github.com/username/LifeManagementSystem.git
cd LifeManagementSystem

Check Maven dependencies
Ensure pom.xml includes:

mongodb-driver-sync

gson

itext7-core (PDF generation)

Start MongoDB server
Make sure MongoDB is running and the database lifemgmt exists (or update in MongoDBConnection.java).

Build and Run

mvn clean compile
mvn exec:java -Dexec.mainClass="auth.LoginFrame"


Or run from your IDE (IntelliJ, Eclipse) by launching auth.LoginFrame

Project Structure
LifeManagementSystem/
│
├── src/main/java/
│   ├── auth/                 # Login and theme management
│   ├── mainmenu/             # Main menu
│   ├── habit/                # Habit Tracker
│   ├── meal/                 # Meal Planner
│   ├── mood/                 # Mood Tracker
│   ├── study/                # Study Planner
│   ├── financeapp/           # Finance Tracker & MongoDB connection
│   └── utils/                # PDF exporter and helpers
│
├── pom.xml                   # Maven configuration
└── README.md

How to Use

Launch LoginFrame and log in or register a new user.

After login, the main menu provides access to all trackers:

Click a button to open a tracker in a new window.

Change theme using the dropdown.

Export PDF to save all tracker data.

Each tracker automatically connects to MongoDB and displays data.

Notes

PDF export generates a snapshot of current data from all trackers.

The GUI is responsive: button sizes adjust to window resizing.

Themes (dark, light, default) change the panel and button colors dynamically.
