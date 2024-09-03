package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    private Integer id;


    @Column(columnDefinition = "varchar(15) not null")
    private String position;


    @Column(columnDefinition = "double not null")
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;
}
