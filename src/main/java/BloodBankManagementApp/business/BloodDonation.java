package BloodBankManagementApp.business;


import lombok.*;

import java.text.DecimalFormat;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BloodDonation {


    private int donationID;

    private Donor donor;
   // private  int donorID;

    //private BloodType bloodType;
    //private BloodType.Type bloodType;
    //private String bloodType;
    private int bloodTypeID;
    //private DecimalFormat quantity; // in liters
    private Double quantity;
    private Date collectionDate;
    
    private Status status;

    public enum Status {
        STORED, TESTED, SUPPLIED, DISCARDED
    }

}

