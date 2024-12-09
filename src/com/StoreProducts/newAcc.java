package StoreProductsTest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class newAcc {

    private final AccountDAO accountDAO;

    public newAcc() {
        accountDAO = new AccountDAOImpl();

        JFrame frame = new JFrame("Create New Account");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(6, 2, 20, 20));

        JLabel l_name = Main.label("Name:");
        JTextField name = Main.textField();
        panel.add(l_name);
        panel.add(name);

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

        ButtonGroup roleGroup = new ButtonGroup();
        JRadioButton admin = new JRadioButton("Admin");
        JRadioButton guest = new JRadioButton("Guest");
        roleGroup.add(admin);
        roleGroup.add(guest);
        panel.add(admin);
        panel.add(guest);

        JButton cancel = Main.button("Cancel");
        cancel.addActionListener(e -> {
            new Login();
            frame.dispose();
        });
        panel.add(cancel);

        JButton createAccount = Main.button("Create Account");
        createAccount.addActionListener(e -> {
            String nameText = name.getText();
            String phoneText = phone.getText();
            String emailText = email.getText();
            String passwordText = password.getText();
            boolean isAdmin = admin.isSelected();

            if (nameText.isEmpty() || phoneText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || (!admin.isSelected() && !guest.isSelected())) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }

            try {
                long phoneNumber = Long.parseLong(phoneText);
                Account newAccount = isAdmin ? new Admin(nameText, phoneNumber, emailText, passwordText)
                        : new Guest(nameText, phoneNumber, emailText, passwordText);
                accountDAO.createAccount(newAccount);
                JOptionPane.showMessageDialog(null, "Account created successfully!");
                new Login();
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Phone number must be an integer!");
            }
        });
        panel.add(createAccount);

        panel.setBorder(BorderFactory.createEmptyBorder(80, 100, 80, 100));
        panel.setBackground(null);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
