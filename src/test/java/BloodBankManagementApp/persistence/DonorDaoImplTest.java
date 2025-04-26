package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.Donor;
import BloodBankManagementApp.business.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DonorDaoImplTest {

    @Mock
    User user ;
    DonorDaoImpl donorDao;
    @Test
    void addDonorSuccessfullyTest() {
        System.out.println("Testing Donor Added Successfully");
        Donor donor = new Donor();
        donorDao.addDonor(donor);
    }
    @Test
    void addDonor() {
    }

    @Test
    void getAllDonor() {
    }

    @Test
    void getDonorById() {
    }

    @Test
    void updateDonorDetails() {
    }
}