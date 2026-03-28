// =============================================
//  COURSE - Course Model (Encapsulation)
// =============================================
public class Course {
    private final int id;
    private final String courseName, teacherName, description;

    public Course(int id, String courseName, String teacherName, String description) {
        this.id = id;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getDescription() {
        return description;
    }
}
