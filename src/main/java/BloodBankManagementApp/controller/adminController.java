package BloodBankManagementApp.controller;

import BloodBankManagementApp.business.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminController {
    /*@GetMapping("/adminDashBoard")

    public  String getAdminUser(Model model, HttpSession session){

        User.Role adminUser = null;
        User currentUser = (User) session.getAttribute("CurrentUser");
        if (currentUser != null){
            User.Role role = currentUser.getRole();
            switch (role.toString().toUpperCase()){
                case "ADMIN":
             adminUser = User.Role.ADMIN;
               break;
                default:
                    System.out.println("Admin role only");
            }
            String greeting = "You welcome to Admin Page";
            model.addAttribute("greeting", greeting);
            session.setAttribute("role",adminUser);

            return "admin-dashboard";
        }

        return "landing";
    }*/
}
