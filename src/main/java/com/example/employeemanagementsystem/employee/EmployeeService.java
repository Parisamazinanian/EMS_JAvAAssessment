package com.example.employeemanagementsystem.employee;

import com.example.employeemanagementsystem.security.token.ConfirmationToken;
import com.example.employeemanagementsystem.security.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
/*
* EmployeeService class for implementing the logics
* This class is implementing the UserDetailsService interface
*/
@Service
@AllArgsConstructor//Lombok's annotation for creating constructor
public class EmployeeService implements UserDetailsService {
    //Creating a String for using it in the error message
    private final static String EMPLOYEE_NOT_FOUND_MSG  = "Employee with e-mail %s not found";
    //Connecting the Service layer to the Repository layer
    private final EmployeeRepository employeeRepository;
    //Using BCryptPasswordEncoder already existed class for encoding the password
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //Connecting to the ConfirmationTokenService to generate a token
    private final ConfirmationTokenService confirmationTokenService;

    //Creating a singUp method
    public String signUp(Employee employee) {
        //check if the email is existed or not form repository
        boolean userExists = employeeRepository
                .findByEmail(employee.getEmail())
                .isPresent();

        if (userExists) {
            //if it exits it will stop at this point
            throw new IllegalStateException("email is taken");
        }
        //if the email does not exists, It will continue with encoding the provided password by the user
        String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());
        //setting the password
        employee.setPassword(encodedPassword);
        //save the user password, save method already exists in the interface
        employeeRepository.save(employee);
        //this part is for generating a token (which is a random string) for email validation otherwise enabled field will be false
        //generate the token after saving the password in db by UUID already existed class
        String token = UUID.randomUUID().toString();
        //confirmation token with an instance of the class
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),//the time of confirmation
                LocalDateTime.now().plusMinutes(15),//token should be expired after 15 minutes
                employee);
        //save the confirmation token information
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }
    //enable the employee
    public int enableEmployee(String email) {
        return employeeRepository.enableEmployee(email);
    }
    ///after registration we want to check if the user exists of not
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(EMPLOYEE_NOT_FOUND_MSG, email)
                ));
    }
    //Deleting the employee, this method is connected to the controller
    public void deleteEmployee(String email) {
        Employee employee = (Employee) loadUserByUsername(email);
        employeeRepository.delete(employee);//jpa has a method for deleting
    }
    //Updating the employee information
    public void updateEmployee(ChangeRequest changeRequest) {
        employeeRepository.update(changeRequest.getEmail(), changeRequest.getFirstName(), changeRequest.getLastName());
    }

    //create a boolean to check if the email is there or not, we used this method
    //in the RegistrationController class
    public boolean checkEmailExistForDelete(String email){
        if (employeeRepository.findByEmail(email).isPresent()){
            return true;
        }else{
            return false;}
    }
}
