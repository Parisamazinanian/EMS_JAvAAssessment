package com.example.employeemanagementsystem.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e " +
            "SET e.enabled = TRUE " +
            "WHERE e.email = ?1"
    )
    int enableEmployee(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.firstName = ?2, e.lastName = ?3 WHERE e.email = ?1")
    void update(String email, String firstName, String lastName);
}
