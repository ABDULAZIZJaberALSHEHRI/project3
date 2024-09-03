package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "AccountRepository number should not be empty!")
    //@Pattern(regexp = "^(?:4[0-9]{3}[- ]?[0-9]{4}[- ]?[0-9]{4}[- ]?[0-9]{4})$")
    @Column(columnDefinition = "varchar(20) not null")
    private String accountNumber;

    @NotNull(message = "balance should not be empty!")
    @Positive(message = "balance should be positive number!")
    @Column(columnDefinition = "double not null")
    private double balance;

    private boolean isActive = false;

    @ManyToOne
    @JsonIgnore
    private Customer customer;
}
