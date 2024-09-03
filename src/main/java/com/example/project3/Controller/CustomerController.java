package com.example.project3.Controller;

import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final AccountService accountService;

    @GetMapping("/get-all-customers")
    public ResponseEntity getAllCustomers() {
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }


    @PostMapping("/customer-register")
    public ResponseEntity userRegister(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.userRegister(customerDTO);
        return ResponseEntity.status(200).body("User registered successfully");
    }

    @PutMapping("/update-customer")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user, @Valid @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(user, customerDTO);
        return ResponseEntity.status(200).body("Customer updated successfully");
    }

    @DeleteMapping("/delete-customer/{customerid}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user, @PathVariable int customerid) {
        customerService.deleteCustomer(user, customerid);
        return ResponseEntity.status(200).body("Customer deleted successfully");
    }

    @PutMapping("/creat-account")
    public ResponseEntity createAccount(@AuthenticationPrincipal User user, @Valid @RequestBody Account account) {
        accountService.initiateAccount(user, account);
        return ResponseEntity.status(200).body("Account created successfully");
    }

    @GetMapping("/view-account-details/{accountid}")
    public ResponseEntity getAccountDetails(@AuthenticationPrincipal User user, @PathVariable int accountid) {

        return ResponseEntity.status(200).body(accountService.viewAccountDetails(user, accountid));
    }

    @GetMapping("/view-user-accounts")
    public ResponseEntity getUserAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.listUserAccounts(user));
    }

    @PutMapping("/deposit/{accountid}/{amount}")
    public ResponseEntity deposit(@AuthenticationPrincipal User user, @PathVariable int accountid, @PathVariable double amount) {
        accountService.deposit(user,accountid,amount);
        return ResponseEntity.status(200).body("Deposit successfully");
    }

    @PutMapping("/withdraw/{accountid}/{amount}")
    public ResponseEntity withdraw(@AuthenticationPrincipal User user, @PathVariable int accountid, @PathVariable double amount) {
        accountService.withdraw(user,accountid,amount);
        return ResponseEntity.status(200).body("Withdraw successfully");
    }

    @PutMapping("/transfer-funds/{fromaccountid}/{toaccountid}/{amount}")
    public ResponseEntity transferFunds (@AuthenticationPrincipal User user, @PathVariable int fromaccountid, @PathVariable int toaccountid, @PathVariable double amount) {
        accountService.transfer(user,fromaccountid,toaccountid,amount);
        return ResponseEntity.status(200).body("Transfer successfully");
    }
}
