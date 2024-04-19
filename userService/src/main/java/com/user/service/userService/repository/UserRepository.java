package com.user.service.userService.repository;

import com.user.service.userService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
//    if we want to write any custom method or query then we write it here
}
