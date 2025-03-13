package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.User;
public interface UserDao {
    public boolean registerUser(User user);
    public User login(String username, String password);
    User loginByUsername(String username);
}
