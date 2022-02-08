package com.example.employeemanagementsystem.registration;


import com.example.employeemanagementsystem.employee.EmployeeStatus;
import lombok.*;
//This is used for the registering a user based on the following information
//we can create an object of this class in our controller
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String departmentName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private EmployeeStatus employeeStatus;
}
