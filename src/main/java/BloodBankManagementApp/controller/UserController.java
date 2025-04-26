package BloodBankManagementApp.controller;

import BloodBankManagementApp.business.User;
import BloodBankManagementApp.persistence.UserDao;
import BloodBankManagementApp.persistence.UserDaoImpl;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BloodBankManagementApp.utility.Utility;

import javax.crypto.SecretKey;
//import org.mindrot.jbcrypt.BCrypt;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Controller class for handling user-related operations such as registration,
 * login, and logout in the Blood Bank Management Application.
 */
@Slf4j

@Controller
public class UserController {
    /**
     * Handles user registration form submission.
     * Validates form fields, hashes the password, creates a new User object,
     * and stores it in the database.
     *
     * @param username  the username submitted by the user
     * @param email     the email address submitted by the user
     * @param password  the password submitted by the user
     * @param password2 the password confirmation
     * @param role      the role selected by the user (ADMIN, DONOR, EMPLOYEE, HOSPITAL_ADMIN)
     * @param model     Spring Model to pass attributes to the view
     * @param session   HttpSession to store session data
     * @return redirect to login page if registration is successful,
     *         otherwise returns the registration form view with error message
     * @throws Exception in case of hashing or persistence errors
     */

    @PostMapping("addUser")
    public String registerUser(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "password2") String password2,
            @RequestParam(name = "role") String role,

            Model model,
            HttpSession session

    ) throws Exception {
        // VALIDATION


        // username validation
        String errorMsg = null;
        if (username.isBlank()) {
            errorMsg = "Username was left blank";

        }
        /*
        Pattern usernameRegex = Pattern.compile("^[a-zA-Z]{3,25}$");
        Matcher match = usernameRegex.matcher(username);
        boolean matchfoundUsername = match.find();

        if (!matchfoundUsername) {
            errorMsg = "Username must be between 3-25 characters, letters in length";

        }*/

        // email validation

        if (email.isBlank()) {
            errorMsg = "Email was left blank";
        }

        // password validation

        if (password.isBlank()) {
            errorMsg = "Password was left blank";

        }

        /*Pattern passwordRegex = Pattern.compile("^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@$!%?&])[A-Za-z\\d@$!%?&]{7,70}$");
        Matcher match1 = passwordRegex.matcher(password);
        boolean matchfoundPassword = match1.find();

        if (!matchfoundPassword){
            errorMsg = "Password didnt have at least 7-70 characters, one uppercase letter, one lowercase letter, one number and one special character";
            /*model.addAttribute("message", message);
            //System.out.println("Password must have at least 7 characters and maximum 70 characters, one uppercase letter, one lowercase letter and one number");

            return  "registrationForm";

             */
        /* }*/

        if (password2.isBlank()) {
            errorMsg = "Confirm Password was left blank";

        }

        if (!password.equals(password2)) {
            errorMsg = "Password and Confirm Password didnt match";

        }
        if (errorMsg != null) {
            model.addAttribute("errorMessage", errorMsg);

            return "registrationForm";
        }

        // Convert role form  form input (text/string) to Enum
        User.Role newUserRole;
        switch (role) {
            case "ADMIN":
                newUserRole = User.Role.ADMIN;
                break;
            case "DONOR":
                newUserRole = User.Role.DONOR;
                break;
            case "EMPLOYEE":
                newUserRole = User.Role.EMPLOYEE;
                break;
            case "HOSPITAL_ADMIN":
                newUserRole = User.Role.HOSPITAL_ADMIN;
                break;

            default:
                newUserRole = User.Role.DONOR;
        }
        // Hash the password before saving to database
        // Hash the password wby calling the method from utility
        // Encrypt the plaintext
        String hashedPassword = Utility.hashPassword(password);

        // Build new user with the detail entered in registration form
        User newUser = User.builder()
                //.userID()
                .username(username)
                //.password(password)
                .password(hashedPassword)
                //.password(encryptedText)
                .email(email)
                .role(newUserRole)
                .build();

        //String view = null;
        UserDao userDao = new UserDaoImpl("database.properties");

       /*User u = new User(0,username, password,email,newUserRole);

        boolean added = userDao.registerUser(u);*/
        boolean added = userDao.registerUser(newUser);
        if (added) {
            String success = "registerSuccess";
            model.addAttribute("registered", success);
            session.setAttribute("loggedInUser", newUser);
            log.info("User {} registered", newUser.getUsername());
            return "login";
        } else {
            String failed = "registerFailed, You may try again.";
            log.info("Registration failed with username {}", username);
            model.addAttribute("errorMessage", failed);
        }
        //return view;
        return "registrationForm";
    }

    /**
     * Handles user login.
     * Validates credentials, checks hashed password, and starts session on success.
     *
     * @param username the username submitted by the user
     * @param password the password submitted by the user
     * @param model    Spring Model to pass attributes to the view
     * @param session  HttpSession to store session data
     * @return redirect to landing page if login is successful, otherwise return to login page
     * @throws Exception if any hashing or database error occurs
     */

    @PostMapping("/login")
    public String login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            Model model, HttpSession session) throws Exception {
        String errorMsg = null;
        if (username.isBlank() || username == null) {
            errorMsg = "Username and password cannot be blank";

        } else if (password.isBlank() || password == null) {
            errorMsg = "Username and password cannot be blank";

        }
        if (errorMsg != null) {
            model.addAttribute("errorMessage", errorMsg);
            return "login";
        }


        // Hash the password before comparing with that from database
        //Hash the password why calling the method from utility

        UserDao userDao = new UserDaoImpl("database.properties");
        // Retrieve user details from databse
        User loggedInUser = userDao.loginByUsername(username);
        // Store users hashed password in a variable
        String storedHash = loggedInUser.getPassword();
        // User.Role role = loggedInUser.getRole(); // Store user's role from db
        //Checks plain_text passwod from login form is same with Hashed password stored in database
        boolean pwd_same = Utility.checkPassword(password, storedHash);
        // If user exist and plainText and storeHashedPassword are the same
        if ((loggedInUser != null) && (pwd_same)) {

            String success = "Login Successful !";
            model.addAttribute("message", success);
            // Start session for current login user
            session.setAttribute("CurrentUser", loggedInUser); // Sets loggedInUser as current session user
            //return "index";
            return "landing";


        } else {
            // Log Info of failed Registration Attempt with imidiate line below
            log.info("Could not login user with username: " + username + "and password: " + password + ",");

            String failed = "Username/password incorrect !";

            model.addAttribute("message", failed);
            //return "login";
        }
        return "login";
    }
        /*if ((loggedInUser != null) && (pwd_same)) {

            String success = "Login Successful !";
            model.addAttribute("message", success);
            // Start session for current login user
            session.setAttribute("CurrentUser", loggedInUser); // Sets loggedInUser as current session user
            //return "index";
            return "landing";


        }
        if((loggedInUser.getRole() == User.Role.ADMIN) && ((loggedInUser != null) && (pwd_same)) ){

            String success = "Login Successful !";
            model.addAttribute("message", success);
            // Start session for current login user
            session.setAttribute("AdminUser", loggedInUser);

            return "admin-dashboard";

        }
        return "login";
    }*/

     //if (loggedInUser.getRole() == User.Role.ADMIN){}


      /*  UserDao userDao = new UserDaoImpl("database.properties");

        User user  = userDao.login(username, password);

        if (user == null){

            return "error";
        }

        return "index";
    }*/



    // LOGOUT METHOD
    /**
     * Handles user logout.
     * Ends the current session and returns to the index page.
     *
     * @param model   Spring Model to pass attributes to the view
     * @param session HttpSession to clear user data
     * @return redirect to index page after logout
     */
    @GetMapping("/logout")
    public String logout(Model model, HttpSession session){
        session.setAttribute("CurrentUser", null); // Sets current user to null
        model.addAttribute("message", "Logout Successful");
        return "index";
    }

}
