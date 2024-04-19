package com.user.service.userService.service.impl;


import com.user.service.userService.entity.Hotel;
import com.user.service.userService.entity.Rating;
import com.user.service.userService.entity.User;
import com.user.service.userService.exceptions.ResourceNotFoundException;
import com.user.service.userService.external.services.HotelService;
import com.user.service.userService.repository.UserRepository;
import com.user.service.userService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

//    constructor injection
//    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate, HotelService hotelService) {
//        this.userRepository = userRepository;
//        this.restTemplate = restTemplate;
//        this.hotelService = hotelService;
//    }

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

//    get single user
    @Override
    public User getUser(String userId) {
//        get user from database with the help of repository.
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given Id is not found on server!! : " + userId));

//        fetching rating of the above user from RATING SERVICE.
//        url: http://localhost:8083/ratings/users/66aded92-13d9-4801-a02d-10d2fe83140d
        Rating[] userRatings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ", userRatings);

        List<Rating> ratings = Arrays.stream(userRatings).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
//            api call to hotel service to get the hotel
//            url: http://localhost:8082/hotels/f4b0d96b-4da9-4487-baa2-157b81cbe0fa

//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
//            using feignClient instead of RestTemplate
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            logger.info("Response status code: {} ", forEntity.getStatusCode());


            rating.setHotel(hotel); //set the hotel to rating

            return rating;  //return the rating
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}
