package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    public RestaurantEntity restaurantByUUID(String uuid) throws RestaurantNotFoundException {
        if(uuid.equals("")){
            throw new RestaurantNotFoundException("RNF-002","Restaurant id field should not be empty");
        }

        RestaurantEntity restaurantByRestaurantId = restaurantDao.restaurantByUUID(uuid);

        if(restaurantByRestaurantId==null){
            throw new RestaurantNotFoundException("RNF-001", "No Restaurant By this Id");
        }

        return restaurantByRestaurantId;
    }




}
