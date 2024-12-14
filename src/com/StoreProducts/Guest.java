package StoreProductsTest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Guest extends Account {

    private final ItemDAO itemDAO;
    private final List<Item> cart;

    public Guest(String name, long phonenumber, String email, String password) {
        super(name, phonenumber, email, password);
        this.itemDAO = new ItemDAOImpl();
        this.cart = new ArrayList<>();
    }

    @Override
    public void menu() {
        JFrame frame = new JFrame("Guest Dashboard");
        frame.getContentPane().setBackground(Main.backgroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(2, 1, 20, 20));

        JButton showMenuButton = Main.button("Show Menu");
        showMenuButton.addActionListener(e -> {
            frame.dispose();
            new MenuPage(false, cart);
        });

        JButton logoutButton = Main.button("Logout");
        logoutButton.addActionListener(e -> {
            new Login();
            frame.dispose();
        });

        panel.add(showMenuButton);
        panel.add(logoutButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public List<Item> getCart() {
        return cart;
    }
}
