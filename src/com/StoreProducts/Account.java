package StoreProductsTest;

public abstract class Account {

    private String name;
    private long phonenumber;
    private String email;
    private String password;

    public Account(String name, long phonenumber, String email, String password) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
    }

    public abstract void menu();

    public String getName() {
        return name;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
