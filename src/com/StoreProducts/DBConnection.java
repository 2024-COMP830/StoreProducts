package StoreProductsTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:StoreManagementSystem.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String accountsTable = """
                    CREATE TABLE IF NOT EXISTS Accounts (
                        account_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        phone_number INTEGER NOT NULL UNIQUE,
                        email TEXT NOT NULL UNIQUE,
                        password TEXT NOT NULL,
                        role TEXT NOT NULL CHECK(role IN ('Admin', 'Guest'))
                    );
                """;
                String itemsTable = """
                    CREATE TABLE IF NOT EXISTS Items (
                        item_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        price REAL NOT NULL,
                        pic TEXT
                    );
                """;
                String cartTable = """
                    CREATE TABLE IF NOT EXISTS Cart (
                        cart_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        account_id INTEGER NOT NULL,
                        item_id INTEGER NOT NULL,
                        added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (account_id) REFERENCES Accounts(account_id),
                        FOREIGN KEY (item_id) REFERENCES Items(item_id)
                    );
                """;

                stmt.execute(accountsTable);
                stmt.execute(itemsTable);
                stmt.execute(cartTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
