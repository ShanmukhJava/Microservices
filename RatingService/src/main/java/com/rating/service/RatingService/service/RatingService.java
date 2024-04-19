package com.rating.service.RatingService.service;

import com.rating.service.RatingService.entity.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
//    create
    Rating create(Rating rating);

//    get all ratings
    List<Rating> getAllRatings();

//    get all by userId
    List<Rating> getRatingByUserId(String userId);

//    get all by hotelId
    List<Rating> getRatingByHotelId(String hotelId);
}
