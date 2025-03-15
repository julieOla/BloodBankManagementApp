package BloodBankManagementApp.persistence;
import BloodBankManagementApp.business.User;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class UserDaoImpl extends MySQLDao implements UserDao{

    public UserDaoImpl(String propertiesFile) {
        super(propertiesFile);
    }

    public UserDaoImpl(Connection c) {
        super(c);
    }

    public UserDaoImpl() {
        super();
    }
    @Override
    public boolean registerUser(User user) {
        boolean added = false;

        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO users (username, passwordHash, email, role) VALUES( ?, ?,?,?)")) {
            //ps.setInt(1, user.getUserID());
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole().toString());


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

    /*@Override
    public User login(String username, String password) {
        return null;
    }*/
    @Override
    public User loginByUsername(String username) {
        User user = null;
        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE username = ? ")) {
            ps.setString(1, username);


            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    user = mapRow(rs);
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing login query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to login User", e);
        }
        super.freeConnection(c);

        return user;
    }
    @Override
    public User getUserByUserName(String username) {
        User user = null;
        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE username = ? ")) {
            ps.setString(1, username);


            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    user = mapRow(rs);
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing login query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to login User", e);
        }
        super.freeConnection(c);

        return user;
    }

    //static User.Role role;

    private static User mapRow(ResultSet rs) throws SQLException {
        // Assuming "role" in the ResultSet is a string (e.g., "ADMIN" or "USER")
        String roleString = rs.getString("role");

        // Convert the string to the corresponding User.Role enum
        User.Role userRole = null;

        try {
            switch (roleString.toUpperCase()){

                case "DONOR":
                    userRole=User.Role.DONOR;
                    break;
                case "EMPLOYEE":
                    userRole=User.Role.EMPLOYEE;
                    break;
                case "ADMIN":
                    userRole=User.Role.ADMIN;
                    break;
                case "HOSPITAL":
                    userRole=User.Role.HOSPITAL_ADMIN;
                    break;
                default:
                    userRole = User.Role.DONOR;

            }
        }catch (IllegalArgumentException e) {
            log.info("Unknown user role !");
            // Handle unknown role or set a default role
            //role = User.Role.DONOR;  // Default role if the value is invalid
        }

        return User.builder()
                .userID(rs.getInt("userID"))
                .username(rs.getString("username"))
                .password(rs.getString("passwordHash"))
                .email(rs.getString("email"))
                .role(userRole)
                .build();
    }


    private boolean  validUser(User u){
        if(u == null){
            return false;
        }
        if(u.getUsername() == null || u.getUsername().isBlank()){
            return false;
        }
        if(u.getPassword() == null || u.getPassword().isBlank()){
            return false;
        }
        if(u.getEmail() == null || u.getEmail().isBlank()){
            return false;
        }
        return true;
    }


}
