package com.user.service.userService.service;

import com.user.service.userService.entity.User;

import java.util.List;

public interface UserService {
//    user operations

//    create
    User saveUser(User user);

//    get all users
    List<User> getAllUser();

//    get single user of given userID
    User getUser(String userId);
}
