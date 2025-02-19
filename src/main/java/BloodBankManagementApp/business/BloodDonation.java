package BloodBankManagementApp.business;


import lombok.*;
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

    private BloodType bloodType;

    private double quantity; // in liters

    private Date collectionDate;
    
    private Status status;

    public enum Status {
        STORED, TESTED, SUPPLIED, DISCARDED
    }
}

