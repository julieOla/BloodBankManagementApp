package BloodBankManagementApp.business;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Add equals and hashcode methods - only include the specifically noted variables
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Donor {

    @EqualsAndHashCode.Include
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

