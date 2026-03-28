import java.util.ArrayList;

// =============================================
//  DATABASE MANAGER - Data Management
// =============================================
public class DatabaseManager {
    private final ArrayList<User>       users       = new ArrayList<>();
    private final ArrayList<Course>     courses     = new ArrayList<>();
    private final ArrayList<Assignment> assignments = new ArrayList<>();

    public DatabaseManager() {
        // Initialize with sample data
        users.add(new Admin(1, "Admin User", "admin@cms.com", "admin123"));
        users.add(new Teacher(2, "Mr. Kumar", "teacher@cms.com", "teacher123"));
        users.add(new Student(3, "Rahul Sharma", "student@cms.com", "student123"));

        courses.add(new Course(1, "Mathematics", "Mr. Kumar", "Algebra, Geometry"));
        courses.add(new Course(2, "Computer Science", "Mr. Kumar", "Java, OOP, DSA"));
        courses.add(new Course(3, "Physics", "Mr. Kumar", "Mechanics, Waves"));

        assignments.add(new Assignment(1, 1, "Algebra Worksheet", "30-Mar-2026", "Solve 20 problems"));
        assignments.add(new Assignment(2, 2, "Java OOP Project", "31-Mar-2026", "Build CMS in Java"));
        assignments.add(new Assignment(3, 3, "Newton Laws Report", "01-Apr-2026", "Write 1 page report"));
    }

    // ========== User Management ==========
    public User login(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void addUser(User u) {
        users.add(u);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public int nextUserId() {
        return users.size() + 1;
    }

    // ========== Course Management ==========
    public void addCourse(Course c) {
        courses.add(c);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public int nextCourseId() {
        return courses.size() + 1;
    }

    // ========== Assignment Management ==========
    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public int nextAssignmentId() {
        return assignments.size() + 1;
    }
}
