import javax.swing.*;
import java.awt.*;

// =============================================
//  LOGIN FRAME - User Authentication UI
// =============================================
public class LoginFrame extends JFrame {
    private final DatabaseManager db;
    private JTextField emailField;
    private JPasswordField passField;
    private JLabel errorLabel;

    public LoginFrame(DatabaseManager db) {
        this.db = db;
        setTitle("CMS Login");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(30, 30, 46));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridwidth = 2;

        // Title
        JLabel title = new JLabel("Course Management System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        g.gridy = 0;
        main.add(title, g);

        // Subtitle
        JLabel sub = new JLabel("Sign in to your account", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(new Color(140, 140, 160));
        g.gridy = 1;
        main.add(sub, g);

        // Email field
        g.gridwidth = 1;
        g.gridy = 2;
        g.gridx = 0;
        main.add(UIUtils.makeLabel("Email"), g);
        g.gridx = 1;
        emailField = new JTextField(15);
        UIUtils.styleInput(emailField);
        main.add(emailField, g);

        // Password field
        g.gridy = 3;
        g.gridx = 0;
        main.add(UIUtils.makeLabel("Password"), g);
        g.gridx = 1;
        passField = new JPasswordField(15);
        UIUtils.styleInput(passField);
        main.add(passField, g);

        // Error message label
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(new Color(248, 113, 113));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g.gridy = 4;
        g.gridx = 0;
        g.gridwidth = 2;
        main.add(errorLabel, g);

        // Login button
        JButton loginBtn = new JButton("Sign In");
        loginBtn.setBackground(new Color(108, 99, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setPreferredSize(new Dimension(200, 38));
        loginBtn.addActionListener(e -> doLogin());
        g.gridy = 5;
        main.add(loginBtn, g);

        // Quick fill hint
        JLabel hint = new JLabel("Quick fill:", SwingConstants.CENTER);
        hint.setForeground(new Color(100, 100, 120));
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        g.gridy = 6;
        main.add(hint, g);

        // Quick fill buttons (Admin, Teacher, Student)
        JPanel hints = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        hints.setBackground(new Color(30, 30, 46));
        String[][] credentials = {
            {"Admin", "admin@cms.com", "admin123"},
            {"Teacher", "teacher@cms.com", "teacher123"},
            {"Student", "student@cms.com", "student123"}
        };
        
        for (String[] cred : credentials) {
            JButton quickBtn = new JButton(cred[0]);
            quickBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            quickBtn.setForeground(new Color(140, 140, 160));
            quickBtn.setBackground(new Color(49, 51, 56));
            quickBtn.setBorderPainted(false);
            quickBtn.setFocusPainted(false);
            quickBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            String email = cred[1];
            String password = cred[2];
            quickBtn.addActionListener(e -> {
                emailField.setText(email);
                passField.setText(password);
            });
            hints.add(quickBtn);
        }
        
        g.gridy = 7;
        main.add(hints, g);
        add(main);
        passField.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword()).trim();
        User user = db.login(email, password);
        
        if (user == null) {
            errorLabel.setText("Invalid email or password!");
        } else {
            dispose();
            new MainFrame(db, user);
        }
    }
}
