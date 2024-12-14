package StoreProductsTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public void createAccount(Account account) {
        String sql = """
            INSERT INTO Accounts (name, phone_number, email, password, role)
            VALUES (?, ?, ?, ?, ?);
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, account.getName());
            pstmt.setLong(2, account.getPhonenumber());
            pstmt.setString(3, account.getEmail());
            pstmt.setString(4, account.getPassword());
            pstmt.setString(5, account instanceof Admin ? "Admin" : "Guest");

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account login(long phoneNumber, String email, String password) {
        String sql = """
            SELECT * FROM Accounts WHERE phone_number = ? AND email = ? AND password = ?;
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, phoneNumber);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                if ("Admin".equals(role)) {
                    return new Admin(rs.getString("name"), rs.getLong("phone_number"),
                            rs.getString("email"), rs.getString("password"));
                } else {
                    return new Guest(rs.getString("name"), rs.getLong("phone_number"),
                            rs.getString("email"), rs.getString("password"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Accounts;";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String role = rs.getString("role");
                if ("Admin".equals(role)) {
                    accounts.add(new Admin(rs.getString("name"), rs.getLong("phone_number"),
                            rs.getString("email"), rs.getString("password")));
                } else {
                    accounts.add(new Guest(rs.getString("name"), rs.getLong("phone_number"),
                            rs.getString("email"), rs.getString("password")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void updateAccount(Account account) {
        String sql = """
            UPDATE Accounts SET name = ?, email = ?, password = ? WHERE phone_number = ?;
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, account.getName());
            pstmt.setString(2, account.getEmail());
            pstmt.setString(3, account.getPassword());
            pstmt.setLong(4, account.getPhonenumber());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(long phoneNumber) {
        String sql = "DELETE FROM Accounts WHERE phone_number = ?;";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, phoneNumber);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
