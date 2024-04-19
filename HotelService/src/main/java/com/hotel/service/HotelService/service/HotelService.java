package com.hotel.service.HotelService.service;

import com.hotel.service.HotelService.entity.Hotel;

import java.util.List;

public interface HotelService {
//    create
    Hotel create(Hotel hotel);

//    get all
    List<Hotel> getAll();

//    get single
    Hotel get(String id);
}
