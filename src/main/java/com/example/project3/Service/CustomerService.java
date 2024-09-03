package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<User> getAllCustomers() {
        return authRepository.findUserByRole("CUSTOMER");
    }
    public void userRegister(CustomerDTO customerDTO) {

        customerDTO.setRole("CUSTOMER");

        User user = new User(null,customerDTO.getUserName(),customerDTO.getPassword(),
                customerDTO.getName(),customerDTO.getEmail(),customerDTO.getRole(),null,null);


        Customer customer = new Customer(customerDTO.getUserId(),customerDTO.getPhoneNumber(),user,null);


        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);

        customerRepository.save(customer);
        authRepository.save(user);

    }

    public void updateCustomer(User user,CustomerDTO customerDTO) {
        User u = authRepository.findUserById(user.getId());

        if (u.getId()!=customerDTO.getUserId()) {
            throw  new ApiException("User not match");
        }

        Customer c = customerRepository.findCustomerById(u.getId());

        u.setUserName(customerDTO.getUserName());
        u.setPassword(customerDTO.getPassword());
        u.setName(customerDTO.getName());
        u.setEmail(customerDTO.getEmail());
        u.getCustomer().setPhoneNumber(customerDTO.getPhoneNumber());
        u.setRole("CUSTOMER");


//        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        //u.setPassword(hash);

        u.setCustomer(c);
        c.setUser(u);
        authRepository.save(u);
        customerRepository.save(c);
    }

    public void deleteCustomer(User user,int customerId) {

        User u = authRepository.findUserById(customerId);
        if (u==null){
            throw  new ApiException("User not found");
        }else if (user.getId()==u.getId() || user.getRole().equals("ADMIN")) {
            authRepository.delete(u);
        }else {
            throw  new ApiException("User not match");
        }
    }

}
