package StoreProductsTest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.List;

public class MenuPage {

    private final ItemDAO itemDAO;
    private final boolean isAdmin;
    private final List<Item> cart;

    public MenuPage(boolean isAdmin, List<Item> cart) {
        this.itemDAO = new ItemDAOImpl();
        this.isAdmin = isAdmin;
        this.cart = cart;

        JFrame frame = new JFrame(isAdmin ? "Admin Menu Management" : "Guest Menu Management");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        String[] columnNames = isAdmin
                ? new String[]{"Item ID", "Item Name", "Price", "Actions"}
                : new String[]{"Item Name", "Price", "Add to Cart"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);

        List<Item> items = itemDAO.getAllItems();
        for (Item item : items) {
            if (isAdmin) {
                model.addRow(new Object[]{
                        item.getItemId(),
                        item.getItemName(),
                        item.getPrice(),
                        "Edit/Delete"
                });
            } else {
                if (!cart.contains(item)) {
                    model.addRow(new Object[]{
                            item.getItemName(),
                            item.getPrice(),
                            "Add to Cart"
                    });
                }
            }
        }

        if (isAdmin) {
            table.getColumn("Actions").setCellRenderer(new ButtonRendererAdmin());
            table.getColumn("Actions").setCellEditor(new ButtonEditorAdmin(items, cart));
        } else {
            table.getColumn("Add to Cart").setCellRenderer(new ButtonRendererGuest());
            table.getColumn("Add to Cart").setCellEditor(new ButtonEditorGuest(items, cart, model));
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton viewCartButton = Main.button("View Cart");
        JButton backButton = Main.button("Back to Dashboard");

        viewCartButton.addActionListener(e -> new CartPage(cart, new Guest("Guest", 1234567890L, "guest@example.com", "password")));
        backButton.addActionListener(e -> {
            frame.dispose();
            if (isAdmin) {
                new Admin("Admin", 1234567890L, "admin@example.com", "password").menu();
            } else {
                new Guest("Guest", 1234567890L, "guest@example.com", "password").menu();
            }
        });

        if (!isAdmin) {
            bottomPanel.add(viewCartButton);
        }
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    class ButtonRendererAdmin extends JPanel implements TableCellRenderer {
        public ButtonRendererAdmin() {
            setLayout(new GridLayout(1, 2));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();
            JButton editButton = new JButton("Edit");
            JButton deleteButton = new JButton("Delete");
            add(editButton);
            add(deleteButton);
            return this;
        }
    }

    class ButtonEditorAdmin extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;
        private final List<Item> items;
        private final List<Item> cart;

        public ButtonEditorAdmin(List<Item> items, List<Item> cart) {
            this.items = items;
            this.cart = cart;
            panel = new JPanel(new GridLayout(1, 2));
            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            editButton.addActionListener(e -> {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, panel);
                int row = table.getEditingRow();
                Item item = items.get(row);
                new editItem(item, cart);
                fireEditingStopped();
            });

            deleteButton.addActionListener(e -> {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, panel);
                int row = table.getEditingRow();
                Item item = items.get(row);
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?");
                if (confirm == JOptionPane.YES_OPTION) {
                    itemDAO.deleteItem(item.getItemId());
                    JOptionPane.showMessageDialog(null, "Item deleted successfully!");
                    fireEditingStopped();
                    new MenuPage(true, cart);
                }
            });

            panel.add(editButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    class ButtonRendererGuest extends JPanel implements TableCellRenderer {
        public ButtonRendererGuest() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();
            JButton addToCartButton = new JButton("Add to Cart");
            add(addToCartButton);
            return this;
        }
    }

    class ButtonEditorGuest extends AbstractCellEditor implements TableCellEditor {
        private final JButton addToCartButton;
        private final List<Item> items;
        private final List<Item> cart;
        private final DefaultTableModel model;

        public ButtonEditorGuest(List<Item> items, List<Item> cart, DefaultTableModel model) {
            this.items = items;
            this.cart = cart;
            this.model = model;

            addToCartButton = new JButton("Add to Cart");

            addToCartButton.addActionListener(e -> {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, addToCartButton);
                int row = table.getEditingRow();
                Item item = items.get(row);
                if (!cart.contains(item)) {
                    cart.add(item);
                    JOptionPane.showMessageDialog(null, item.getItemName() + " added to cart!");
                    model.removeRow(row);
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return addToCartButton;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
}
