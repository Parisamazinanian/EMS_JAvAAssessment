package com.example.employeemanagementsystem.security.token;

import com.example.employeemanagementsystem.employee.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    // confirmation is optional
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(nullable = false, name = "employee_id")
    private Employee employee;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Employee employee) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.employee = employee;
    }
}
