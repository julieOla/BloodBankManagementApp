package BloodBankManagementApp.persistence;
import BloodBankManagementApp.business.User;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
/**
 * Implementation of the {@link UserDao} interface for handling user-related
 * database operations in a MySQL database for the Blood Bank Management application.
 * <p>
 * This class supports user registration, login, and retrieval by username.
 */
@Slf4j
public class UserDaoImpl extends MySQLDao implements UserDao{
    /**
     * Constructor that initializes the DAO with database configuration from a properties file.
     *
     * @param propertiesFile the path to the database properties file.
     */
    public UserDaoImpl(String propertiesFile) {
        super(propertiesFile);
    }
    /**
     * Constructor that initializes the DAO with a given SQL connection.
     *
     * @param c a {@link Connection} object.
     */
    public UserDaoImpl(Connection c) {
        super(c);
    }

    /**
     * Default constructor that uses the default database configuration.
     */
    public UserDaoImpl() {
        super();
    }

    /**
     * Registers a new user in the database.
     *
     * @param user the {@link User} object containing user details.
     * @return {@code true} if the user was successfully added; {@code false} otherwise.
     */
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
    /**
     * Logs in a user by their username.
     *
     * @param username the username to log in with.
     * @return the {@link User} object if a matching record is found; {@code null} otherwise.
     */
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
    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user.
     * @return the {@link User} object if found; {@code null} otherwise.
     */
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
    /**
     * Maps a row from the {@link ResultSet} to a {@link User} object.
     *
     * @param rs the result set containing user data.
     * @return the constructed {@link User} object.
     * @throws SQLException if any SQL errors occur during field extraction.
     */

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

    /**
     * Validates a {@link User} object by checking that its fields are not null or blank.
     *
     * @param u the user object to validate.
     * @return {@code true} if the user is valid; {@code false} otherwise.
     */
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
