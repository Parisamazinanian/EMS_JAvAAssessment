package com.example.employeemanagementsystem.registration;

import com.example.employeemanagementsystem.department.Department;
import com.example.employeemanagementsystem.employee.EmployeeStatus;
import lombok.*;

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
