package com.tushar.User_service.repository;

import com.tushar.User_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
}
