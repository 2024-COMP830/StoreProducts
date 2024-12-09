package StoreProductsTest;

public class Item {
    private int itemId;
    private String itemName;
    private double price;
    private String pic;

    public Item(int itemId, String itemName, double price, String pic) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.pic = pic;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public String getPic() {
        return pic;
    }
}
