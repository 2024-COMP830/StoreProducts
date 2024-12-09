package StoreProductsTest;

import javax.swing.*;
import java.awt.*;

public class newItem {

    private final ItemDAO itemDAO;

    public newItem() {
        itemDAO = new ItemDAOImpl();

        JFrame frame = new JFrame("Add New Item");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));

        JLabel nameLabel = Main.label("Item Name:");
        JTextField nameField = Main.textField();
        JLabel priceLabel = Main.label("Price:");
        JTextField priceField = Main.textField();

        JButton saveButton = Main.button("Save");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String price = priceField.getText();

            try {
                double priceValue = Double.parseDouble(price);
                Item item = new Item(0, name, priceValue, "default.png");
                itemDAO.addItem(item);
                JOptionPane.showMessageDialog(null, "Item added successfully!");
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid price!");
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(saveButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
