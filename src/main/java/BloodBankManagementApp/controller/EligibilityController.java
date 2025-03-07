package BloodBankManagementApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class EligibilityController {


       /* @GetMapping("/")
        public String showSurveyForm() {
            return "index";
        }*/
       @PostMapping("/eligibility")
       public String handleSurveySubmission(@RequestParam String question1,
                                            @RequestParam String question2,
                                            @RequestParam String question3,
                                            @RequestParam String question4,
                                            @RequestParam String question5,
                                            @RequestParam String question6,
                                            @RequestParam String question7,
                                            @RequestParam String question8,
                                            @RequestParam String question9,
                                            @RequestParam String question10,
                                            @RequestParam String question11,
                                            @RequestParam String question12,
                                            @RequestParam String question13,
                                            @RequestParam String question14,
                                            @RequestParam String question15,
                                            Model model) {
           model.addAttribute("question1", question1);
           model.addAttribute("question2", question2);
           model.addAttribute("question3", question3);
           model.addAttribute("question4", question4);
           model.addAttribute("question5", question5);
           model.addAttribute("question6", question6);
           model.addAttribute("question7", question7);
           model.addAttribute("question8", question8);
           model.addAttribute("question9", question9);
           model.addAttribute("question10", question10);
           model.addAttribute("question11", question11);
           model.addAttribute("question12", question12);
           model.addAttribute("question13", question13);
           model.addAttribute("question14", question14);
           model.addAttribute("question15", question15);

           // Check eligibility based on answers
           boolean isEligible = question1.equals("Yes") &&
                   question2.equals("Yes") &&
                   question3.equals("Yes") &&
                   question4.equals("No") &&
                   question5.equals("No") &&
                   question6.equals("No") &&
                   question7.equals("No") &&
                   question8.equals("No") &&
                   question9.equals("No") &&
                   question10.equals("No") &&
                   question11.equals("No") &&
                   question12.equals("No") &&
                   question13.equals("No") &&
                   question14.equals("No") &&
                   question15.equals("N0");

           model.addAttribute("isEligible", isEligible);

           return "quizResult";


       }


}
