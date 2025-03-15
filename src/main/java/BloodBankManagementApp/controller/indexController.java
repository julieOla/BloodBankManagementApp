package BloodBankManagementApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    @GetMapping("/")
    public String getHome(){

        return "index";
    }
    @GetMapping("/quizPage")
    public String getQuizPage() {
        return "quiz";  // This returns the quiz.html template
    }
    @GetMapping("/quizResultPage")
    public String getQuizResultPage() {
        return "quizResult";  // This returns the quizResult.html template
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

        return "landing";
    }
    @GetMapping("/adminDashBoard")
    public String getAdminDashBoard(){return "admin-dashboard";}
    @GetMapping("/userRegisterPage")
    public String getRegistrationForm(){return "registrationForm";}
    @GetMapping("/addDonorPage")
    public String showDonorForm() {
        return "donorForm"; } // Name of the HTML template (donorForm.htm
    }

