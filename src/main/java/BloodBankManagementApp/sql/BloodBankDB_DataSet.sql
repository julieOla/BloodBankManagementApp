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
INSERT INTO Users( username, passwordHash, email, role)
VALUES( 'brigjones','bj123', 'brig@jones.co.uk', 'employee'),
      ( 'Maryblack','mb190', 'mary@black.com', 'admin'),
      ( 'DanDavito','dd234', 'dan@davito.com', 'donor'),
      ( 'KerryJob','kj001', 'kerry@job.org', 'hospital_admin');
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
/*INSERT INTO Donors( userID, fullName, dateOfBirth, contactNumber, address)
VALUES( 3,'Dan Davito','2000-03-20','+353421490', '6 College rd., Dundalk, co.Louth'),
      ( 4,'Peter Piper','1980-10-30','+353412000', '14 Kells st., Ardee, co.Louth');
*/
-- Hospital Employees Table (Employees working in hospitals)
/*CREATE TABLE Employees (
                           employeeID INT AUTO_INCREMENT PRIMARY KEY,
                           userID INT UNIQUE NOT NULL,
                           hospitalID INT NOT NULL,
                           fullName VARCHAR(100) NOT NULL,
                           position VARCHAR(50) NOT NULL,
                           /*salary DECIMAL(10,2),
                           phone VARCHAR(15) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE,
                           FOREIGN KEY (hospitalID) REFERENCES Hospitals(hospitalID) ON DELETE CASCADE
);*/
/*
INSERT INTO  employees( userID, hospitalID, fullName, position, email)
VALUES(1, 1, 'Brig Jones', 'Matron','brigjone@olol.ie');*/
/*Data for the table `Donors` */



/*Data for the table `BloodDonations` */
/*INSERT INTO BloodDonations ( bloodTypeID, quantity, collectionDate, status)
VALUES ( 2, 0.5, '2025-02-14', 'Stored'),
       ( 1, 0.5, '2025-02-14', 'Stored');*/

/*Data for the table `BloodTests` */
/*INSERT INTO BloodTests (donationID, testDate, result, notes)
VALUES (1, '2025-02-15', 'Safe', 'Blood is clean and ready for supply');
*/
/*Data for the table `BloodRequests` */
/*INSERT INTO BloodRequests (hospitalID, bloodTypeID, quantity, requestDate, status)
VALUES (1, 3, 1.0, '2025-02-16', 'Pending');
*/
/*Data for the table `BloodSupplies` */
/*INSERT INTO BloodSupplies (requestID, donationID, supplyDate, quantitySupplied)
VALUES (1, 1, '2025-02-17', 1.0);*/



