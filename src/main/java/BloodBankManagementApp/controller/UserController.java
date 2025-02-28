package BloodBankManagementApp.controller;

import BloodBankManagementApp.business.User;
import BloodBankManagementApp.persistence.UserDao;
import BloodBankManagementApp.persistence.UserDaoImpl;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j

@Controller
public class UserController {
    // register

   /* @PostMapping("register")
    public String registerUser(*/
   @PostMapping("addUser")
   public String registerUser(
            @RequestParam(name="username") String username,
            @RequestParam(name="email") String email,
            @RequestParam(name="password") String password,
            @RequestParam(name="password2") String password2,
            @RequestParam(name="role") String role,
            //Role userrole = Enum.EnumDesc<E>,
            //assignRole(@RequestParam("role") User.Role role),
    //@RequestParam(name="address") String address,
            //@RequestParam(name="dateOfBirth") String dateOfBirth,
            //Model model, HttpSession session) throws InvalidKeySpecException, NoSuchAlgorithmException {
            Model model,
            HttpSession session

    ){
        // VALIDATION


        // username validation
       String errorMsg = null;
        if (username.isBlank()){
            errorMsg = "Username was left blank";
            /*model.addAttribute("message", errorMsg);
            System.out.println("Username was left blank");
            //return "user_indexSignUp";
            //return  "registration";
            return  "registrationForm";

             */
        }

        Pattern usernameRegex = Pattern.compile("^[a-zA-Z]{3,25}$");
        Matcher match = usernameRegex.matcher(username);
        boolean matchfoundUsername = match.find();

        if (!matchfoundUsername){
            errorMsg = "Username must be between 3-25 characters, letters only";
           /* model.addAttribute("message", errorMsg);
            System.out.println("Username must be between 3-25 characters, letters only");

            return  "registrationForm";

            */
        }

        // email validation

        if(email.isBlank()){
            errorMsg = "Email was left blank";
            /*model.addAttribute("message", errorMsg);
            System.out.println("Email was left blank");

            return  "registrationForm";

             */
        }

        // password validation

        if (password.isBlank()){
            errorMsg = "Password was left blank";
            /*model.addAttribute("message", errorMsg);
            System.out.println("Password was left blank");

            return  "registrationForm";

             */
        }

        Pattern passwordRegex = Pattern.compile("^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@$!%?&])[A-Za-z\\d@$!%?&]{7,70}$");
        Matcher match1 = passwordRegex.matcher(password);
        boolean matchfoundPassword = match1.find();

        if (!matchfoundPassword){
            errorMsg = "Password didnt have at least 7-70 characters, one uppercase letter, one lowercase letter, one number and one special character";
            /*model.addAttribute("message", message);
            //System.out.println("Password must have at least 7 characters and maximum 70 characters, one uppercase letter, one lowercase letter and one number");

            return  "registrationForm";

             */
        }

        if (password2.isBlank()){
            errorMsg = "Confirm Password was left blank";
            /*String message5 = "Confirm Password was left blank";
            model.addAttribute("message5", message5);
            System.out.println("Confirm password was left blank");
            return  "registrationForm";

             */
        }

        if (!password.equals(password2)){
            errorMsg = "Password and Confirm Password didnt match";
            /*String message5 = "Password and Confirm Password didnt match";
            model.addAttribute("message5", message5);
            System.out.println("Passwords dont match");

            return  "registrationForm";*/
        }
       if (errorMsg != null) {
           model.addAttribute("errorMessage", errorMsg);
           //return  "registerUser";
           //return  "register";
           return "registrationForm";
       }
        String view = "";

        User.Role newUserRole;
        switch (role){
            case "admin":
                newUserRole = User.Role.ADMIN;
                break;
            case "donor":
                newUserRole = User.Role.DONOR;
                break;
            case "employee":
                newUserRole = User.Role.EMPLOYEE;
                break;
            case "hospital_Admin":
                newUserRole = User.Role.HOSPITAL_ADMIN;
                break;

            default:
                newUserRole= User.Role.DONOR;
        }
        // Build new user with the detail entered in registration form
        User newUser = User.builder()
                .userID(0)
                .username(username)
                .password(password)
                .email(email)
                .role(newUserRole)
                .build();

        //String view = "";
        UserDao userDao = new UserDaoImpl("database.properties");
        // Convert the role string to the Role enum
        //User.Role userRole = User.Role.valueOf(role.toUpperCase());
        //User.Role userRole = User.Role.DONOR;
        //User u = new User(username, password,email,userRole.name().toUpperCase());
        //User u = new User(0,username, password,email,userRole);
       User u = new User(0,username, password,email,newUserRole);
        boolean added = userDao.registerUser(u);
        if(added ){
            view = "registerSuccess";
            model.addAttribute("registeredUser", u);
            session.setAttribute("loggedInUser", u);
            log.info("User {} registered", u.getUsername());
        }else{
            view = "registerFailed";
            log.info("Registration failed with username {}", username);
        }
        return view;
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            Model model, HttpSession session) {
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

        UserDao userDao = new UserDaoImpl("database.properties");
        User loggedInUser = userDao.login(username, password);

        if (loggedInUser != null) {
            String success = "Login Successful !";
            model.addAttribute("message", success);
            // Start session for current login user
            session.setAttribute("CurrentUser", loggedInUser);
            return "index";
        } else {
            // Log Info of failed Registration Attempt with imidiate line below
            log.info("Could not register user with username: " + username + "and email: " + password + ",");

            String failed = "Username/password incorrect !";
            model.addAttribute("message", failed);
            return "login";
        }
    }
}
