package com.example.project3.Controller;

import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Service.AccountService;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")

public class EmployeeController {

    private final EmployeeService employeeService;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @GetMapping("/get-all-employees")
    public ResponseEntity getAllEmployees() {
        return ResponseEntity.status(200).body(employeeService.getAllEmployees());
    }

    @PostMapping("/register-employee")
    public ResponseEntity registerEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.status(200).body("Employee registered successfully");
    }

    @PutMapping("/update-employee/{employeeid}")
    public ResponseEntity updateEmployee(@PathVariable int employeeid, @AuthenticationPrincipal User user, @RequestBody EmployeeDTO employeeDTO) {

        employeeService.updateEmployee(user, employeeid, employeeDTO);
        return ResponseEntity.status(200).body("Employee updated successfully");
    }

    @DeleteMapping("/delete-employee/{employeeid}")
    public ResponseEntity deleteEmployee(@PathVariable int employeeid, @AuthenticationPrincipal User user) {
        employeeService.deleteEmployee(user, employeeid);
        return ResponseEntity.status(200).body("Employee deleted successfully");
    }

    @PutMapping("/active-account/{accountid}")
    public ResponseEntity activeAccount(@PathVariable int accountid, @AuthenticationPrincipal User user) {
        accountService.activateAccount(user, accountid);
        return ResponseEntity.status(200).body("Account activated successfully");
    }

    @PutMapping("/block-account/{accountid}")
    public ResponseEntity blockAccount(@PathVariable int accountid, @AuthenticationPrincipal User user) {
        accountService.blockBankAccount(user, accountid);
        return ResponseEntity.status(200).body("Account blocked successfully");
    }
}
