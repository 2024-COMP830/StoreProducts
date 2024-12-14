package StoreProductsTest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CartPage {

    private final List<Item> cart;
    private final Guest guest;

    public CartPage(List<Item> cart, Guest guest) {
        this.cart = cart;
        this.guest = guest;

        JFrame frame = new JFrame("Your Cart");
        frame.getContentPane().setBackground(Main.backgroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        String[] columnNames = {"Item Name", "Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        cart.forEach(item -> model.addRow(new Object[]{item.getItemName(), item.getPrice()}));

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton checkoutButton = Main.button("Checkout");
        JButton backButton = Main.button("Back to Menu");

        checkoutButton.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Cart is empty!");
                return;
            }
            StringBuilder summary = new StringBuilder("Order Summary:\n");
            double total = 0;

            for (Item item : cart) {
                summary.append(item.getItemName()).append(" - $").append(item.getPrice()).append("\n");
                total += item.getPrice();
                new ItemDAOImpl().deleteItem(item.getItemId());
            }
            summary.append("\nTotal: $").append(total);
            summary.append("\n\nGuest Name: ").append(guest.getName());

            JOptionPane.showMessageDialog(frame, summary.toString());
            cart.clear();
            frame.dispose();
            new MenuPage(false, cart);
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuPage(false, cart);
        });

        bottomPanel.add(checkoutButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
