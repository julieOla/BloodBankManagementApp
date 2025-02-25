package BloodBankManagementApp.business;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Donor {

    private int donorID;


    private User user;
    @NonNull
    private int userID;

    @NonNull
    private String fullName;

    @NonNull
    private Date dateOfBirth;

    @NonNull
    private String contactNumber;

    @NonNull
    private String address;
}

