package StoreProductsTest;

import javax.swing.*;
import java.awt.*;

public class Guest extends Account {

    private final ItemDAO itemDAO;

    public Guest(String name, long phonenumber, String email, String password) {
        super(name, phonenumber, email, password);
        itemDAO = new ItemDAOImpl();
    }

    @Override
    public void menu() {
        JFrame frame = new JFrame("Store Management System");
        frame.getContentPane().setBackground(Main.backgroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JButton showMenu = Main.button("Show Menu");
        showMenu.addActionListener(e -> new MenuPage(false));
        frame.add(showMenu, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
