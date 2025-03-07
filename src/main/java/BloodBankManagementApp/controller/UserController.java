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

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Slf4j

@Controller
public class UserController {

    //private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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

        }

        Pattern usernameRegex = Pattern.compile("^[a-zA-Z]{3,25}$");
        Matcher match = usernameRegex.matcher(username);
        boolean matchfoundUsername = match.find();

        if (!matchfoundUsername){
            errorMsg = "Username must be between 3-25 characters, letters in length";

        }

        // email validation

        if(email.isBlank()) {
            errorMsg = "Email was left blank";
        }

        // password validation

        if (password.isBlank()){
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

        if (password2.isBlank()){
            errorMsg = "Confirm Password was left blank";

        }

        if (!password.equals(password2)){
            errorMsg = "Password and Confirm Password didnt match";

        }
       if (errorMsg != null) {
           model.addAttribute("errorMessage", errorMsg);

           return "registrationForm";
       }

        // Convert role form  form input (text/string) to Enum
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
        // Hash the password before saving to database
           //String hashedPassword = passwordEncoder.encode(password);
       // Save the user with hashed password (e.g., userService.save(new User(username, email, hashedPassword, role)))

       // Build new user with the detail entered in registration form
        User newUser = User.builder()
                //.userID()
                .username(username)
                .password(password)
               // .password(hashedPassword)
                .email(email)
                .role(newUserRole)
                .build();

        //String view = null;
        UserDao userDao = new UserDaoImpl("database.properties");

       /*User u = new User(0,username, password,email,newUserRole);

        boolean added = userDao.registerUser(u);*/
       boolean added = userDao.registerUser(newUser);
        if(added ){
            String success = "registerSuccess";
            model.addAttribute("registered", success);
            session.setAttribute("loggedInUser", newUser);
            log.info("User {} registered", newUser.getUsername());
            return "login";
        }else{
            String failed = "registerFailed, You may try again.";
            log.info("Registration failed with username {}", username);
            model.addAttribute("errorMessage", failed);
        }
        //return view;
       return "registrationForm";
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
