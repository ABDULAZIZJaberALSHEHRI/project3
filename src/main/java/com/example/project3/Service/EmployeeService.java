package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public List<User> getAllEmployees() {
        return authRepository.findUserByRole("EMPLOYEE");
    }

    public void registerEmployee(EmployeeDTO employeeDTO) {



        User user = new User(null,employeeDTO.getUserName(),employeeDTO.getPassword(),
                employeeDTO.getName(),employeeDTO.getEmail(),"EMPLOYEE",null,null);

        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);

        Employee employee = new Employee(user.getId(), employeeDTO.getPosition(),employeeDTO.getSalary(),user);




        employeeRepository.save(employee);
        authRepository.save(user);

    }

    public void updateEmployee(User user,int employeeId, EmployeeDTO employeeDTO) {
        User user1 = authRepository.findUserById(employeeId);
        if(user1==null) {
            throw new ApiException("Wrong ID");
        }
        if (user1.getId()!=user.getId()) {
            throw new ApiException("Employee mis match");
        }
        user1.setName(employeeDTO.getName());
        user1.setUserName(employeeDTO.getUserName());
        user1.setEmail(employeeDTO.getEmail());
        user1.setPassword(employeeDTO.getPassword());
        user1.getEmployee().setPosition(employeeDTO.getPosition());
        user1.getEmployee().setSalary(employeeDTO.getSalary());

        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user1.setPassword(hash);

        authRepository.save(user1);
    }

    public void deleteEmployee(User user,int employeeId) {
        User user1 = authRepository.findUserById(employeeId);
        if(user1==null) {
            throw new ApiException("Wrong ID");
        }
        if (Objects.equals(user1.getId(), user.getId()) ||user.getRole().equals("ADMIN")) {
            authRepository.delete(user1);
        } else  {
            throw new ApiException("Employee mis match");
        }


    }
}
