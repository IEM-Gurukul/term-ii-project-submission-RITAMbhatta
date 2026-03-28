// =============================================
//  ASSIGNMENT - Assignment Model (Encapsulation)
// =============================================
public class Assignment {
    private final int id, courseId;
    private final String title, dueDate, description;

    public Assignment(int id, int courseId, String title, String dueDate, String description) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }
}
