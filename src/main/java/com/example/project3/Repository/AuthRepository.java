package com.example.project3.Repository;

import com.example.project3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {

    User findUserById(int id);

    User findUserByUserName(String username);

    List<User> findUserByRole(String role);
}
