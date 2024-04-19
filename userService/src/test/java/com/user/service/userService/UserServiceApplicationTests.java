package com.user.service.userService;

import com.user.service.userService.entity.Rating;
import com.user.service.userService.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RatingService ratingService;

//	@Test
//	void createRating(){
//		Rating rating = Rating.builder().rating(5).userId("").hotelId("").feedback("It has been created using feign client.").build();
//		ratingService.createRating(rating);
//		System.out.println("New rating has been created!!");
//	}

}
