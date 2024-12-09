package StoreProductsTest;

import javax.swing.*;
import java.awt.*;

public class editItem {

    private final ItemDAO itemDAO;

    public editItem() {
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
        JLabel nameLabel = Main.label("New Name:");
        JTextField nameField = Main.textField();
        JLabel priceLabel = Main.label("New Price:");
        JTextField priceField = Main.textField();

        JButton saveButton = Main.button("Save");
        saveButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());

                itemDAO.editItem(id, name, price);
                JOptionPane.showMessageDialog(null, "Item updated successfully!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input!");
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
