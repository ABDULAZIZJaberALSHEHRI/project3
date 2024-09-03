package com.example.project3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    private Integer userId;

    @NotEmpty(message = "UserName cannot be empty!")
    @Size(min = 4,max = 10,message = "UserName length should be between '4' and '10'!")
    private String userName;

    @NotEmpty(message = "Password should not be empty!")
    @Size(min = 6,message = "password length should be more than '6'!")
    private String password;

    @NotEmpty(message = "name should not be empty!")
    @Size(min = 2,max = 20,message = "name length should be more than '2' and less than '20'!")
    private String name;

    @Email(message = "Email not valid!")
    private String email;

    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$")
    private String role;

    @NotEmpty(message = "Position should not be empty!")
    @Size(max = 15,message = "Position length should be less than '15'!")
    private String position;

    @NotNull(message = "Salary should not be empty!")
    @Positive(message = "Salary should be positive number!")
    private Double salary;


}
