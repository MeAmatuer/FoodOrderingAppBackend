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

    /**
     * Method called to get the Restaurant entity given the restaurant id
     * @param restaurantId
     * @return
     * @throws RestaurantNotFoundException
     */
    public RestaurantEntity restaurantByUUID(String restaurantId) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantById(restaurantId);
        if(restaurantEntity == null){
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }else {
            return restaurantEntity;
        }
    }
}
