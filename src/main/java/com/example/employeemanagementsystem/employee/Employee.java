package com.example.employeemanagementsystem.employee;

import com.example.employeemanagementsystem.department.Department;
import com.example.employeemanagementsystem.security.token.ConfirmationToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/*
* Employee class which is implementing an already existed UserDetails interface
* We defined some fields in this class
* Lombok's annotations were used
* this class in an Entity that means later we can use this class as a table in our db */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Employee implements UserDetails {
    //annotations for generating the employee Ids automatically
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private Long id;
    //this class is joined to the Department table from "departemtn_id" column
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "department_id")
    private Department department;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    //employee Status is and Enum class so we need to use this annotation
    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;
    private Boolean locked = false;
    private Boolean enabled = false;
    //when we want to delete an employee because this table is connected to token, so we are giving the permission
    //to delete the token connected to it
    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "employee")
    private List<ConfirmationToken> tokens;//because we have more than one token for each user so delete them
    //Creating a constructor without the id
    public Employee(Department department,
                   String firstName,
                   String lastName,
                   String email,
                   String password,
                   EmployeeStatus employeeStatus) {
        this.department = department;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.employeeStatus = employeeStatus;
    }
    //Overriding the methods of UserDetails interface
    //this method is for securing the authority (in this case people in our EmployeeStatus Enum class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //building an instance of class SimpleGrantedAuthority and this object is dependent on the assigned user
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(employeeStatus.name());
        // a list with one member (authority)
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        //we make it valid (if it is non expired)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
