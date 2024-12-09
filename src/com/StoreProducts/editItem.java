package StoreProductsTest;

import javax.swing.*;
import java.awt.*;

public class editItem {

    private final ItemDAO itemDAO;

    public editItem(Item item) {
        itemDAO = new ItemDAOImpl();

        JFrame frame = new JFrame("Edit Item");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));

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

        JButton saveButton = Main.button("Save");
        saveButton.addActionListener(e -> {
            try {
                String newName = nameField.getText();
                double newPrice = Double.parseDouble(priceField.getText());

                itemDAO.editItem(item.getItemId(), newName, newPrice);
                JOptionPane.showMessageDialog(frame, "Item updated successfully!");
                frame.dispose();
                new MenuPage(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(saveButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
