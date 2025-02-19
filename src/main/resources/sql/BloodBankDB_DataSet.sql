USE BloodBankDB;

/*
 CREATE TABLE Users (
                       userID INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       passwordHash VARCHAR(255) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       role ENUM('admin', 'donor', 'employee', 'hospital_admin') NOT NULL
)
 */
/*Data for the table `Users` */
INSERT INTO Users(userID, username, password, email, role)
VALUES(1, 'brigjones','bj123', 'brig@jones.co.uk', 'employee'),
      (2, 'Maryblack','dd234', 'mary@black.com', 'admin'),
      (3, 'DanDavito','dd234', 'dan@davito.com', 'donor'),
      (4, 'KerryJob','kj001', 'kerry@job.org', 'hospital_admin');

/*
 CREATE TABLE Donors (
                        donorID INT AUTO_INCREMENT PRIMARY KEY,
                        userID INT UNIQUE NOT NULL,
                        fullName VARCHAR(100) NOT NULL,
                        dateOfBirth DATE NOT NULL,
                        contactNumber VARCHAR(15) NOT NULL,
                        address TEXT NOT NULL,
                        FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE
);
 */
/*Data for the table `Donors` */
INSERT INTO Donors(donorID, userID, fullName, dateOfBirth, contactNumber, address)
VALUES(1, 3,);


/*Data for the table `BloodDonations` */
INSERT INTO BloodDonations (donorID, bloodTypeID, quantity, collectionDate, status)
VALUES (1, 2, 0.5, '2025-02-14', 'Stored'),
       (2, 1, 0.5, '2025-02-14', 'Stored');

/*Data for the table `BloodTests` */
INSERT INTO BloodTests (donationID, testDate, result, notes)
VALUES (1, '2025-02-15', 'Safe', 'Blood is clean and ready for supply');

/*Data for the table `BloodRequests` */
INSERT INTO BloodRequests (hospitalID, bloodTypeID, quantity, requestDate, status)
VALUES (1, 3, 1.0, '2025-02-16', 'Pending');

/*Data for the table `BloodSupplies` */
INSERT INTO BloodSupplies (requestID, donationID, supplyDate, quantitySupplied)
VALUES (1, 1, '2025-02-17', 1.0);



