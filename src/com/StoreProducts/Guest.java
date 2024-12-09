package StoreProductsTest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Guest extends Account {

    private final ItemDAO itemDAO;
    private final ArrayList<Item> cart;

    public Guest(String name, long phonenumber, String email, String password) {
        super(name, phonenumber, email, password);
        itemDAO = new ItemDAOImpl();
        cart = new ArrayList<>();
    }

    @Override
    public void menu() {
        JFrame frame = new JFrame("Store Management System");
        frame.getContentPane().setBackground(Main.backgroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 5, 3, 3));
        panel.setBackground(null);

        itemDAO.getAllItems().forEach(item -> {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(Main.foregroundColor);

            JLabel itemName = new JLabel(item.getItemName() + " - $" + item.getPrice());
            JButton addToCart = Main.button("Add to Cart");

            addToCart.addActionListener(e -> {
                cart.add(item);
                JOptionPane.showMessageDialog(null, item.getItemName() + " added to cart.");
                showCartSection();
                frame.dispose();
            });

            itemPanel.add(itemName, BorderLayout.CENTER);
            itemPanel.add(addToCart, BorderLayout.SOUTH);
            panel.add(itemPanel);
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showCartSection() {
        JFrame cartFrame = new JFrame("Cart Section");
        cartFrame.getContentPane().setBackground(Main.backgroundColor);
        cartFrame.setSize(700, 550);
        cartFrame.setLayout(new BorderLayout());
        cartFrame.setLocationRelativeTo(null);
        cartFrame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(cart.size() + 1, 1, 10, 10));
        panel.setBackground(null);

        JLabel header = new JLabel("Your Cart");
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("Helvetica", Font.BOLD, 20));
        panel.add(header);

        cart.forEach(item -> {
            JLabel itemLabel = new JLabel(item.getItemName() + " - $" + item.getPrice());
            itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
            panel.add(itemLabel);
        });

        JButton checkoutButton = Main.button("Checkout");
        checkoutButton.addActionListener(e -> JOptionPane.showMessageDialog(cartFrame, "Proceeding to checkout..."));
        panel.add(checkoutButton);

        cartFrame.add(panel, BorderLayout.CENTER);
        cartFrame.setVisible(true);
    }
}
