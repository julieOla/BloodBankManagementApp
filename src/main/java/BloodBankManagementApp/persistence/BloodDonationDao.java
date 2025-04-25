package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.BloodDonation;
import java.util.List;

public interface BloodDonationDao {


    boolean addBloodDonation(BloodDonation donation);

    public List<BloodDonation> getAllBloodDonation();
    public BloodDonation getBloodDonationByID(int id);
    public boolean deleteBloodDonationByID(int id);
    public boolean updateBloodDonationByID(int id, String status);
}
