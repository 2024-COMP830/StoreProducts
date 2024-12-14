package StoreProductsTest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class newItem {

    private final ItemDAO itemDAO;

    public newItem(List<Item> cart) {
        itemDAO = new ItemDAOImpl();

        JFrame frame = new JFrame("Add New Item");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Main.highlightColor);
        JLabel titleLabel = Main.label("Add New Item");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        formPanel.setBackground(Main.foregroundColor);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel nameLabel = Main.label("Item Name:");
        JTextField nameField = Main.textField();
        JLabel priceLabel = Main.label("Price:");
        JTextField priceField = Main.textField();

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(priceLabel);
        formPanel.add(priceField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Main.foregroundColor);

        JButton saveButton = Main.button("Save");
        JButton cancelButton = Main.button("Cancel");

        saveButton.setBackground(Main.highlightColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String price = priceField.getText();

            try {
                double priceValue = Double.parseDouble(price);
                Item item = new Item(0, name, priceValue, "default.png");
                itemDAO.addItem(item);
                JOptionPane.showMessageDialog(frame, "Item added successfully!");
                frame.dispose();
                new MenuPage(true, cart); // Return to the menu page for admin
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid price!");
            }
        });

        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(e -> {
            frame.dispose();
            new MenuPage(true, cart); // Return to the menu page for admin
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
