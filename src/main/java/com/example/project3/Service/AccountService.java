package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public void initiateAccount(User user, Account account) {
        account.setCustomer(user.getCustomer());
        accountRepository.save(account);
    }

    public void activateAccount(User user, int accountId) {
        Account a =accountRepository.findAccountById(accountId);

        if (a==null){
            throw  new ApiException("account not found");
        }
        a.setActive(true);
        accountRepository.save(a);
    }

    public Account viewAccountDetails(User user,int accountId) {
        Account a = accountRepository.findAccountById(accountId);
        if (a==null){
            throw  new ApiException("account not found");
        }
        Customer c = customerRepository.findCustomerById(a.getCustomer().getId());
        if (!Objects.equals(user.getCustomer().getId(), c.getId())){
            throw  new ApiException("wrong account");
        }
        return a;
    }

    public List<Account> listUserAccounts(User user) {
        return accountRepository.findAllByCustomer(user.getCustomer());
    }

    public void deposit(User user, int accountId, double amount) {
        Account a = accountRepository.findAccountById(accountId);
        if (a==null){
            throw  new ApiException("account not found");
        }
        Customer c = customerRepository.findCustomerById(a.getCustomer().getId());
        if (!Objects.equals(user.getCustomer().getId(), c.getId())){
            throw  new ApiException("wrong account");
        }
        if (!a.isActive()){
            throw  new ApiException("account not active");
        }

        a.setBalance(a.getBalance()+amount);
        accountRepository.save(a);
    }

    public void withdraw(User user, int accountId, double amount) {
        Account a = accountRepository.findAccountById(accountId);
        if (a==null){
            throw  new ApiException("account not found");
        }
        Customer c = customerRepository.findCustomerById(a.getCustomer().getId());
        if (!Objects.equals(user.getCustomer().getId(), c.getId())){
            throw  new ApiException("wrong account");
        }
        if (!a.isActive()){
            throw  new ApiException("account not active");
        }
        if (a.getBalance()<amount || a.getBalance()<0){
            throw  new ApiException("insufficient balance");
        }
        a.setBalance(a.getBalance()-amount);
        accountRepository.save(a);
    }

    public void transfer(User user, int fromAccountId, int toAccountId ,double amount) {
        Account from = accountRepository.findAccountById(fromAccountId);
        if (from ==null){
            throw  new ApiException("account not found");
        }
        Customer c = customerRepository.findCustomerById(from.getCustomer().getId());
        if (!Objects.equals(user.getCustomer().getId(), c.getId())){
            throw  new ApiException("wrong account");
        }
        if (!from.isActive()){
            throw  new ApiException("account not active");
        }
        if (from.getBalance()<amount || from.getBalance()==0){
            throw  new ApiException("insufficient balance");
        }
        from.setBalance(from.getBalance()-amount);
        accountRepository.save(from);


        Account to = accountRepository.findAccountById(toAccountId);
        if (to==null){
            throw  new ApiException("Respond account not found");
        } else if (!to.isActive()) {
            throw  new ApiException("account not active");
        }
        to.setBalance(to.getBalance()+amount);
        accountRepository.save(to);
    }

    public void blockBankAccount(User user, int accountId) {
        Account a = accountRepository.findAccountById(accountId);
        if (a==null){
            throw  new ApiException("account not found");
        }
        if (!a.isActive()){
            throw  new ApiException("account already blocked");
        }
        a.setActive(false);
        accountRepository.save(a);
    }
}
