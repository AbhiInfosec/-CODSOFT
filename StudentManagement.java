import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ─── Student Model ───────────────────────────────────────────────────────────
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int rollNumber;
    private String name;
    private String email;
    private String phone;
    private Map<String, Double> subjectMarks;
    private String enrollmentDate;
    private String status; // Active / Inactive
    
    public Student(int roll, String name, String email, String phone) {
        this.rollNumber = roll;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subjectMarks = new LinkedHashMap<>();
        this.enrollmentDate = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.status = "Active";
    }
    
    // Getters
    public int getRoll()     { return rollNumber; }
    public String getName()  { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getStatus(){ return status; }
    public String getDate()  { return enrollmentDate; }
    public Map<String, Double> getMarks() { return subjectMarks; }
    
    // Setters
    public void setName(String n)   { this.name = n; }
    public void setEmail(String e)  { this.email = e; }
    public void setPhone(String p)  { this.phone = p; }
    public void setStatus(String s) { this.status = s; }
    public void addMark(String subject, double mark) { subjectMarks.put(subject, mark); }
    
    public double getAverage() {
        if (subjectMarks.isEmpty()) return 0;
        return subjectMarks.values().stream()
            .mapToDouble(Double::doubleValue).average().orElse(0);
    }
    
    public double getTotalMarks() {
        return subjectMarks.values().stream().mapToDouble(Double::doubleValue).sum();
    }
    
    public String getGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A+";
        else if (avg >= 80) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 60) return "C";
        else if (avg >= 50) return "D";
        else if (avg >= 40) return "E";
        else return "F";
    }
    
    public String getGradeEmoji() {
        switch (getGrade()) {
            case "A+": return "🏆";
            case "A":  return "⭐";
            case "B":  return "👍";
            case "C":  return "✅";
            case "D":  return "📚";
            default:   return "❌";
        }
    }
    
    public void displayCard() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║              STUDENT PROFILE CARD           ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf ("║  Roll No  : %-33d║%n", rollNumber);
        System.out.printf ("║  Name     : %-33s║%n", name);
        System.out.printf ("║  Email    : %-33s║%n", email);
        System.out.printf ("║  Phone    : %-33s║%n", phone);
        System.out.printf ("║  Enrolled : %-33s║%n", enrollmentDate);
        System.out.printf ("║  Status   : %-33s║%n", status);
        System.out.println("╠══════════════════════════════════════════════╣");
        if (!subjectMarks.isEmpty()) {
            System.out.println("║              SUBJECT MARKS                   ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            for (Map.Entry<String, Double> e : subjectMarks.entrySet()) {
                System.out.printf("║  %-20s : %-10.1f/100           ║%n", e.getKey(), e.getValue());
            }
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.printf ("║  Total Marks  : %-28.1f║%n", getTotalMarks());
            System.out.printf ("║  Average      : %-28.2f║%n", getAverage());
            System.out.printf ("║  Grade        : %-2s %-1s                             ║%n", getGrade(), getGradeEmoji());
        } else {
            System.out.println("║  No marks added yet                          ║");
        }
        System.out.println("╚══════════════════════════════════════════════╝");
    }
}

// ─── Student Management System ───────────────────────────────────────────────
class SMS {
    private List<Student> students = new ArrayList<>();
    private static final String FILE = "students_data.dat";
    private int nextRoll = 1001;
    
    public SMS() { load(); }
    
    public boolean add(Student s) {
        if (findByRoll(s.getRoll()) != null) return false;
        students.add(s);
        save();
        return true;
    }
    
    public boolean remove(int roll) {
        Student s = findByRoll(roll);
        if (s == null) return false;
        students.remove(s);
        save();
        return true;
    }
    
    public Student findByRoll(int roll) {
        return students.stream().filter(s -> s.getRoll() == roll).findFirst().orElse(null);
    }
    
    public List<Student> findByName(String name) {
        List<Student> res = new ArrayList<>();
        for (Student s : students)
            if (s.getName().toLowerCase().contains(name.toLowerCase())) res.add(s);
        return res;
    }
    
    public List<Student> getAll() { return students; }
    public int count()            { return students.size(); }
    public int getNextRoll()      { return nextRoll++; }
    
    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("📭 No students in the system!");
            return;
        }
        System.out.println("\n╔══════╦══════════════════════╦═══════╦════════╦════════╗");
        System.out.println("║ Roll ║ Name                 ║ Grade ║  Avg % ║ Status ║");
        System.out.println("╠══════╬══════════════════════╬═══════╬════════╬════════╣");
        for (Student s : students) {
            System.out.printf("║ %-4d ║ %-20s ║ %-5s ║ %5.1f%% ║ %-6s ║%n",
                s.getRoll(), s.getName(), s.getGrade() + " " + s.getGradeEmoji(),
                s.getAverage(), s.getStatus());
        }
        System.out.println("╚══════╩══════════════════════╩═══════╩════════╩════════╝");
        System.out.println("  Total Students: " + students.size());
    }
    
    public void showStats() {
        if (students.isEmpty()) { System.out.println("📭 No data!"); return; }
        double avgAll = students.stream().mapToDouble(Student::getAverage).average().orElse(0);
        Student top = students.stream().max(Comparator.comparingDouble(Student::getAverage)).orElse(null);
        Student low = students.stream().min(Comparator.comparingDouble(Student::getAverage)).orElse(null);
        long passing = students.stream().filter(s -> s.getAverage() >= 40).count();
        long failing = students.size() - passing;
        
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║            CLASS STATISTICS 📊              ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf ("║  Total Students  : %-25d║%n", students.size());
        System.out.printf ("║  Class Average   : %-24.2f%%║%n", avgAll);
        System.out.printf ("║  Passing Students: %-25d║%n", passing);
        System.out.printf ("║  Failing Students: %-25d║%n", failing);
        System.out.printf ("║  Pass Rate       : %-24.1f%%║%n", (passing * 100.0 / students.size()));
        if (top != null) System.out.printf("║  Top Student     : %-25s║%n", top.getName());
        if (low != null) System.out.printf("║  Needs Attention : %-25s║%n", low.getName());
        System.out.println("╚══════════════════════════════════════════════╝");
    }
    
    @SuppressWarnings("unchecked")
    private void load() {
        File f = new File(FILE);
        if (!f.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
            students = (List<Student>) in.readObject();
        } catch (Exception e) { students = new ArrayList<>(); }
    }
    
    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(students);
        } catch (IOException e) { System.out.println("⚠️ Save failed!"); }
    }
}

// ─── Main Application ─────────────────────────────────────────────────────────
public class StudentManagement {
    static Scanner sc = new Scanner(System.in);
    static SMS sms = new SMS();
    
    public static void main(String[] args) {
        showBanner();
        
        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Choose option (1-8): ");
            switch (sc.nextLine().trim()) {
                case "1": addStudent();    break;
                case "2": viewStudent();   break;
                case "3": updateStudent(); break;
                case "4": removeStudent(); break;
                case "5": searchStudent(); break;
                case "6": sms.displayAll(); break;
                case "7": sms.showStats(); break;
                case "8":
                    System.out.println("\n✅ All data saved! Goodbye! 👋");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option! Please choose 1-8.");
            }
        }
        sc.close();
    }
    
    static void showBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║      STUDENT MANAGEMENT SYSTEM v2.0 🎓      ║");
        System.out.println("║      CodSoft Java Internship Task 5         ║");
        System.out.println("║    Developed by: Abhishek Mishra            ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("📅 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        System.out.println("📚 Students in system: " + sms.count() + "\n");
    }
    
    static void showMenu() {
        System.out.println("\n──────────────────────────────────────");
        System.out.println("              MAIN MENU");
        System.out.println("──────────────────────────────────────");
        System.out.println("1. ➕ Add New Student");
        System.out.println("2. 👤 View Student Profile");
        System.out.println("3. ✏️  Update Student Info");
        System.out.println("4. 🗑️  Remove Student");
        System.out.println("5. 🔍 Search Students");
        System.out.println("6. 📋 Display All Students");
        System.out.println("7. 📊 Class Statistics");
        System.out.println("8. 🚪 Exit & Save");
        System.out.println("──────────────────────────────────────");
    }
    
    static void addStudent() {
        System.out.println("\n➕ ADD NEW STUDENT");
        System.out.println("──────────────────────────────────────");
        
        int roll = sms.getNextRoll();
        System.out.println("Auto-assigned Roll Number: " + roll);
        
        System.out.print("Full Name  : ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("❌ Name required!"); return; }
        
        System.out.print("Email      : ");
        String email = sc.nextLine().trim();
        
        System.out.print("Phone      : ");
        String phone = sc.nextLine().trim();
        
        Student s = new Student(roll, name, email, phone);
        
        // Add marks
        System.out.print("\nAdd subject marks? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
            System.out.print("How many subjects? ");
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                for (int i = 0; i < n; i++) {
                    System.out.print("Subject " + (i+1) + " name : ");
                    String sub = sc.nextLine().trim();
                    System.out.print("Marks (0-100)    : ");
                    try {
                        double marks = Double.parseDouble(sc.nextLine().trim());
                        if (marks >= 0 && marks <= 100) s.addMark(sub, marks);
                        else System.out.println("⚠️ Invalid marks! Skipped.");
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid input! Skipped.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number of subjects!");
            }
        }
        
        if (sms.add(s)) {
            System.out.println("\n✅ Student added successfully!");
            s.displayCard();
        } else {
            System.out.println("❌ Error adding student!");
        }
    }
    
    static void viewStudent() {
        System.out.print("\n👤 Enter roll number: ");
        try {
            int roll = Integer.parseInt(sc.nextLine().trim());
            Student s = sms.findByRoll(roll);
            if (s != null) s.displayCard();
            else System.out.println("❌ Student not found!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid roll number!");
        }
    }
    
    static void updateStudent() {
        System.out.print("\n✏️  Enter roll number to update: ");
        try {
            int roll = Integer.parseInt(sc.nextLine().trim());
            Student s = sms.findByRoll(roll);
            if (s == null) { System.out.println("❌ Student not found!"); return; }
            
            System.out.println("Editing: " + s.getName() + " (Press Enter to keep current value)");
            
            System.out.print("Name  [" + s.getName() + "]: ");
            String name = sc.nextLine().trim();
            if (!name.isEmpty()) s.setName(name);
            
            System.out.print("Email [" + s.getEmail() + "]: ");
            String email = sc.nextLine().trim();
            if (!email.isEmpty()) s.setEmail(email);
            
            System.out.print("Phone [" + s.getPhone() + "]: ");
            String phone = sc.nextLine().trim();
            if (!phone.isEmpty()) s.setPhone(phone);
            
            System.out.print("Add/Update marks? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                System.out.print("Subject name: ");
                String sub = sc.nextLine().trim();
                System.out.print("Marks (0-100): ");
                try {
                    double marks = Double.parseDouble(sc.nextLine().trim());
                    if (marks >= 0 && marks <= 100) s.addMark(sub, marks);
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid marks!");
                }
            }
            
            System.out.println("✅ Student updated successfully!");
            s.displayCard();
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid roll number!");
        }
    }
    
    static void removeStudent() {
        System.out.print("\n🗑️  Enter roll number to remove: ");
        try {
            int roll = Integer.parseInt(sc.nextLine().trim());
            Student s = sms.findByRoll(roll);
            if (s == null) { System.out.println("❌ Student not found!"); return; }
            
            System.out.println("Are you sure you want to remove: " + s.getName() + "?");
            System.out.print("Type 'CONFIRM' to proceed: ");
            if (sc.nextLine().trim().equals("CONFIRM")) {
                sms.remove(roll);
                System.out.println("✅ Student removed successfully!");
            } else {
                System.out.println("❌ Removal cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid roll number!");
        }
    }
    
    static void searchStudent() {
        System.out.println("\n🔍 SEARCH STUDENT");
        System.out.println("1. By Roll Number");
        System.out.println("2. By Name");
        System.out.print("Choose (1/2): ");
        
        String choice = sc.nextLine().trim();
        if (choice.equals("1")) {
            System.out.print("Roll Number: ");
            try {
                int roll = Integer.parseInt(sc.nextLine().trim());
                Student s = sms.findByRoll(roll);
                if (s != null) s.displayCard();
                else System.out.println("❌ No student found with roll: " + roll);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid roll number!");
            }
        } else if (choice.equals("2")) {
            System.out.print("Name (partial ok): ");
            String name = sc.nextLine().trim();
            List<Student> results = sms.findByName(name);
            if (results.isEmpty()) {
                System.out.println("❌ No students found with name: " + name);
            } else {
                System.out.println("✅ Found " + results.size() + " student(s):");
                results.forEach(Student::displayCard);
            }
        }
    }
}
