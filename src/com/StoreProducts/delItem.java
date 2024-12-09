package StoreProductsTest;

import javax.swing.*;
import java.awt.*;

public class delItem {

    private final ItemDAO itemDAO;

    public delItem() {
        itemDAO = new ItemDAOImpl();

        JFrame frame = new JFrame("Delete Item");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));

        JLabel idLabel = Main.label("Item ID:");
        JTextField idField = Main.textField();

        JButton deleteButton = Main.button("Delete");
        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                itemDAO.deleteItem(id);
                JOptionPane.showMessageDialog(null, "Item deleted successfully!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid item ID!");
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(deleteButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
