package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.BloodType;
import BloodBankManagementApp.business.Donor;
import org.attoparser.dom.Text;

import java.util.List;

public interface DonorDao {
    public  boolean addDonor(Donor donor);
    public List<Donor> getAllDonor();
    public Donor getDonorById(int donorId);
    public boolean updateDonorDetails(int id, String phoneNum, Text address);
}
