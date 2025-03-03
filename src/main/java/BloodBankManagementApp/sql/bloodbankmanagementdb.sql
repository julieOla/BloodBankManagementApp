DROP DATABASE IF EXISTS bloodbankmanagementdb;
CREATE DATABASE IF NOT EXISTS bloodbankmanagementdb;

USE bloodbankmanagementdb;

DROP TABLE IF EXISTS users;
CREATE TABLE users(
                      userid   INT UNSIGNED NOT NULL AUTO_INCREMENT,
                      firstname varchar(50) NOT NULL,
                      lastname varchar(50) NOT NULL,
                      username varchar(50) NOT NULL,
                      password varchar(100) NOT NULL,
                      email varchar(50) NOT NULL,
                      usertype varchar(50),
                      isvaliduser varchar(20),
                      PRIMARY KEY(userid)

);

# --Insert into table
INSERT INTO users (username, password) VALUES ('julie', 'j123');

/*Table structure for table employees */
DROP TABLE IF EXISTS employees;
CREATE TABLE employees (
                           employeeNumber int(11) AUTO_INCREMENT NOT NULL,
                           userid int(11) NOT NULL,
                          /* lastName varchar(50) NOT NULL,
                           firstName varchar(50) NOT NULL,
                           email varchar(100) NOT NULL,*/
                           jobTitle varchar(50) NOT NULL,
                           PRIMARY KEY  (employeeNumber),
                           FOREIGN KEY (userid) REFERENCES users(userid)
);

/*DROP TABLE IF EXISTS employees;
CREATE TABLE employees(
                      employeeId   INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      userId    INT NOT NULL,
                      bloodgroup varchar(20) NOT NULL,
                      status varchar(20) NOT NULL ,
                      lastupdate DATETIME*/


    /*PRIMARY KEY(username)*/

/*);*/