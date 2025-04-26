package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.Donor;
import BloodBankManagementApp.business.User;
import lombok.extern.slf4j.Slf4j;
import org.attoparser.dom.Text;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link DonorDao} interface to interact with the Donor table in the database.
 * This class provides methods for adding, updating, and retrieving donor records.
 * It extends {@link MySQLDao} for database connection handling.
 *
 * <p>Logging is provided using Lombok's {@code @Slf4j} annotation.</p>
 *
 * @see Donor
 * @see MySQLDao
 */

@Slf4j
public class DonorDaoImpl extends MySQLDao implements DonorDao{

    /**
     * Constructs a DonorDaoImpl with a specific properties file for DB configuration.
     *
     * @param propertiesFile the name of the properties file
     */
    public DonorDaoImpl(String propertiesFile) {
        super(propertiesFile);
    }

    /**
     * Constructs a DonorDaoImpl with an existing database connection.
     *
     * @param c the SQL connection
     */
    public DonorDaoImpl(Connection c) {
        super(c);
    }

    /**
     * Constructs a DonorDaoImpl with default configuration.
     */
    public DonorDaoImpl() {
        super();
    }

    /**
     * Adds a new donor to the database.
     *
     * @param donor the Donor object to be added
     * @return {@code true} if donor was added successfully, {@code false} otherwise
     */
    @Override
    public boolean addDonor(Donor donor) {

        boolean added = false;

        Connection c = super.getConnection();
        //User donorUser =
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO donors (userID, fullName, dateOfBirth, contactNumber, address) VALUES(?, ?, ?,?,?)")) {
            ps.setInt(1, donor.getUserID());
            ps.setString(2, donor.getFullName());
            ps.setDate(3, (Date) donor.getDateOfBirth());
            ps.setString(4, donor.getContactNumber());
            ps.setString(5, donor.getAddress());


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                added = true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("SQLIntegrityConstraintViolationException occurred when attempting to register new User", e);
        } catch (SQLException e) {
            log.error("SQLException occurred when attempting to register new User", e);
        }

        super.freeConnection(c);

        return added;
    }

    /**
     * Retrieves a list of all donors from the database.
     *
     * @return a List of Donor objects
     */
    @Override
    public  List<Donor> getAllDonor() {

        List<Donor> listOfDonors = new ArrayList<>();

        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT * FROM donors ")) {

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    listOfDonors.add(mapRow(rs));
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to execute get donors query", e);
        }
        super.freeConnection(c);

        return listOfDonors;

    }
    /**
     * Retrieves a donor by their unique ID.
     *
     * @param donorId the donor ID
     * @return the Donor object if found, or {@code null} otherwise
     */
    @Override
    public Donor getDonorById(int donorId) {
        Donor donor = null;
        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT * FROM donors WHERE donorID = ? ")) {
            ps.setInt(1, donorId);


            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    donor = mapRow(rs);
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to get donor", e);
        }
        super.freeConnection(c);

        return donor;

    }

    /**
     * Updates a donor's contact number and address.
     *
     * @param id       the donor ID
     * @param phoneNum the new contact number
     * @param address  the new address
     * @return {@code true} if update was successful, {@code false} if no rows were affected
     * @throws RuntimeException if more than one row is affected
     */
    public boolean updateDonorDetails(int id, String phoneNum, Text address) throws RuntimeException {
        int rowsAffected = 0;

        Connection conn = super.getConnection();
        try (PreparedStatement ps =
                     conn.prepareStatement("UPDATE donors SET contactNumber = ?, address = ? WHERE donorID = ?")) {

            ps.setString(1, String.valueOf(address));
            ps.setString(2, phoneNum);
            ps.setInt(3, id);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            log.info(LocalDateTime.now() + ": An SQLException  occurred while preparing the SQL " +
                    "statement." + e.getMessage());
        }

        if(rowsAffected > 1){
            throw new RuntimeException(LocalDateTime.now() + " ERROR: Multiple rows affected on primary key selection" +
                    ".");
        }
        else if(rowsAffected == 0){
            return false;
        }else{
            return true;
        }
    }
    private static Donor mapRow(ResultSet rs) throws SQLException {

        return Donor.builder()
                .donorID(rs.getInt("donorID"))
                .userID(rs.getInt("userID"))
                .fullName(rs.getString("fullName"))
                .dateOfBirth(rs.getDate("dateOfBirth"))
                .contactNumber(rs.getString("contactNumber"))
                .address(rs.getString("address"))
                .build();
    }

    public static void main(String[] args) {
        // Testing getdonorbyId()
        Donor donor;
        DonorDao donorDao = new DonorDaoImpl("database.properties");
        donor = donorDao.getDonorById(2);
        System.out.println(donor);
        System.out.println("------------------------------");
        List<Donor> donorslist = donorDao.getAllDonor();
        for (Donor d : donorslist
             ) {
            System.out.println(d);
        }
        //System.out.println(donorslist);

    }
}
