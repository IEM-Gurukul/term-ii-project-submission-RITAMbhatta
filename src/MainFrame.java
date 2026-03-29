import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class MainFrame extends JFrame {
    private final DatabaseManager db;
    private final User            currentUser;
    private final JPanel          contentPanel;

    public MainFrame(DatabaseManager db, User currentUser) {
        this.db = db;
        this.currentUser = currentUser;
        setTitle("Course Management System");
        setSize(960, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(43, 43, 43));
        add(contentPanel, BorderLayout.CENTER);
        showDashboard();
        setVisible(true);
    }

    // ========== Sidebar Navigation ==========
    private JPanel buildSidebar() {
        JPanel side = new JPanel();
        side.setBackground(new Color(49, 51, 56));
        side.setPreferredSize(new Dimension(210, 620));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(BorderFactory.createEmptyBorder(20, 12, 20, 12));

        // Logo
        JLabel logo = new JLabel("  CMS Portal");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(logo);
        side.add(Box.createVerticalStrut(16));

        // User badge
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        badge.setBackground(new Color(60, 63, 65));
        badge.setMaximumSize(new Dimension(186, 54));
        badge.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 100)));
        
        JLabel avatar = new JLabel(String.valueOf(currentUser.getName().charAt(0)));
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        avatar.setForeground(Color.WHITE);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(108, 99, 255));
        avatar.setPreferredSize(new Dimension(34, 34));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        
        JLabel name = new JLabel(currentUser.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 12));
        name.setForeground(Color.WHITE);
        
        JLabel role = new JLabel(currentUser.getRole());
        role.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        role.setForeground(new Color(140, 140, 160));
        
        info.add(name);
        info.add(role);
        badge.add(avatar);
        badge.add(info);
        badge.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(badge);
        side.add(Box.createVerticalStrut(20));

        // Navigation menu based on user role
        if (currentUser.getRole().equals("admin")) {
            side.add(UIUtils.navBtn("  Dashboard", this::showDashboard));
            side.add(UIUtils.navBtn("  Manage Users", this::showUsers));
            side.add(UIUtils.navBtn("  Courses", this::showCourses));
            side.add(UIUtils.navBtn("  Assignments", this::showAssignments));
        } else if (currentUser.getRole().equals("teacher")) {
            side.add(UIUtils.navBtn("  My Courses", this::showCourses));
            side.add(UIUtils.navBtn("  Assignments", this::showAssignments));
        } else {
            side.add(UIUtils.navBtn("  All Courses", this::showCourses));
            side.add(UIUtils.navBtn("  Assignments", this::showAssignments));
        }

        side.add(Box.createVerticalGlue());

        // Logout button
        JButton logoutBtn = new JButton("  Logout");
        logoutBtn.setBackground(new Color(60, 30, 30));
        logoutBtn.setForeground(new Color(248, 113, 113));
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
        logoutBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutBtn.setMaximumSize(new Dimension(186, 36));
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame(db);
        });
        side.add(logoutBtn);
        return side;
    }

    // ========== Dashboard Page ==========
    private void showDashboard() {
        contentPanel.removeAll();
        JPanel page = UIUtils.basePage("Dashboard", "System overview");

        // Statistics cards
        JPanel stats = new JPanel(new GridLayout(1, 4, 12, 0));
        stats.setOpaque(false);
        stats.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));
        
        int studentCount = 0;
        for (User u : db.getUsers()) {
            if (u.getRole().equals("student")) studentCount++;
        }
        
        stats.add(UIUtils.statCard("Total Users", String.valueOf(db.getUsers().size()), new Color(167, 139, 250)));
        stats.add(UIUtils.statCard("Courses", String.valueOf(db.getCourses().size()), new Color(96, 165, 250)));
        stats.add(UIUtils.statCard("Assignments", String.valueOf(db.getAssignments().size()), new Color(251, 191, 36)));
        stats.add(UIUtils.statCard("Students", String.valueOf(studentCount), new Color(52, 211, 153)));
        page.add(stats, BorderLayout.CENTER);

        // Users table
        String[] cols = {"ID", "Name", "Email", "Role"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (User u : db.getUsers()) {
            model.addRow(new Object[]{u.getId(), u.getName(), u.getEmail(), u.getRole()});
        }
        page.add(new JScrollPane(UIUtils.styledTable(model)), BorderLayout.SOUTH);
        refresh(page);
    }

    // ========== User Management Page ==========
    private void showUsers() {
        contentPanel.removeAll();
        JPanel page = UIUtils.basePage("Manage Users", "Add and view all users");

        // Form for adding users
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        form.setBackground(new Color(49, 51, 56));
        
        JTextField nameField = UIUtils.hintField("Full Name");
        JTextField emailField = UIUtils.hintField("Email");
        JTextField passField = UIUtils.hintField("Password");
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"student", "teacher", "admin"});
        roleCombo.setBackground(new Color(60, 63, 65));
        roleCombo.setForeground(Color.WHITE);
        
        JButton addBtn = UIUtils.accentBtn("Add User");
        form.add(nameField);
        form.add(emailField);
        form.add(passField);
        form.add(roleCombo);
        form.add(addBtn);

        // Users table
        String[] cols = {"ID", "Name", "Email", "Role"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (User u : db.getUsers()) {
            model.addRow(new Object[]{u.getId(), u.getName(), u.getEmail(), u.getRole()});
        }

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passField.getText().trim();
            String role = (String) roleCombo.getSelectedItem();
            
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }
            
            User user = role.equals("admin") ? new Admin(db.nextUserId(), name, email, password)
                      : role.equals("teacher") ? new Teacher(db.nextUserId(), name, email, password)
                      : new Student(db.nextUserId(), name, email, password);
            
            db.addUser(user);
            model.addRow(new Object[]{user.getId(), user.getName(), user.getEmail(), user.getRole()});
            nameField.setText("Full Name");
            emailField.setText("Email");
            passField.setText("Password");
            JOptionPane.showMessageDialog(this, "User '" + name + "' added!");
        });

        page.add(form, BorderLayout.CENTER);
        page.add(new JScrollPane(UIUtils.styledTable(model)), BorderLayout.SOUTH);
        refresh(page);
    }

    // ========== Courses Page ==========
    private void showCourses() {
        contentPanel.removeAll();
        JPanel page = UIUtils.basePage("Courses", "View and manage courses");

        String[] cols = {"ID", "Course Name", "Teacher", "Description"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (Course c : db.getCourses()) {
            model.addRow(new Object[]{c.getId(), c.getCourseName(), c.getTeacherName(), c.getDescription()});
        }

        // Admin can add courses
        if (currentUser.getRole().equals("admin")) {
            JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            form.setBackground(new Color(49, 51, 56));
            
            JTextField nameField = UIUtils.hintField("Course Name");
            JTextField teacherField = UIUtils.hintField("Teacher Name");
            JTextField descField = UIUtils.hintField("Description");
            JButton addBtn = UIUtils.accentBtn("Add Course");
            
            form.add(nameField);
            form.add(teacherField);
            form.add(descField);
            form.add(addBtn);
            
            addBtn.addActionListener(e -> {
                String name = nameField.getText().trim();
                String teacher = teacherField.getText().trim();
                String description = descField.getText().trim();
                
                if (name.isEmpty() || teacher.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fill course name and teacher!");
                    return;
                }
                
                Course course = new Course(db.nextCourseId(), name, teacher, 
                    description.isEmpty() ? "No description" : description);
                db.addCourse(course);
                model.addRow(new Object[]{course.getId(), course.getCourseName(), 
                    course.getTeacherName(), course.getDescription()});
                nameField.setText("Course Name");
                teacherField.setText("Teacher Name");
                descField.setText("Description");
            });
            
            page.add(form, BorderLayout.CENTER);
        }

        page.add(new JScrollPane(UIUtils.styledTable(model)), BorderLayout.SOUTH);
        refresh(page);
    }

    // ========== Assignments Page ==========
    private void showAssignments() {
        contentPanel.removeAll();
        JPanel page = UIUtils.basePage("Assignments", "View and post assignments");

        String[] cols = {"ID", "Title", "Course", "Due Date", "Description"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (Assignment a : db.getAssignments()) {
            String courseName = "?";
            for (Course c : db.getCourses()) {
                if (c.getId() == a.getCourseId()) {
                    courseName = c.getCourseName();
                    break;
                }
            }
            model.addRow(new Object[]{a.getId(), a.getTitle(), courseName, a.getDueDate(), a.getDescription()});
        }

        // Teachers and admins can add assignments
        if (!currentUser.getRole().equals("student")) {
            JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            form.setBackground(new Color(49, 51, 56));
            
            JTextField titleField = UIUtils.hintField("Assignment Title");
            JTextField dueField = UIUtils.hintField("Due Date e.g. 30-Mar-2026");
            JTextField descField = UIUtils.hintField("Description");
            
            String[] courseNames = new String[db.getCourses().size()];
            for (int i = 0; i < db.getCourses().size(); i++) {
                courseNames[i] = db.getCourses().get(i).getCourseName();
            }
            JComboBox<String> courseCombo = new JComboBox<>(courseNames);
            courseCombo.setBackground(new Color(60, 63, 65));
            courseCombo.setForeground(Color.WHITE);
            
            JButton addBtn = UIUtils.accentBtn("Post Assignment");
            form.add(titleField);
            form.add(dueField);
            form.add(descField);
            form.add(courseCombo);
            form.add(addBtn);
            
            addBtn.addActionListener(e -> {
                String title = titleField.getText().trim();
                String dueDate = dueField.getText().trim();
                String description = descField.getText().trim();
                
                if (title.isEmpty() || dueDate.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fill title and due date!");
                    return;
                }
                
                Course course = db.getCourses().get(courseCombo.getSelectedIndex());
                Assignment assignment = new Assignment(db.nextAssignmentId(), course.getId(), title, dueDate,
                    description.isEmpty() ? "No description" : description);
                db.addAssignment(assignment);
                model.addRow(new Object[]{assignment.getId(), assignment.getTitle(), course.getCourseName(),
                    assignment.getDueDate(), assignment.getDescription()});
                titleField.setText("Assignment Title");
                dueField.setText("Due Date e.g. 30-Mar-2026");
                descField.setText("Description");
            });
            
            page.add(form, BorderLayout.CENTER);
        }

        page.add(new JScrollPane(UIUtils.styledTable(model)), BorderLayout.SOUTH);
        refresh(page);
    }

    // ========== Helper Methods ==========
    private void refresh(JPanel panel) {
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
