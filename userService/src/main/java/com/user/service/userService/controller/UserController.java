package com.user.service.userService.controller;


import com.user.service.userService.entity.User;
import com.user.service.userService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

//    create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

//    get single user
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        logger.info("Get Single User Handler: UserController");
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

//    creating fallback method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
//        logger.info("Fallback is executed because service is down: ", ex.getMessage());
        ex.printStackTrace();

        User user = User.builder()
                .email("dummy123@gmail.com")
                .name("Dummy")
                .about("This dummy user is created because some of the services are down!!")
                .userId("123456")
                .build();

        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }

//    get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
