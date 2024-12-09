package StoreProductsTest;

public class Item {

    private String itemName;
    private double price;
    private String pic;

    public Item(String itemName, double price, String pic) {
        this.itemName = itemName;
        this.price = price;
        this.pic = pic;
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
