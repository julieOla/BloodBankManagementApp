package BloodBankManagementApp.business;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class User {

    // PROPETIES
    private int userID;

    @EqualsAndHashCode.Include
    private String username;

    @ToString.Exclude
    private String password;


    private String email;

    //@Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN, DONOR, EMPLOYEE, HOSPITAL_ADMIN
    }
}


/*
import lombok.*;

// Add getter methods
@Getter
// Add a toString method
@ToString
// Add equals and hashcode methods - only include the specifically noted variables
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// Add the ability to build object with any components in any order
@Builder
// Add an all-args constructor
@AllArgsConstructor
@NoArgsConstructor

public class User {
    // PROPERTIES
    @EqualsAndHashCode.Include
    private int userId;
    private String username; // Concert firstname & lastname
    @ToString.Exclude
    private String password;
    private String email;
    private String usertype; // Employee, donor, Hospital
    private boolean isValideUser; // varify is a user is still a valid user

    //CONSTRUCTOR

}*/
