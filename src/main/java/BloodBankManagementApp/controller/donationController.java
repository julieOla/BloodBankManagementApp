package BloodBankManagementApp.controller;

import BloodBankManagementApp.business.BloodDonation;
import BloodBankManagementApp.business.BloodType;
import BloodBankManagementApp.business.BloodTypes;
import BloodBankManagementApp.business.Donor;
import BloodBankManagementApp.persistence.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.values;

@Slf4j
@Controller
public class donationController {
    @GetMapping("/getDonationForm")
    public String getDonationForm(Model model){
        //BloodTypes bloodTypes = null;
        // BloodTypes.values();
        List<BloodType> bloodTypes ;
        List<Donor> donors ;

        DonorDao donorDao = new  DonorDaoImpl("database.properties");
        BloodTypeDao bloodTypeDao = new BloodTypeDaoImpl("database.properties");

        donors = donorDao.getAllDonor();
        bloodTypes = bloodTypeDao.getAllBloodType();
        model.addAttribute("donors", donors);
        model.addAttribute("bloodTypes", bloodTypes);

        model.addAttribute("bloodType",BloodTypes.values());

        return "donationForm";
    }
    @PostMapping("/addDonation")
    public String addDonation(
            @RequestParam(name = "donorID")  int donorID,
            //@RequestParam(name = "donorID")  Donor donorID,
            //@RequestParam(name = "bloodType") Enum bloodType,
           // @RequestParam(name = "bloodTypeID") BloodType type,
            //@RequestParam(name = "bloodTypeID") String bloodtype,
            @RequestParam(name = "bloodTypeID") int bloodTypeID,
            @RequestParam(name = "quantity") Double quantity,
            @RequestParam(name = "collectionDate")Date collectionDate,
            @RequestParam( name = "status")BloodDonation.Status status,

            Model model
            )throws Exception{
        String errorMsg = null;
        //if (donorID.getDonorID() == 0){
        if (donorID == 0){
            errorMsg = "No donor ID selected.";
        //}if (bloodType.name().isBlank()){
        //}if (type.getBloodTypeID() == 0){
        //}if (bloodtype.isBlank()){
        }if (bloodTypeID == 0){
            errorMsg = "No BloodType selected.";
        }
        if (quantity.intValue() == 0.00){
            errorMsg = "No donor ID selected.";
        }
        LocalDate d = LocalDate.now();
        Date date = Date.valueOf(d);
        if (collectionDate.before(date) || collectionDate.after(date)){
            errorMsg = "Enter today's date.";
        }
        if (status.name().isEmpty()){
            errorMsg = "No status selected.";
        }
        if (errorMsg != null) {
            model.addAttribute("errorMessage", errorMsg);

            return "donationForm";
        }

        DonorDao donorDao = new  DonorDaoImpl("database.properties");
        Donor donor;
        donor = donorDao.getDonorById(donorID);
        // Build new donation with the detail entered in donation form
        BloodDonation newBloodDonation = BloodDonation.builder()
                //.donationID(donorID.getDonorID())
                .donor(donor)
                //.donor(donorID)
                //.bloodType(bloodtype)
                .bloodTypeID(bloodTypeID)
                //.quantity((Double) quantity)
                .quantity(quantity)
                .collectionDate(collectionDate)
                .status(status)
                .build();

        // Create access to Database
        BloodDonationDao newDonation = new  BloodDonationDaoImpl("database.properties");
        // Save donation to Database
        boolean added = newDonation.addBloodDonation(newBloodDonation);
        if (added) {
            String success = "Donation added Successfully";
            model.addAttribute("donation_Added", success);
            model.addAttribute("CurrentDonation:", newBloodDonation);
            //session.setAttribute("loggedInUser", newUser);
            //log.info("Current donation by", newUser.getUsername());
            log.info("Current donation by", newBloodDonation.getDonor().getFullName());
            return "donationForm";
        } else {
            String failed = "Add donation Failed, You may try again.";
            log.info(" Failed to add donation username {}", newBloodDonation.getDonor().getDonorID());
            model.addAttribute("errorMessage", failed);
        }

        return "donationForm";
    }
    // LIST DONATIONS METHOD
    @GetMapping("/listDonations")
    public String showListOfBloodDonations(Model model){
        List<BloodDonation> bloodDonationList ;
        BloodDonationDao bloodDonationDao = new  BloodDonationDaoImpl("database.properties");
        bloodDonationList = bloodDonationDao.getAllBloodDonation();
        model.addAttribute("bloodDonationList", bloodDonationList);
        //model.addAttribute("message", "Logout Successful");
        return "listBloodDonations";
    }
    public static void main(String[] args){
        // CustomerDao customerDao = new CustomerDaoImpl("database.properties");
        //        List<Customer> customers = customerDao.getAllCustomers();
        //        System.out.println(customers);
        //        System.out.println("------------------------------");
        //        System.out.println("Customer with id 119: " + customerDao.getById(119));
        BloodDonationDao bloodDonationDao = new BloodDonationDaoImpl("database.properties");
        List<BloodDonation>bloodDonationList ;
        bloodDonationList = bloodDonationDao.getAllBloodDonation();
        System.out.println(bloodDonationList);

    }


    //########################################################################################################
    // Convert Enum BloodType string to Blood Type
        /*BloodType.Type bloodtype = null;

        switch (bloodType.toString().toUpperCase()){
            case "A+":
                bloodtype = BloodType.Type.A_POS;
                break;
            case "A-":
                bloodtype = BloodType.Type.A_NEG;
                break;
            case "B+":
                bloodtype = BloodType.Type.B_POS;
                break;
            case "B-":
                bloodtype = BloodType.Type.B_NEG;
                break;
            case "O+":
                bloodtype = BloodType.Type.O_POS;
                break;
            case "O-":
                bloodtype = BloodType.Type.O_NEG;
                break;
            case "AB+":
                bloodtype = BloodType.Type.AB_POS;
                break;
            case "AB-":
                bloodtype = BloodType.Type.AB_NEG;
                break;
            default:
                log.info("Unknown Blood Tpye.");

        }*/

}
