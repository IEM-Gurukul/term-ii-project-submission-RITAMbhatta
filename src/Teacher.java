// =============================================
//  TEACHER - Teacher User Class (Inheritance)
// =============================================
public class Teacher extends User {
    public Teacher(int id, String name, String email, String password) {
        super(id, name, email, password, "teacher");
    }
}
