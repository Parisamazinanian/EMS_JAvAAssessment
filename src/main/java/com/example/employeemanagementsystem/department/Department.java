package com.example.employeemanagementsystem.department;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
* Department is a table in our database
* it has two columns: id and name of the department
* Id is generated automatically
*/

//Lombok's annotations for generating getters and setters and no argument constructor
@Getter
@Setter
@NoArgsConstructor
//this annotation means that this class can be mapped to a table
@Entity
public class Department {
    //Codes for generating department ids automatically
    //we use these annotations before the id field
    @Id
    @SequenceGenerator(
            name = "department_sequence",
            sequenceName = "department_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "department_sequence"
    )
    private Long id;
    //this name is referring to the name of our departments
    private String name;

    //Creating a constructor without id with only name of the department
    public Department(String name) {
        this.name = name;
    }
}
