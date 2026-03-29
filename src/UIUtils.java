import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// =============================================
//  UI UTILITIES - Styling & Component Helpers
// =============================================
public class UIUtils {

    // ========== Styling Methods ==========
    
    /**
     * Creates a styled label with custom font and color
     */
    public static JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(140, 140, 160));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return label;
    }

    /**
     * Styles input fields (text fields and password fields) with consistent appearance
     */
    public static void styleInput(JTextField field) {
        field.setBackground(new Color(49, 51, 56));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 100)),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    /**
     * Creates a text field with hint placeholder text
     */
    public static JTextField hintField(String hint) {
        JTextField field = new JTextField(14);
        field.setText(hint);
        field.setForeground(new Color(120, 120, 140));
        field.setBackground(new Color(60, 63, 65));
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 100)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(hint)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(hint);
                    field.setForeground(new Color(120, 120, 140));
                }
            }
        });
        return field;
    }

    /**
     * Creates a styled accent button
     */
    public static JButton accentBtn(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(108, 99, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Creates a styled navigation button with hover effects
     */
    public static JButton navBtn(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setForeground(new Color(180, 180, 200));
        button.setBackground(new Color(49, 51, 56));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(186, 36));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> action.run());
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(108, 99, 255));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(49, 51, 56));
                button.setForeground(new Color(180, 180, 200));
            }
        });
        return button;
    }

    /**
     * Styles a table with consistent appearance
     */
    public static JTable styledTable(javax.swing.table.DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(new Color(49, 51, 56));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(70, 70, 80));
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(108, 99, 255, 80));
        table.getTableHeader().setBackground(new Color(60, 63, 65));
        table.getTableHeader().setForeground(new Color(140, 140, 160));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        return table;
    }

    /**
     * Creates a styled statistics card
     */
    public static JPanel statCard(String label, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(49, 51, 56));
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setForeground(new Color(140, 140, 160));
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setForeground(color);
        valueComponent.setFont(new Font("Segoe UI", Font.BOLD, 30));
        
        card.add(labelComponent, BorderLayout.NORTH);
        card.add(valueComponent, BorderLayout.CENTER);
        return card;
    }

    /**
     * Creates a base page panel with title and subtitle
     */
    public static JPanel basePage(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(140, 140, 160));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        return panel;
    }
}
