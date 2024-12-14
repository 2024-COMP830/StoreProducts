package StoreProductsTest;

import java.util.List;

public interface AccountDAO {
    void createAccount(Account account);
    Account login(long phoneNumber, String email, String password);
    List<Account> getAllAccounts();
    void updateAccount(Account account); // Added update functionality
    void deleteAccount(long phoneNumber); // Added delete functionality
}
