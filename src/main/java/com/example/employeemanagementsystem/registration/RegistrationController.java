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
/*
* this is our controller class which is connectd to the service layer and our frontend*/
@Controller
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;
    private ConfirmationTokenService confirmationTokenService;
    private DepartmentService departmentService;
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    //Get mapping for the confirm
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token, Model model) {
        //if the token is not confirmed show the error page
        if (!registrationService.confirmToken(token).equals("confirmed")) {
            return "error_page";
        }
        //Check if the confirmation token is there or not
        ConfirmationToken confirmedToken = confirmationTokenService.getConfirmationToken(token)
                .orElseThrow(() -> new IllegalStateException("Nothing here"));
        //if the confirmation token is true then bring the employee data
        Employee currentEmployee = confirmedToken.getEmployee();
        //getting the first name of the employee for showing it in the confirmation page
        model.addAttribute("userName", currentEmployee.getFirstName());
        return "confirmation_page";
    }
    //GetMapping method for registering
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        //showing all the departments in the registration page
        Set<String> departmentNames = departmentService.getDepartmentNames();
        //showing all the status except ADMIN so the user can choose his/her status while registering
        Set<EmployeeStatus> statusSelections=new HashSet<>();
        for(EmployeeStatus status:EmployeeStatus.values()) {
            if(!status.equals(EmployeeStatus.ADMIN)){
                statusSelections.add(status);
            }
        }
        //for showing the department name
        model.addAttribute("departments", departmentNames);
        //for choosing the status type
        model.addAttribute("selectStatusEmployee", statusSelections);
        //connecting to the RegistrationRequest object for registering
        model.addAttribute("regRequest", new RegistrationRequest());
        return "register_page";
    }
    //For posting the registration request
    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest request, Model model) {
        System.out.println("registration request " + request.getEmail());
        String token = registrationService.register(request);
        model.addAttribute("userName", request.getFirstName());
        return (token == null) ? "error_page" : "index";
    }
    //for accessing to the login page
    @GetMapping("/login")
    public String getLoginPage() {
        return "login_page";
    }
    //for accessing to the personal page after a successful login
    @GetMapping("/personal")
    public String getPersonalPage(Model model, Principal principal) {
        Employee employee = employeeRepository.findByEmail(principal.getName()).get();
        model.addAttribute("employee", employee);
        return "personal_page";
    }
    //showing the error page if the login or confirmation was not working
    @RequestMapping("/error-page.html")
    public String getErrorPage() {
        return "error_page";
    }
    //accessing the delete page for deleting an employee
    @GetMapping("/delete")
    public String getDeletePage() {
        return "delete_page";
    }
    //deleting the employee, this is only possible by the ADMIN
    @RequestMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam String deleteRequestEmail, Model model) {
        if (employeeService.checkEmailExistForDelete(deleteRequestEmail)){
            employeeService.deleteEmployee(deleteRequestEmail);
            model.addAttribute("emaildeleted", deleteRequestEmail);
            return "successful_delete";
        }else{

            return "error_page";
        }}
    //updating employee information
    @GetMapping(value="/update")
    public String getUpdatePage(Principal principal, Model model) {

        model.addAttribute("email", principal.getName());
        return "update_page";
    }
    //updating employee information based on the created object in changRequest class
    @RequestMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute ChangeRequest changeRequest) {
        Employee employee = (Employee) employeeService.loadUserByUsername(changeRequest.getEmail());
    //making sure the employee doesn't delete his own data
        if (changeRequest.getFirstName().isBlank() && changeRequest.getLastName().isBlank()) {
            return "error_page";
        } else if (changeRequest.getFirstName().isBlank()) {
            changeRequest.setFirstName(employee.getFirstName());
        } else if (changeRequest.getLastName().isBlank()) {
            changeRequest.setLastName(employee.getLastName());
        }
        employeeService.updateEmployee(changeRequest);
        return "index";
    }
    // return the show_page showing the employee data
    @GetMapping("/show_page")
    public String showPage(Model model, Principal principal){
        Employee employee = employeeRepository.findByEmail(principal.getName()).get();
        model.addAttribute("employee", employee);
        return "show_page";
    }
    // return the forbidden page
    @RequestMapping("/forbidden.html")
    public String getForbiddenPage() {
        return "forbidden";
    }
}