package com.example.employeemanagementsystem.department;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
* This class is our department service, so we annotate the class with @Service
* @Service annotation is used with classes that provide some business functionalities.
* This class is connected to the DepartmentRepository and it has two methods.
* */
@Service
//Using Lombok's for creating constructors of all the class fields
@AllArgsConstructor
public class DepartmentService {
    private DepartmentRepository departmentRepository;
    //finding the department name and check if that department name exists or not
    public Department find(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Department not found"));
    }
    //Creating a set of department names
    public Set<String> getDepartmentNames() {
        List<Department> allDepartments = departmentRepository.findAll();
        Set<String> departmentNames = new HashSet<>();
        for (Department department : allDepartments) {
            departmentNames.add(department.getName());
        }
        return departmentNames;
    }
}
