package com.education.university.repo;

import com.education.university.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
}
