package StoreProductsTest;

import javax.swing.*;
import java.awt.*;

public class Admin extends Account {

    private final ItemDAO itemDAO;

    public Admin(String name, long phonenumber, String email, String password) {
        super(name, phonenumber, email, password);
        itemDAO = new ItemDAOImpl();
    }

    @Override
    public void menu() {
        JFrame frame = new JFrame("Admin Menu");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 1, 20, 20));

        JButton showMenu = Main.button("Show Menu");
        showMenu.addActionListener(e -> {
            StringBuilder menuItems = new StringBuilder();
            itemDAO.getAllItems().forEach(item -> menuItems.append(item.getItemName())
                    .append(" - ").append(item.getPrice()).append(" $\n"));
            JOptionPane.showMessageDialog(null, menuItems.toString().isEmpty() ? "No items in the menu!" : menuItems);
        });
        panel.add(showMenu);

        JButton addItem = Main.button("Add New Item");
        addItem.addActionListener(e -> new newItem());
        panel.add(addItem);

        JButton editItem = Main.button("Edit Item");
        editItem.addActionListener(e -> new editItem());
        panel.add(editItem);

        JButton deleteItem = Main.button("Delete Item");
        deleteItem.addActionListener(e -> new delItem());
        panel.add(deleteItem);

        JButton logout = Main.button("Logout");
        logout.addActionListener(e -> {
            new Login();
            frame.dispose();
        });
        panel.add(logout);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
