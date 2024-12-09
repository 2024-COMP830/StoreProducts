package StoreProductsTest;

import java.util.List;

public interface AccountDAO {
    void createAccount(Account account);
    Account login(long phoneNumber, String email, String password);
    List<Account> getAllAccounts();
}
