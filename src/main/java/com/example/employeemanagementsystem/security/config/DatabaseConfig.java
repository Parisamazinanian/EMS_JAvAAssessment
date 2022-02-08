package com.example.employeemanagementsystem.security.config;

import com.example.employeemanagementsystem.department.Department;
import com.example.employeemanagementsystem.department.DepartmentRepository;
import com.example.employeemanagementsystem.employee.Employee;
import com.example.employeemanagementsystem.employee.EmployeeService;
import com.example.employeemanagementsystem.employee.EmployeeStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
* @Configuration is used for adding some data to our db
* here we added some department names and also we added an admin information to the db
*/
@Configuration
public class DatabaseConfig {

    @Bean
    public CommandLineRunner demo(DepartmentRepository departmentRepository, EmployeeService employeeService) {
        return (args) -> {

            Department adminDepartment = new Department("IT");
            //saving 3 departments
            departmentRepository.save(adminDepartment);
            departmentRepository.save(new Department("Accounting"));
            departmentRepository.save(new Department("Sales"));

            //signing up admin with hard coding so the admin inforation will be always in our database
            employeeService.signUp(new Employee(
                    adminDepartment,
                    "Admin",
                    "Admin",
                    "admin@employeems.com",
                    "admin",
                    EmployeeStatus.ADMIN));
            //we enabled it by hand
            employeeService.enableEmployee("admin@employeems.com");

        };
    }

}