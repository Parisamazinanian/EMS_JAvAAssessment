package com.example.employeemanagementsystem.registration;

import com.example.employeemanagementsystem.department.DepartmentService;
import com.example.employeemanagementsystem.employee.*;
import com.example.employeemanagementsystem.security.token.ConfirmationToken;
import com.example.employeemanagementsystem.security.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;;
import java.util.Set;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;
    private ConfirmationTokenService confirmationTokenService;
    private DepartmentService departmentService;
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token, Model model) {
        if (!registrationService.confirmToken(token).equals("confirmed")) {
            return "error_page";
        }
        ConfirmationToken confirmedToken = confirmationTokenService.getConfirmationToken(token)
                .orElseThrow(() -> new IllegalStateException("Nothing here"));
        Employee currentEmployee = confirmedToken.getEmployee();

        model.addAttribute("userName", currentEmployee.getFirstName());
        return "confirmation_page";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        Set<String> departmentNames = departmentService.getDepartmentNames();
        Set<EmployeeStatus> statusSelections=new HashSet<>();
        for(EmployeeStatus status:EmployeeStatus.values()) {
            if(!status.equals(EmployeeStatus.ADMIN)){
                statusSelections.add(status);
            }
        }

        model.addAttribute("departments", departmentNames);
        model.addAttribute("selectStatusEmployee", statusSelections);
        model.addAttribute("regRequest", new RegistrationRequest());
        return "register_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest request, Model model) {
        System.out.println("registration request " + request.getEmail());
        String token = registrationService.register(request);
        model.addAttribute("userName", request.getFirstName());
        return (token == null) ? "error_page" : "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login_page";
    }

    @GetMapping("/personal")
    public String getPersonalPage() {
        return "personal_page";
    }

    @RequestMapping("/error-page.html")
    public String getErrorPage() {
        return "error_page";
    }

    @GetMapping("/delete")
    public String getDeletePage() {
        return "delete_page";
    }

    @RequestMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam String deleteRequestEmail) {
        employeeService.deleteEmployee(deleteRequestEmail);
        return "index";
    }
    @GetMapping(value="/update")
    public String getUpdatePage(Principal principal, Model model) {
        // System.out.println("email is: "+ email);
        //Employee employee = employeeRepository.findByEmail(email).get();
        model.addAttribute("email", principal.getName());
        return "update_page";
    }

    @RequestMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute ChangeRequest changeRequest) {
        System.out.println(changeRequest.getEmail());
        employeeService.updateEmployee(changeRequest);
        return "index";
    }


}
