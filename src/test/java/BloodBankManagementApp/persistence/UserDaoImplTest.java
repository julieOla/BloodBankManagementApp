package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {
    @Mock
    UserDaoImpl userDao;
    @Test
    void registerUserSuccessfullyTest() {
        System.out.println("Texting Register new user successfully .");
        User user = new User();
        userDao.registerUser(user);
    }
    @Test
    void UserSuccessfullyTest1() {
        System.out.println("Texting Register new user successfully .");
        User user = new User();
        user.setUserID(1);
        user.setUsername("PopFrancis");
        user.setPassword("pf001");
        user.setEmail("pop@francis.eu");
        user.setRole(User.Role.DONOR);
        User user2 = new User();
        user2.setUserID(1);
        user2.setUsername("PopFrancis");
        user2.setPassword("pf001");
        user2.setEmail("pop@francis.eu");
        user2.setRole(User.Role.DONOR);


        userDao.registerUser(user);
    }
    @Test
    void registerUser() {
    }

    @Test
    void loginByUsername() {
    }

    @Test
    void getUserByUserNameSuccessfullTest() {
        System.out.println("Texting getUserByName successfull .");
        User user = new User();
        user.setUserID(1);
        user.setUsername("PopFrancis");
        user.setPassword("pf001");
        user.setEmail("pop@francis.eu");
        user.setRole(User.Role.DONOR);
        User user2 = new User();
        user2.setUserID(2);
        user2.setUsername("Mama Sita");
        user2.setPassword("mSisa123");
        user2.setEmail("mama@sita.ie");
        user2.setRole(User.Role.EMPLOYEE);
        userDao.getUserByUserName("MamaSita");
    }
    @Test
    void getUserByUserNameUnsuccessfullTest() {
        System.out.println("Texting getUserByName successfull .");
        User user = new User();
        user.setUserID(1);
        user.setUsername("PopFrancis");
        user.setPassword("pf001");
        user.setEmail("pop@francis.eu");
        user.setRole(User.Role.DONOR);
        User user2 = new User();
        user2.setUserID(2);
        user2.setUsername("Mama Sita");
        user2.setPassword("mSisa123");
        user2.setEmail("mama@sita.ie");
        user2.setRole(User.Role.EMPLOYEE);
        userDao.getUserByUserName("Trump");
    }
}