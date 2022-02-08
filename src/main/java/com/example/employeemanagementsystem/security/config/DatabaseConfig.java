package com.example.employeemanagementsystem.security.config;

import com.example.employeemanagementsystem.department.Department;
import com.example.employeemanagementsystem.department.DepartmentRepository;
import com.example.employeemanagementsystem.employee.Employee;
import com.example.employeemanagementsystem.employee.EmployeeService;
import com.example.employeemanagementsystem.employee.EmployeeStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    public CommandLineRunner demo(DepartmentRepository departmentRepository, EmployeeService employeeService) {
        return (args) -> {

            Department adminDepartment = new Department("IT");

            departmentRepository.save(adminDepartment);
            departmentRepository.save(new Department("Accounting"));
            departmentRepository.save(new Department("Sales"));


            employeeService.signUp(new Employee(
                    adminDepartment,
                    "Admin",
                    "Admin",
                    "admin@employeems.com",
                    "admin",
                    EmployeeStatus.ADMIN));

            employeeService.enableEmployee("admin@employeems.com");

        };
    }

}