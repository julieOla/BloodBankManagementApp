package BloodBankManagementApp.persistence;
import BloodBankManagementApp.business.User;
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
    public User login(String username, String password) {
        User user = null;
        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE username = ? AND passwordHash = ?")) {
            ps.setString(1, username);
            ps.setString(2, password);

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

    static User.Role role;

    private static User mapRow(ResultSet rs) throws SQLException {
        return User.builder()
                .username(rs.getString("username"))
                .password(rs.getString("passwordHash"))
                .email(rs.getString("email"))
                .role(role)
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
