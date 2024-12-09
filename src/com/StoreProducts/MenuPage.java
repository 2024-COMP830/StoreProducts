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

    public MenuPage(boolean isAdmin) {
        this.itemDAO = new ItemDAOImpl();
        this.isAdmin = isAdmin;

        JFrame frame = new JFrame("Menu Management");
        frame.getContentPane().setBackground(Main.foregroundColor);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        String[] columnNames = isAdmin
                ? new String[]{"Item ID", "Item Name", "Price", "Actions"}
                : new String[]{"Item ID", "Item Name", "Price"};

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
                model.addRow(new Object[]{
                        item.getItemId(),
                        item.getItemName(),
                        item.getPrice()
                });
            }
        }

        if (isAdmin) {
            table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
            table.getColumn("Actions").setCellEditor(new ButtonEditor(items));
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
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

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;
        private final List<Item> items;

        public ButtonEditor(List<Item> items) {
            this.items = items;
            panel = new JPanel(new GridLayout(1, 2));
            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            editButton.addActionListener(e -> {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, panel);
                int row = table.getEditingRow();
                Item item = items.get(row);
                new editItem(item);
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
                    new MenuPage(true);
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
}
