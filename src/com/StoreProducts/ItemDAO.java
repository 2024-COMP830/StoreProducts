package StoreProductsTest;

import java.util.List;

public interface ItemDAO {
    void addItem(Item item);
    void editItem(int id, String name, double price);
    void deleteItem(int id);
    List<Item> getAllItems();
}
