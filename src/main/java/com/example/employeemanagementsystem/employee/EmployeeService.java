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

@Service
@AllArgsConstructor
public class EmployeeService implements UserDetailsService {
    private final static String EMPLOYEE_NOT_FOUND_MSG  = "Employee with e-mail %s not found";
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    public String signUp(Employee employee) {
        boolean userExists = employeeRepository
                .findByEmail(employee.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email is taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);

        employeeRepository.save(employee);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                employee);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableEmployee(String email) {
        return employeeRepository.enableEmployee(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(EMPLOYEE_NOT_FOUND_MSG, email)
                ));
    }
    //Deleting the employee
    public void deleteEmployee(String email) {
        Employee employee = (Employee) loadUserByUsername(email);
        employeeRepository.delete(employee);//jpa has a method for deleting
    }
    public void updateEmployee(ChangeRequest changeRequest) {
        employeeRepository.update(changeRequest.getEmail(), changeRequest.getFirstName(), changeRequest.getLastName());
    }


}
