package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryBusinessService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class RestaurantController {

    @Autowired
    private RestaurantBusinessService restaurantBusinessService;

    @Autowired
    private CategoryBusinessService categoryBusinessService;


    @RequestMapping(method = RequestMethod.GET,path = "/restaurant",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants(){

        List<RestaurantEntity> restaurantEntities = restaurantBusinessService.getAllRestaurantsByRating();

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();

        for(RestaurantEntity restaurantEntity:restaurantEntities){

            RestaurantDetailsResponseAddressState addressState = new RestaurantDetailsResponseAddressState()
                    .id(UUID.fromString(restaurantEntity.getAddress().getState_id().getUuid())).
                            stateName(restaurantEntity.getAddress().getState_id().getState_name());

            RestaurantDetailsResponseAddress address = new RestaurantDetailsResponseAddress().
                    id(UUID.fromString(restaurantEntity.getAddress().getUuid())).
                    flatBuildingName(restaurantEntity.getAddress().getFlat_buil_number()).
                    locality(restaurantEntity.getAddress().getLocality()).city(restaurantEntity.getAddress().getCity()).
                    pincode(restaurantEntity.getAddress().getPincode()).state(addressState);

            String restaurantCategories = categoryBusinessService.getCategoriesByRestaurant(restaurantEntity.getUuid())
                    .stream().map(rc -> String.valueOf(rc.getCategory_name())).collect(Collectors.joining(","));

            RestaurantList restaurantList = new RestaurantList().id(UUID.fromString(restaurantEntity.getUuid())).
                    restaurantName(restaurantEntity.getReastaurant_name()).photoURL(restaurantEntity.getPhoto_url())
                    .customerRating(restaurantEntity.getCustomer_rating()).
                    averagePrice(restaurantEntity.getAverage_price_for_two()).numberCustomersRated(restaurantEntity.getNumber_of_customers_rated())
                    .address(address).categories(restaurantCategories);

                    restaurantListResponse.addRestaurantsItem(restaurantList);
        }

        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);
    }
}



