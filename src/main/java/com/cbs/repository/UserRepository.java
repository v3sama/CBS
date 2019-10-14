package com.cbs.repository;

import com.cbs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findByEmail(String email);

    User findByPhone(String phone);
}

