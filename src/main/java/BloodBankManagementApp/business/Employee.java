package BloodBankManagementApp.business;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private int employeeID;

    //private User user;
    private String userID;
    // Hospital hospital;
    private String hospitalID;

    private String fullName;

    private String position;

    private String email;
}



/*import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class Employee extends User{
    // PROPERTIES
    private  int employeeNumber;
    private String jobTitle;
}*/
