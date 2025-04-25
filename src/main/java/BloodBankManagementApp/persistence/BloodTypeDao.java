package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.BloodType;
import BloodBankManagementApp.business.BloodTypes;

import java.util.List;

public interface BloodTypeDao {
    public List<BloodType> getAllBloodType();
    //public List<BloodTypes> getAllBloodType();
    public BloodType getBloodTypeByGroupName(BloodType type);
    public BloodType getBloodTypeByGroupID(int groupID);
}
