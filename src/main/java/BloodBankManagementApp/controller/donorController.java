package BloodBankManagementApp.controller;

import BloodBankManagementApp.business.Donor;
import BloodBankManagementApp.business.User;
import BloodBankManagementApp.persistence.DonorDao;
import BloodBankManagementApp.persistence.DonorDaoImpl;
import BloodBankManagementApp.persistence.UserDao;
import BloodBankManagementApp.persistence.UserDaoImpl;
import BloodBankManagementApp.utility.DateValidation;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.attoparser.dom.Text;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Controller
public class donorController {
DateValidation validation = new DateValidation();
    @PostMapping("addDonor")
    public String addNewDonor(
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name="dateOfBirth") Date dateOfBirth,
            @RequestParam(name="contactNumber") String contactNumber,
            @RequestParam(name="address") String address,

            Model model, HttpSession session
    )throws Exception{
        String errorMsg = null;
        if (firstName == null || firstName.isBlank()) {
            errorMsg = "You cannot register without firstname";
        }else if (lastName == null || lastName.isBlank()) {
            errorMsg = "You cannot register without lastname";
        }

       /* else if (dateOfBirth == null || !isValidAge(dateOfBirth)) {
            errorMsg = "You must be 18 and above to become a donor";
        }*/ else if (contactNumber == null || contactNumber.isBlank() || !isValidContactNumber(contactNumber)) {
            errorMsg = "Your contact number must be a local number !";
        } else if (address == null || address.isEmpty()) {
            errorMsg = "Your contact Address is required";
        }
        if (errorMsg != null) {
            model.addAttribute("errorMessage", errorMsg);

            return "donorForm";
        }
        String userName = firstName.concat(lastName);
        UserDao userDao = new UserDaoImpl("database.properties");
        User userDonor = userDao.getUserByUserName(userName);
        // Build new user with the detail entered in registration form
        Donor newDonor = Donor.builder()
                .userID(userDonor.getUserID())
                .fullName(userName)
                .dateOfBirth(dateOfBirth)
                .contactNumber(contactNumber)
                .address(address)
                .build();


        // create new dao and register new user
        DonorDao donorDao = new DonorDaoImpl("database.properties");
        boolean donorAdded = donorDao.addDonor(newDonor);
        if (donorAdded) {
            String success = "Donor added Successful";
            model.addAttribute("message", success);
            return "index";
        } else {

            log.info("Could not add this donor : " + userName );
            String failed = "could not add donor to database, Try again";
            model.addAttribute("errorMessage", failed);
        }
        //return "register";
        return "donorForm";

    }
    static boolean isValidAge(java.util.Date date) throws Exception{
        LocalDate currentDate = LocalDate.now();

        // date = new Date();
        LocalDate local = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int age = Period.between( local, currentDate ).getYears();
        // return age;

        boolean isEligibleAge = false;
        if(age > 17 && age < 66){
            isEligibleAge = true;
        }
        return isEligibleAge;
        // return (Period.between( currentDate , local).getYears());
    }
    /*private   boolean isValidateAge(Date date)throws IllegalArgumentException{
        LocalDate today  = LocalDate.now();
        // Convert Date to LocalDate
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if((today.getYear() - localDate.getYear()) < 18){
            return false;
        }
        return true;

    }*/
    private boolean isValidContactNumber(String contactNumber)throws IllegalArgumentException{

        String firstFour = "+353";
        if(contactNumber.startsWith(firstFour) == true){
            return true;
        }
        return  false;
    }
    // List Donors
    @GetMapping("/viewDonors")
    public String showListOfDonors(Model model){
        List<Donor> donors ;
        DonorDao donorDao = new DonorDaoImpl("database.properties");
        donors = donorDao.getAllDonor();
        model.addAttribute("donors", donors);
        return "listDonors";
    }
    /*@GetMapping("/editDonor")
    public String getDonorDetail(@RequestParam(name = "donorID") int donorID, Model model){
        model.addAttribute("donorID", donorID);
        return "editDonor";

    }*/
   /* @GetMapping("/editDonor")
    public String showEditDonorForm(@RequestParam int donorID, Model model){
        DonorDao donorDao = new DonorDaoImpl("database.properties");
        Donor donor = donorDao.getDonorById(donorID);
        model.addAttribute("donorID", donorID);
        return "editDonor";

    }*/
    @GetMapping("/editDonor")
    public String showEditDonorForm(@RequestParam int donorID, Model model){
        DonorDao donorDao = new DonorDaoImpl("database.properties");
        Donor donor = donorDao.getDonorById(donorID);
        //model.addAttribute("donorID", donorID);
        if (donor == null){
            return "redirect:/listDonors";
        }
        Donor newDonor =new Donor();
        //newDonor.setDonorID(donor.getDonorID());
        newDonor.setFullName(donor.getFullName());
        newDonor.setContactNumber(donor.getContactNumber());
        newDonor.setAddress(donor.getAddress());
        newDonor.setDateOfBirth(donor.getDateOfBirth());

        model.addAttribute("donor", donor);
        model.addAttribute("newDonor", newDonor);

        return "editDonor";

    }
    @PostMapping("/updateDonor")
    public String updateDonorDetail(
            @RequestParam int id,
            @RequestParam(name="contactNumber") String contactNumber,
            @RequestParam(name="address") Text address,

            Model model, HttpSession session
    )throws Exception{
        String errorMsg = null;
        if (contactNumber == null || contactNumber.isBlank() || !isValidContactNumber(contactNumber)) {
            errorMsg = "Your contact number must be a local number !";
        } else if (address == null || address == null) {
            errorMsg = "Your contact Address is required";
        }
        if (errorMsg != null) {
            model.addAttribute("errorMessage", errorMsg);

            return "donorForm";
        }
        /*UserDao userDao = new UserDaoImpl("database.properties");

        // Build new user with the detail entered in registration form
        Donor newDonor = Donor.builder()
                .userID(userDonor.getUserID())
                .fullName(userName)
                .dateOfBirth(dateOfBirth)
                .contactNumber(contactNumber)
                .address(address)
                .build();
        */

        // create new dao and register new user
        DonorDao donorDao = new DonorDaoImpl("database.properties");
        boolean donorUpdated = donorDao.updateDonorDetails(id, contactNumber, address);
        if (donorUpdated) {
            String success = "Donor updated Successful";
            model.addAttribute("message", success);
            return "index";
        } else {

            log.info("Could not update this donor : ");
            String failed = "could not update donor in database, Try again";
            model.addAttribute("errorMessage", failed);


            return "landing";
        }
    }
}
