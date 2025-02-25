package BloodBankManagementApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    @GetMapping("/")
    public String getHome(){

        return "index";
    }
    @GetMapping("/registerPage")
    public String getRegister(){
       // System.out.println("Testing );
        return "registration";
    }
    @GetMapping("/loginPage")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/landingPage")
    public String getLanding(){
        return "main";
    }
    public String getAdminDashBoard(){return "addminDashBoard";}
    @GetMapping("/userRegisterPage")
    public String getRegistrationForm(){return "registrationForm";}
    @GetMapping("/donorPage")
    public String showDonorForm() {
        return "donorForm"; } // Name of the HTML template (donorForm.htm
    }

