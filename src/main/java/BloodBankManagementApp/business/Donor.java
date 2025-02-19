package BloodBankManagementApp.business;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Donor {

    private int donorID;


    //private User user;
    @NonNull
    private String userID;

    @NonNull
    private String fullName;

    @NonNull
    private String dateOfBirth;

    @NonNull
    private String contactNumber;

    @NonNull
    private String address;
}

