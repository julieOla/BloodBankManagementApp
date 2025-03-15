package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.Donor;
import BloodBankManagementApp.business.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@Slf4j
public class DonorDaoImpl extends MySQLDao implements DonorDao{

    public DonorDaoImpl(String propertiesFile) {
        super(propertiesFile);
    }

    public DonorDaoImpl(Connection c) {
        super(c);
    }

    public DonorDaoImpl() {
        super();
    }
    @Override
    public boolean addDonor(Donor donor) {

        boolean added = false;

        Connection c = super.getConnection();
        //User donorUser =
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO donors VALUES(?, ?, ?,?,?)")) {
            ps.setInt(1, donor.getUser().getUserID());
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

    @Override
    public List<Donor> getAllDonor() {

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
    private static Donor mapRow(ResultSet rs) throws SQLException {

        return Donor.builder()
                .userID(rs.getInt("donorID"))
                .userID(rs.getInt("userID"))
                .fullName(rs.getString("fullName"))
                .dateOfBirth(rs.getDate("dateOfBirth"))
                .contactNumber(rs.getString("contactNumber"))
                .address(rs.getString("address"))
                .build();
    }


}
