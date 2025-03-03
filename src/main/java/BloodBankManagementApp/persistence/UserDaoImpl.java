package BloodBankManagementApp.persistence;
import BloodBankManagementApp.business.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

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
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO users VALUES( ?, ?,?,?)")) {
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

    @Override
    public User login(String username, String password) {
        return null;
    }
}
