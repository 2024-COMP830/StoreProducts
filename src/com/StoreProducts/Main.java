package StoreProductsTest;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main {

    public static Color backgroundColor = Color.decode("#D1E0E0");
    public static Color foregroundColor = Color.decode("#e7f9f9");
    public static Color highlightColor = Color.decode("#fe947f");

    public static void main(String[] args) {
    	DBConnection.initializeDatabase();
        new Login();
    }

    public static JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Helvetica", Font.BOLD, 17));
        return label;
    }

    public static JTextField textField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Helvetica", Font.BOLD, 17));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        return textField;
    }

    public static JButton button(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Helvetica", Font.BOLD, 17));
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setBackground(highlightColor);
        btn.setBorder(null);
        return btn;
    }
}
