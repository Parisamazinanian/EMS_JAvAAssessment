package com.example.employeemanagementsystem.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/*
* This repository is extending JpaRepository using Department and id
* Repository classes are connected to the db
* In this class we have Optional<T> query
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    //Our query method will return an Optional that contains the found object or an empty Optional.
    Optional<Department> findByName(String name);
}
