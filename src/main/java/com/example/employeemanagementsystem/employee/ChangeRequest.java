package com.example.employeemanagementsystem.employee;

import lombok.*;
// This class is used for updating the user's information
//the fields are our focus points for update
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
