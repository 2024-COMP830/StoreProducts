package StoreProductsTest;

import javax.swing.*;
import java.awt.*;

public class Login {

    private final AccountDAO accountDAO;

    public Login() {
        accountDAO = new AccountDAOImpl();

        JFrame frame = new JFrame("Login");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));

        JLabel l_phone = Main.label("Phone number:");
        JTextField phone = Main.textField();
        panel.add(l_phone);
        panel.add(phone);

        JLabel l_email = Main.label("Email:");
        JTextField email = Main.textField();
        panel.add(l_email);
        panel.add(email);

        JLabel l_password = Main.label("Password:");
        JTextField password = Main.textField();
        panel.add(l_password);
        panel.add(password);

        JButton newAcc = Main.button("Create new account");
        newAcc.addActionListener(e -> {
            new newAcc();
            frame.dispose();
        });
        panel.add(newAcc);

        JButton login = Main.button("Login");
        login.addActionListener(e -> {
            String phoneText = phone.getText();
            String emailText = email.getText();
            String passwordText = password.getText();

            try {
                long phoneNumber = Long.parseLong(phoneText);
                Account account = accountDAO.login(phoneNumber, emailText, passwordText);
                if (account != null) {
                    account.menu();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Phone number must be an integer!");
            }
        });
        panel.add(login);

        panel.setBorder(BorderFactory.createEmptyBorder(140, 100, 140, 100));
        panel.setBackground(null);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
