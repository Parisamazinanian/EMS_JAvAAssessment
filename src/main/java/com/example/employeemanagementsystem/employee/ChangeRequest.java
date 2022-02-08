package com.example.employeemanagementsystem.employee;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ChangeRequest {
    private String firstName;
    private String lastName;
    private String email;

}
