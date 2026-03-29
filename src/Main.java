// =============================================
//  MAIN - Application Entry Point
// =============================================
public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        LoginFrame loginFrame = new LoginFrame(db);
        loginFrame.setVisible(true);
    }
}
