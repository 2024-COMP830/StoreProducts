package StoreProductsTest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class editItem {

    private final ItemDAO itemDAO;

    public editItem(Item item, List<Item> cart) {
        itemDAO = new ItemDAOImpl();

        JFrame frame = new JFrame("Edit Item");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        formPanel.setBackground(Main.foregroundColor);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel idLabel = Main.label("Item ID:");
        JTextField idField = Main.textField();
        idField.setText(String.valueOf(item.getItemId()));
        idField.setEditable(false);

        JLabel nameLabel = Main.label("Item Name:");
        JTextField nameField = Main.textField();
        nameField.setText(item.getItemName());

        JLabel priceLabel = Main.label("Price:");
        JTextField priceField = Main.textField();
        priceField.setText(String.valueOf(item.getPrice()));

        formPanel.add(idLabel);
        formPanel.add(idField);
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
            try {
                String newName = nameField.getText();
                double newPrice = Double.parseDouble(priceField.getText());

                itemDAO.editItem(item.getItemId(), newName, newPrice);
                JOptionPane.showMessageDialog(frame, "Item updated successfully!");
                frame.dispose();
                new MenuPage(true, cart); // Return to the menu page for admin
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
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

        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
