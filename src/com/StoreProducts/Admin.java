package StoreProductsTest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Admin extends Account {

    private final ItemDAO itemDAO;

    public Admin(String name, long phonenumber, String email, String password) {
        super(name, phonenumber, email, password);
        itemDAO = new ItemDAOImpl();
    }

    @Override
    public void menu() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 1, 20, 20));

        JButton showMenuButton = Main.button("Show Menu");
        showMenuButton.addActionListener(e -> {
            frame.dispose();
            new MenuPage(true, new ArrayList<>()); // Admin does not use the cart
        });

        JButton addItemButton = Main.button("Add New Item");
        addItemButton.addActionListener(e -> {
            frame.dispose();
            new newItem(new ArrayList<>()); // Pass an empty cart for admin
        });

        JButton logoutButton = Main.button("Logout");
        logoutButton.addActionListener(e -> {
            new Login();
            frame.dispose();
        });

        panel.add(showMenuButton);
        panel.add(addItemButton);
        panel.add(logoutButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
