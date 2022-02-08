package com.example.employeemanagementsystem.department;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    public Department find(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Department not found"));
    }

    public Set<String> getDepartmentNames() {
        List<Department> allDepartments = departmentRepository.findAll();
        Set<String> departmentNames = new HashSet<>();
        for (Department department : allDepartments) {
            departmentNames.add(department.getName());
        }
        return departmentNames;
    }
}
