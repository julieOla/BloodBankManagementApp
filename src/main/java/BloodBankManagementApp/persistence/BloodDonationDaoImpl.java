package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.BloodDonation;
import BloodBankManagementApp.business.BloodType;
import BloodBankManagementApp.business.Donor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class BloodDonationDaoImpl extends MySQLDao implements BloodDonationDao{

    public BloodDonationDaoImpl(String propertiesFile) {
        super(propertiesFile);
    }

    public BloodDonationDaoImpl(Connection c) {
        super(c);
    }

    public BloodDonationDaoImpl() {
        super();
    }


    //@Override
    /*public boolean addBloodDonation(BloodDonation donation) {
        boolean added = false;

        Connection c = super.getConnection();
        //User donorUser =
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO blooddonations (donorID, bloodTypeID, quantity, colectionDate, status) VALUES(?, ?, ?,?,?)")) {
            ps.setInt(1, donation.getDonorID());
           // ps.setString(2,  donation.getBloodType().getBloodTypeID();
            ps.setInt(2, donation.getDonationID());
            ps.setDouble(3, donation.getQuantity());
            ps.setDate(4, Date.valueOf(String.valueOf(donation.getCollectionDate())));
            ps.setString(5, String.valueOf(donation.getStatus()));


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
       // return false;
    }*/

   /* @Override
    /*public List<BloodDonation> getAllBloodDonation() {
        List<BloodDonation> listOfDonations = new ArrayList<>();

        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT * FROM blooddonations ")) {

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    listOfDonations.add(mapRow(rs));
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to execute get donors query", e);
        }
        super.freeConnection(c);

        return listOfDonations;
        //return null;
    }*/

    @Override
    public boolean addBloodDonation(BloodDonation donation) {
        boolean added = false;
        Connection c = super.getConnection();
        //User donorUser =
        //String toAdd = getDatabaseBloodType(bloodtype.getBloodType());
        //int id = donation.getDonor().getDonorID();
        //BloodType.Type type;
        //BloodType.BloodTypes.values();

        try (PreparedStatement ps = c.prepareStatement("INSERT INTO blooddonations ( donorID, bloodTypeID, quantity, collectionDate, status) " +
                "VALUES(?, ?, ?, ?,?)")) {
            //ps.setInt(1, donation.getDonor().getDonorID());
            ps.setInt(1, donation.getDonor().getDonorID());
           // ps.setString(2, String.valueOf(donation.getBloodType()));
            ps.setInt(2,donation.getBloodTypeID());
            //ps.setInt(2, donation.getBloodType().getBloodTypeID());
            ps.setDouble(3, donation.getQuantity());
            ps.setDate(4, (Date) donation.getCollectionDate());
            ps.setString(5, String.valueOf(donation.getStatus()));

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                added = true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("SQLIntegrityConstraintViolationException occurred when attempting to add new Donation !", e);
        } catch (SQLException e) {
            log.error("SQLException occurred when attempting to add new Donation !", e);
        }

        super.freeConnection(c);

        return added;

    }

    @Override
    public List<BloodDonation> getAllBloodDonation() {
        List<BloodDonation> listOfDonations = new ArrayList<>();

        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT * FROM blooddonations ")) {

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    listOfDonations.add(mapRow(rs));
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to execute get donors query", e);
        }
        super.freeConnection(c);

        return listOfDonations;

    }

    @Override
    public BloodDonation getBloodDonationByID(int id) {

    //public BloodDonation getById(int id) {
        BloodDonation donation = null;

        // Get a connection using the superclass
        Connection conn = super.getConnection();
        // TRY to get a statement from the connection
        // When you are parameterizing the query, remember that you need
        // to use the ? notation (so you can fill in the blanks later)
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM blooddonations where donationID = ?")) {
            // Fill in the blanks, i.e. parameterize the query
            ps.setInt(1, id);

            // TRY to execute the query
            try (ResultSet rs = ps.executeQuery()) {
                // Extract the information from the result set
                // Use extraction method to avoid code repetition!
                if(rs.next()) {
                    donation = mapRow(rs);
                }
            } catch (SQLException e) {
                log.info("SQL Exception occurred when executing SQL or processing results.", e);
            }
        } catch (SQLException e) {
            log.info("SQL Exception occurred when attempting to prepare SQL for execution", e);

        }finally {
            // Close the connection using the superclass method
            super.freeConnection(conn);
        }
        return donation;
    }

        @Override
    public boolean deleteBloodDonationByID(int id) {
            int rowsAffected = 0;

            Connection conn = super.getConnection();
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM blooddonations where donationID = ?")) {
                // Fill in the blanks, i.e. parameterize the query
                ps.setInt(1, id);

                // Execute the operation
                // Remember that when you are doing an update, a delete or an insert,
                // your only result will be a number indicating how many rows were affected
                rowsAffected = ps.executeUpdate();
            } catch (SQLException e) {
                log.info("SQL Exception occurred when attempting to prepare/execute SQL.", e);
            } finally {
                // Close the superclass method
                super.freeConnection(conn);
            }

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }

    }

    @Override
    public boolean updateBloodDonationByID(int id, String status)throws RuntimeException {
        //return false;
    //}
    //public boolean updateEmployeeEmail(int id, String email) throws RuntimeException {
        int rowsAffected = 0;

        Connection conn = super.getConnection();
        try (PreparedStatement ps =
                     conn.prepareStatement("UPDATE blooddonations SET status = ? WHERE donationID = ?")) {
            ps.setString(1, status);
            ps.setInt(2, id);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            log.info(LocalDateTime.now() + ": An SQLException  occurred while preparing the SQL " +
                    "statement.", e);
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

    // Method to convert BloodType to string
     /*static String getBloodType(BloodDonation bloodType) throws SQLException {

         // Convert the string to the corresponding BloodTpyt.Type enum

        // BloodType.Type type = null;
         String result = null;
         String convertType = bloodType.getBloodType().toString();

         try {
             switch (convertType) {
                 case "A+":

                     result = convertType;
                 case "A-":
                     result = convertType;
                 case "B+":
                     result = convertType;
                 case "B-":
                     result = convertType;
                 case "O+":
                     result = convertType;
                 case "O-":
                     result = convertType;
                 case "AB+":
                     result = convertType;
                 case "AB-":
                     result = convertType;

             }
         } catch (IllegalArgumentException e) {
             log.info("Unknown Blood Type !");

         }
         return result;
     }*/

    // Method to convert Status to string, returns status
    private static BloodDonation.Status getStatus(ResultSet resultSet) throws SQLException {
        BloodDonation.Status status = null;

        String stat = resultSet.getString("status");
        try{
            //STORED, TESTED, SUPPLIED, DISCARDED
            switch (stat.toUpperCase()){
                case "STORED":
                    status = BloodDonation.Status.STORED;
                    break;
                case "TESTED":
                    status = BloodDonation.Status.TESTED;
                    break;
                case "SUPPLIED":
                    status = BloodDonation.Status.SUPPLIED;
                    break;
                case "DISCARDED":
                    status = BloodDonation.Status.DISCARDED;
                    break;
                default:
                    status = BloodDonation.Status.STORED;
            }
        }catch (IllegalArgumentException e){
            log.info("Unknown status");
        }
        return status;
    }

    // Method to convert Status to string, returns string
    private static String getStatusAsString(BloodDonation.Status stat) throws SQLException {
        BloodDonation.Status status ;
        String statTOstr = String.valueOf(stat);
        String result = null;
        try{
            //STORED, TESTED, SUPPLIED, DISCARDED
            switch (statTOstr ){
                case "STORED":
                    result = String.valueOf(stat);

                    break;
                case "TESTED":
                    result = String.valueOf(stat);
                    break;
                case "SUPPLIED":
                    result = String.valueOf(stat);
                    break;
                case "DISCARDED":
                    result = String.valueOf(stat);
                    break;
                default:
                    result = String.valueOf(stat);
            }
        }catch (IllegalArgumentException e){
            log.info("Unknown status");
        }
        return result;
    }
    private static BloodDonation mapRow(ResultSet rs) throws SQLException {
        // Assuming "status" in the ResultSet is a string (e.g., "STORED, TESTED, SUPPLIED, DISCARDED")
        String statusString = rs.getString("status");

        // Convert the string to the corresponding Blooddonation.Status enum
         BloodDonation.Status status = null;
         //STORED, TESTED, SUPPLIED, DISCARDED
        try {
            switch (statusString.toUpperCase()){

                case "STORED":
                    status = BloodDonation.Status.STORED;
                    break;
                case "TESTED":
                    status = BloodDonation.Status.TESTED;
                    break;
                case "SUPPLIED":
                    status = BloodDonation.Status.SUPPLIED;
                    break;
                case "DISCARDED":
                    status = BloodDonation.Status.DISCARDED;
                    break;

                default:
                    status = BloodDonation.Status.STORED;

            }
        }catch (IllegalArgumentException e) {
            log.info("Unknown user role !");
            // Handle unknown role or set a default role
            //role = User.Role.DONOR;  // Default role if the value is invalid
        }
        //Donor donor = new Donor();
        Donor donor;
       int donorid = rs.getInt("donorID");
       DonorDao donorDao = new DonorDaoImpl("database.properties");
       donor = donorDao.getDonorById(donorid);
        //BloodType.Type bt = BloodType.Type.valueOf(rs.getString("bloodTypeID"));
        BloodType.BloodTypes bloodgroup = BloodType.BloodTypes.valueOf(rs.getString("bloodTypeID"));
       // BloodType.Type bloodtype = null;
       /* int typeID = 0;
        switch (bt.toString().toUpperCase()){
            case "A+":
                typeID = bloodtype.getBloodTypeID();
            case "A-":
                typeID = bloodtype.getBloodTypeID();
            case "B+":
                typeID = bloodtype.getBloodTypeID();
            case "B-":
                typeID = bloodtype.getBloodTypeID();
            case "O+":
                typeID = bloodtype.getBloodTypeID();
            case "O-":
                typeID = bloodtype.getBloodTypeID();
            case "AB+":
                typeID = bloodtype.getBloodTypeID();
            case "AB-":
                typeID = bloodtype.getBloodTypeID();
        }*/

        /*switch (bloodtype){

            case BloodType.Type.A_POS:
                typeID = bloodtype.getBloodTypeID();
            case BloodType.Type.A_NEG:
                typeID = bloodtype.getBloodTypeID();
            case BloodType.Type.B_POS:
                typeID = bloodtype.getBloodTypeID();
            case BloodType.Type.B_NEG:
                typeID = bloodtype.getBloodTypeID();
            case BloodType.Type.O_POS:
                typeID = bloodtype.getBloodTypeID();
            case BloodType.Type.O_NEG:
                typeID = bloodtype.getBloodTypeID();
            case BloodType.Type.AB_POS:
                typeID = bloodtype.getBloodTypeID();
            case BloodType.Type.AB_NEG:
                typeID = bloodtype.getBloodTypeID();
        }*/
        // Get blood type from string
        BloodType.BloodTypes bloodType = null;
        //BloodType.BloodTypes.values();
       /* for (BloodType.BloodTypes type: BloodType.BloodTypes.values()
        ) {*/
            switch (bloodgroup.getBloodType().toUpperCase()){
            //switch (type.getBloodType().toUpperCase()){
                case "A+":
                    bloodType = BloodType.BloodTypes.A_POS;
                    break;
                case "A-":
                    bloodType = BloodType.BloodTypes.A_NEG;
                    break;
                case "B+":
                    bloodType = BloodType.BloodTypes.B_POS;
                    break;
                case "B-":
                    bloodType = BloodType.BloodTypes.B_NEG;
                    break;
                case "O+":
                    bloodType = BloodType.BloodTypes.O_POS;
                    break;
                case "O-":
                    bloodType = BloodType.BloodTypes.O_NEG;
                    break;
                case "AB+":
                    bloodType = BloodType.BloodTypes.AB_POS;
                    break;
                case "AB-":
                    bloodType = BloodType.BloodTypes.AB_NEG;
                    break;
            }

        //}

       /* switch (bloodType.toString()){
            case "A+":
                bloodtype = BloodType.Type.A_POS;
            case "A-":
                bloodtype = BloodType.Type.A_NEG;
            case "B+":
                bloodtype = BloodType.Type.B_POS;
            case "B-":
                bloodtype = BloodType.Type.B_NEG;
            case "O+":
                bloodtype = BloodType.Type.O_POS;
            case "O-":
                bloodtype = BloodType.Type.O_NEG;
            case "AB+":
                bloodtype = BloodType.Type.AB_POS;
            case "AB-":
                bloodtype = BloodType.Type.AB_NEG;
            default:
                log.info("Unknown Blood Tpye.");

        }*/

        return BloodDonation.builder()
                .donationID(rs.getInt("donationID"))
                .donor(donor)
                .bloodTypeID(rs.getInt("bloodTypeID"))
                //.bloodType(bloodgroup.getBloodType())
                //.bloodType(rs.getString("bloodTypeID"))
                .quantity(rs.getDouble("quantity"))
                .collectionDate(rs.getDate("collectionDate"))
                .status(BloodDonation.Status.valueOf(rs.getString("status")))
                .build();


    }
  //###########################################################################################################

    /*private static BloodDonation mapRow(ResultSet rs) throws SQLException {
        Donor newdonor = new Donor();
        // Assuming "Type" in the ResultSet is a string (e.g., "A+" or "A-" ...)
        String typeString = rs.getString("bloodType");
        // Convert the string to the corresponding BloodTpyt.Type enum

        BloodType.Type type = null;

        try {
            switch (typeString.toUpperCase()){
                case "A+":
                    type  = BloodType.Type.A_POS;
                case "A-":
                    type  = BloodType.Type.A_NEG;
                case "B+":
                    type  = BloodType.Type.B_POS;
                case "B-":
                    type  = BloodType.Type.B_NEG;
                case "O+":
                    type  = BloodType.Type.O_POS;
                case "O-":
                    type  = BloodType.Type.O_NEG;
                case "AB+":
                    type  = BloodType.Type.AB_POS;
                case "AB-":
                    type  = BloodType.Type.AB_NEG;

            }
        }catch (IllegalArgumentException e) {
            log.info("Unknown user role !");

        }
        return BloodDonation.builder()
                .donationID(rs.getInt("donationID"))

                .donorID( newdonor.getDonorID())
                .quantity(rs.getDouble("quantity"))
                .collectionDate(rs.getDate("collectionDate"))
                .status(rs.getString("status"))
                .build();
    }*/

}
