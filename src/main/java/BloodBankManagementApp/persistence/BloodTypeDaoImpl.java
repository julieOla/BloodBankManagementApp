package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.BloodType;
import BloodBankManagementApp.business.BloodTypes;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import static BloodBankManagementApp.business.BloodType.Type.*;

@Slf4j
public class BloodTypeDaoImpl extends MySQLDao implements BloodTypeDao{
    public BloodTypeDaoImpl (String propertiesFile) {
        super(propertiesFile);
    }

    public BloodTypeDaoImpl(Connection c) {
        super(c);
    }

    public BloodTypeDaoImpl() {
        super();
    }
    /*public boolean addBloodType(BloodType bloodtype){
        boolean added = false;
        Connection c = super.getConnection();
        //User donorUser =
        //String toAdd = getDatabaseBloodType(bloodtype.getBloodType());
        BloodType.Type type = BloodType.Type.valueOf(getDatabaseBloodType(bloodtype.getBloodType()));
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO bloodtypes ( bloodType) VALUES(?)")) {
            ps.setString(1, type.name().toUpperCase());

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
    @Override
    public List<BloodType> getAllBloodType() {
    //public List<BloodTypes> getAllBloodType() {
        List<BloodType> listOfBloodTypes = new ArrayList<>();

        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT * FROM bloodtypes ")) {
        //try (PreparedStatement ps = c.prepareStatement("SELECT bloodTypeID, bloodType FROM bloodtypes ")) {
        try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    listOfBloodTypes.add(mapRow(rs));
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to execute get donors query", e);
        }
        super.freeConnection(c);

        return listOfBloodTypes;
        //return null;
    }

    @Override
    public BloodType getBloodTypeByGroupName(BloodType type) {
        BloodType bgroup = null;
        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT bloodType FROM bloodtypes WHERE bloodType = ? ")) {
            ps.setString(1, String.valueOf(type));


            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    bgroup = mapRow(rs);
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to get donor", e);
        }
        super.freeConnection(c);

        return bgroup;

    }

    @Override
    public BloodType getBloodTypeByGroupID(int groupID) {
        BloodType group = null;
        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT bloodType FROM bloodtypes WHERE bloodTypeID = ? ")) {
            ps.setInt(1, groupID);


            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    group = mapRow(rs);
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to get donor", e);
        }
        super.freeConnection(c);

        //return bgroup.getBloodType();
        //return group.getBloodType();
        return group;

    }
    private static BloodType mapRow(ResultSet rs) throws SQLException {
    //private static BloodTypes mapRow(ResultSet rs) throws SQLException {
        //BloodType type = new BloodType(
               //rs.getInt("bloodTypeID"),
                //rs.getString("bloodType")
        //return BloodTypes.builder()
        return BloodType.builder()
                .bloodTypeID(rs.getInt("bloodTypeID"))
                .bloodType(rs.getString("bloodType"))
                //.bloodType(BloodType.Type.valueOf(rs.getString("bloodType")))
                .build();
    }
    /*private static BloodType mapRow(ResultSet rs) throws SQLException {
        BloodType type = new BloodType(
                rs.getInt("bloodTypeID"),
                //rs.unwrap(rs.getObject("bloodTypeID", BloodType.Type.valueOf(rs.getString("bloodType")))));
                rs.getString("bloodType"),
        // Assuming "role" in the ResultSet is a string (e.g., "ADMIN" or "USER")
        //String typeString = rs.getString("bloodType");
        // Convert the string to the corresponding BloodType.Bloodtype enum
        //BloodType.bloodType group = null;
        BloodType.Type blood_type = null;

        try {
            switch (typeString.toUpperCase()){

                case "A_POS":
                    blood_type= BloodType.Type.A_POS ;
                    break;
                case "A_NEG":
                    blood_type = BloodType.Type.A_NEG;
                    break;
                case "B_POS":
                    blood_type = BloodType.Type.B_POS;
                    break;
                case "B_NEG":
                    blood_type = BloodType.Type.B_NEG;
                    break;
                case "O_POS":
                    blood_type = BloodType.Type.O_POS;
                    break;
                case "O_NEG":
                    blood_type = BloodType.Type.O_NEG;
                    break;
                case "AB_POS":
                    blood_type = BloodType.Type.AB_POS;
                    break;
                case "AB_NEG":
                    blood_type = BloodType.Type.AB_NEG;
                    break;
                default:


            }
        }catch (IllegalArgumentException e) {
            log.info("Unknown user role !");
            // Handle unknown role or set a default role
            //role = User.Role.DONOR;  // Default role if the value is invalid
        }

        //return BloodType.builder()
        return BloodType.builder()
                .bloodTypeID(rs.getInt("bloodTypeID"))
                //.bloodType(group)
                //.bloodType(rs.getString("bloodType"))
                .bloodType(rs.getString("bloodType"))
                .build();
    }*/
    /*private static String getDatabaseBloodType(BloodType bloodtype){
        String typeToString = null;
        try{
            switch (bloodtype.toString().toUpperCase()){
                case A_POS: typeToString = "A+";
                break;
                case A_NEG: typeToString = "A-";
                case B_POS: typeToString ="B+";
                case B_NEG: typeToString = "B-";
                case O_POS: typeToString = "O+";
                case O_NEG: typeToString = "O-";
                case AB_POS: typeToString = "AB+";
                case AB_NEG: typeToString = "AB-";
                default:

                    throw new IllegalArgumentException("Unknown blood type: " + bloodtype);

            }

        }catch (IllegalArgumentException e){
            log.info("Unknown blood type ! ");
        }
        return typeToString;

    }*/

}
