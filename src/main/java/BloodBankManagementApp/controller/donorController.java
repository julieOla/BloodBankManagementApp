package BloodBankManagementApp.controller;

import BloodBankManagementApp.business.Donor;
import BloodBankManagementApp.business.User;
import BloodBankManagementApp.persistence.DonorDao;
import BloodBankManagementApp.persistence.DonorDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
@Slf4j
@Controller
public class donorController {

    @PostMapping("addDonor")
    public String addDonor(
            @RequestParam(name = "fullName") String fullName,
            @RequestParam(name="dateOfBirth") Date dateOfBirth,
            @RequestParam(name="contactNumber") String contactNumber,
            @RequestParam(name="address") String address,

            Model model
    ){
        String errorMsg = null;
        if (fullName == null || fullName.isBlank()) {
            errorMsg = "You cannot register without fullname";
        } else if (dateOfBirth == null || !isValidateAge(dateOfBirth)) {
            errorMsg = "You must be 18 and above to become a donor";
        } else if (contactNumber == null || contactNumber.isBlank() || !isValidContactNumber(contactNumber)) {
            errorMsg = "Your contact number must be a local number !";
        } else if (address == null || address.isEmpty()) {
            errorMsg = "Your contact Address is required";
        }
        if (errorMsg != null) {
            model.addAttribute("errorMessage", errorMsg);

            return "donorForm";
        }
        User user = new User();
        // Build new user with the detail entered in registration form
        Donor newDonor = Donor.builder()
                .userID(user.getUserID())
                .fullName(fullName)
                .dateOfBirth(dateOfBirth)
                .contactNumber(contactNumber)
                .address(address)
                .build();


        // create new dao and register new user
        DonorDao donorDao = new DonorDaoImpl("database.properties");
        boolean donorAdded = donorDao.addDonor(newDonor);
        if (donorAdded) {
            String success = "Registration Successful";
            model.addAttribute("message", success);
            return "index";
        } else {
            // Log Info of failed Registration Attempt with imidiate line below
            //log.info("Could not register user with username: " + username + "and email: " + email + ",");
            String failed = "could not register at this time";
            model.addAttribute("errorMessage", failed);
        }
        //return "register";
        return "donorForm";

    }
    public  boolean isValidateAge(Date date){
        LocalDate today  = LocalDate.now();
        // Convert Date to LocalDate
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if(today.getYear() - localDate.getYear() < 18){
            return false;
        }
        return true;

    }
    public boolean isValidContactNumber(String contactNumber){

        String firstFour = "+535";
        if(contactNumber.startsWith(firstFour) == true){
            return true;
        }
        return  false;
    }
}
