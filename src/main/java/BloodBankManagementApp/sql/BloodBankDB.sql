CREATE DATABASE BloodBankDB;
USE BloodBankDB;

-- Users Table (Manages authentication for all users)
CREATE TABLE Users (
                       userID INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       passwordHash VARCHAR(255) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       role ENUM('admin', 'donor', 'employee', 'hospital_admin') NOT NULL
);

-- Donors Table (Stores donor information, linked to Users)
CREATE TABLE Donors (
                        donorID INT AUTO_INCREMENT PRIMARY KEY,
                        userID INT UNIQUE NOT NULL,
                        fullName VARCHAR(100) NOT NULL,
                        dateOfBirth DATE NOT NULL,
                        contactNumber VARCHAR(15) NOT NULL,
                        address TEXT NOT NULL,
                        FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE
);

-- Blood Types Table (To avoid redundant blood type storage)
CREATE TABLE BloodTypes (
                            bloodTypeID INT AUTO_INCREMENT PRIMARY KEY,
                            bloodType ENUM('A+', 'A-', 'B+', 'B-', 'O+', 'O-', 'AB+', 'AB-') UNIQUE NOT NULL
);

-- Blood Donations Table (Tracks donations made by donors)
CREATE TABLE BloodDonations (
                                donationID INT AUTO_INCREMENT PRIMARY KEY,
                                donorID INT NOT NULL,
                                bloodTypeID INT NOT NULL,
                            --    quantity DECIMAL(5,2) NOT NULL CHECK (quantity > 0), -- Quantity in liters
                                quantity DOUBLE(5,2) NOT NULL CHECK (quantity > 0),
                                collectionDate DATE NOT NULL,
                                status ENUM('Stored', 'Tested', 'Supplied', 'Discarded') DEFAULT 'Stored',
                                FOREIGN KEY (donorID) REFERENCES Donors(donorID) ON DELETE CASCADE,
                                FOREIGN KEY (bloodTypeID) REFERENCES BloodTypes(bloodTypeID) ON DELETE CASCADE
);

-- Blood Tests Table (Tracks tests performed on donated blood)
CREATE TABLE BloodTests (
                            testID INT AUTO_INCREMENT PRIMARY KEY,
                            donationID INT NOT NULL,
                            testDate DATE NOT NULL,
                            result ENUM('Safe', 'Infected', 'Requires Further Testing') NOT NULL,
                            notes TEXT,
                            FOREIGN KEY (donationID) REFERENCES BloodDonations(donationID) ON DELETE CASCADE
);

-- Hospitals Table (Stores hospital details)
CREATE TABLE Hospitals (
                           hospitalID INT AUTO_INCREMENT PRIMARY KEY,
                           hospitalName VARCHAR(100) NOT NULL,
                           address TEXT NOT NULL,
                           phone VARCHAR(15) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL
);

-- Hospital Employees Table (Employees working in hospitals)
CREATE TABLE Employees (
                           employeeID INT AUTO_INCREMENT PRIMARY KEY,
                           userID INT UNIQUE NOT NULL,
                           hospitalID INT NOT NULL,
                           fullName VARCHAR(100) NOT NULL,
                           position VARCHAR(50) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE,
                           FOREIGN KEY (hospitalID) REFERENCES Hospitals(hospitalID) ON DELETE CASCADE
);

-- Blood Requests Table (Hospitals requesting blood units)
CREATE TABLE BloodRequests (
                               requestID INT AUTO_INCREMENT PRIMARY KEY,
                               hospitalID INT NOT NULL,
                               bloodTypeID INT NOT NULL,
                               quantity DECIMAL(5,2) NOT NULL CHECK (quantity > 0),
                               requestDate DATE NOT NULL,
                               status ENUM('Pending', 'Approved', 'Rejected', 'Completed') DEFAULT 'Pending',
                               FOREIGN KEY (hospitalID) REFERENCES Hospitals(hospitalID) ON DELETE CASCADE,
                               FOREIGN KEY (bloodTypeID) REFERENCES BloodTypes(bloodTypeID) ON DELETE CASCADE
);

-- Blood Supply Table (Tracks blood units supplied to hospitals)
CREATE TABLE BloodSupplies (
                               supplyID INT AUTO_INCREMENT PRIMARY KEY,
                               requestID INT NOT NULL,
                               donationID INT NOT NULL,
                               supplyDate DATE NOT NULL,
                               quantitySupplied DECIMAL(5,2) NOT NULL CHECK (quantitySupplied > 0),
                               FOREIGN KEY (requestID) REFERENCES BloodRequests(requestID) ON DELETE CASCADE,
                               FOREIGN KEY (donationID) REFERENCES BloodDonations(donationID) ON DELETE CASCADE
);

-- Create Employees Table
CREATE TABLE Employees (
                           EmployeeID INT PRIMARY KEY,
                           FirstName VARCHAR(100),
                           LastName VARCHAR(100),
                           Salary DECIMAL(10, 2),
                           Department VARCHAR(100)
);
--  DonorsLogs Table ( Tracks Update action on donors table)
CREATE TABLE DonorsLogs (
                              LogID INT AUTO_INCREMENT PRIMARY KEY,
                              DonorID INT,
                              ActionType VARCHAR(50),
                              OldContactNumber  VARCHAR(50),
                              OldAddress  TEXT(50),
                           -- LogMessage VARCHAR(255),
                              LogDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Create TRIGGER To insert Update into DonorsLogs table each Transaction
DELIMITER $$
CREATE TRIGGER before_donors_update
    BEFORE UPDATE ON donors
    FOR EACH ROW
BEGIN
    IF OLD.contactNumber != NEW.contactNumber AND OLD.address != NEW.address THEN
        INSERT INTO DonorsLogs (donorID, ActionType, OldContactNumber, OldAddress)
        VALUES (NEW.donorID, 'UPDATE', OLD.contactNumber, OLD.address );
    END IF;
END $$

DELIMITER ;

-- Create TRIGGER on Donors table Before Update
/*DELIMITER $$

CREATE TRIGGER before_donors_update
    BEFORE UPDATE ON donors
    FOR EACH ROW
BEGIN
    IF OLD.contactNumber != NEW.contactNumber AND OLD.address != NEW.address THEN
        INSERT INTO DonorsLogs (donorID, ActionType, OldContactNumber, OldAddress)
        VALUES (NEW.donorID, 'UPDATE', CONCAT('ContactNumber changed from ', OLD.contactNumber, ' to ', NEW.contactNumber), CONCAT('Address changed from ', OLD.address, ' to ', NEW.address) );
    END IF;
END $$

DELIMITER ;
*/


