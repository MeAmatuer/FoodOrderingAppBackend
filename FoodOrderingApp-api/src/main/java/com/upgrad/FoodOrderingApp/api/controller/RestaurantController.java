package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryBusinessService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //This method returns  all the restaurants in order of their ratings
    //It display the response in a JSON format with the corresponding HTTP status
    //And within each restaurant it displays restaurant categories in alphabetical order

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants() {

        List<RestaurantEntity> restaurantEntities = restaurantBusinessService.getAllRestaurantsByRating();

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();

        for (RestaurantEntity restaurantEntity : restaurantEntities) {

            //Extracting state field of a restaurant

            RestaurantDetailsResponseAddressState addressState = new RestaurantDetailsResponseAddressState()
                    .id(UUID.fromString(restaurantEntity.getAddress().getState_id().getUuid())).
                            stateName(restaurantEntity.getAddress().getState_id().getState_name());

            //Extracting address field of a restaurant

            RestaurantDetailsResponseAddress address = new RestaurantDetailsResponseAddress().
                    id(UUID.fromString(restaurantEntity.getAddress().getUuid())).
                    flatBuildingName(restaurantEntity.getAddress().getFlat_buil_number()).
                    locality(restaurantEntity.getAddress().getLocality()).city(restaurantEntity.getAddress().getCity()).
                    pincode(restaurantEntity.getAddress().getPincode()).state(addressState);

            //Extracting category field of a restaurant

            String restaurantCategories = categoryBusinessService.getCategoriesByRestaurant(restaurantEntity.getUuid())
                    .stream().map(rc -> String.valueOf(rc.getCategory_name())).collect(Collectors.joining(","));

            //Populating restaurant List with all necessary fields

            RestaurantList restaurantList = new RestaurantList().id(UUID.fromString(restaurantEntity.getUuid())).
                    restaurantName(restaurantEntity.getReastaurant_name()).photoURL(restaurantEntity.getPhoto_url())
                    .customerRating(restaurantEntity.getCustomer_rating()).
                            averagePrice(restaurantEntity.getAverage_price_for_two()).numberCustomersRated(restaurantEntity.getNumber_of_customers_rated())
                    .address(address).categories(restaurantCategories);

            //Populating response field with all the restaurant items
            restaurantListResponse.addRestaurantsItem(restaurantList);
        }

        //Returning the response with the desired http status code
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);
    }

    //This method returns restaurant/s by name. Its a GET Request. It takes restaurant name as a path variable
    //If the restaurant name field entered by the customer is empty, throw “RestaurantNotFoundException”
    //If there are no restaurants by the name entered by the customer, return an empty list with corresponding HTTP status
    //It returns the restaurant list as per the restaurant name search field
    //Searched restaurants are also displayed in the alphabetical order

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/name/{restaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantsByName(@PathVariable("restaurant_name") final String restaurantName)
            throws RestaurantNotFoundException {

        List<RestaurantEntity> matchedRestaurantsByNameList = restaurantBusinessService.getRestaurantsByName(restaurantName);

        RestaurantListResponse listResponse = new RestaurantListResponse();

        if (matchedRestaurantsByNameList.isEmpty()) {
            return new ResponseEntity<RestaurantListResponse>(listResponse, HttpStatus.NOT_FOUND);
        }

        for (RestaurantEntity restaurantEntity : matchedRestaurantsByNameList) {

            //Extracting state field of a restaurant

            RestaurantDetailsResponseAddressState responseAddressState = new RestaurantDetailsResponseAddressState()
                    .id(UUID.fromString(restaurantEntity.getAddress().getState_id().getUuid())).
                            stateName(restaurantEntity.getAddress().getState_id().getState_name());

            //Extracting address field of a restaurant

            RestaurantDetailsResponseAddress responseAddress = new RestaurantDetailsResponseAddress().
                    id(UUID.fromString(restaurantEntity.getAddress().getUuid())).
                    flatBuildingName(restaurantEntity.getAddress().getFlat_buil_number()).
                    locality(restaurantEntity.getAddress().getLocality()).city(restaurantEntity.getAddress().getCity()).
                    pincode(restaurantEntity.getAddress().getPincode()).state(responseAddressState);

            //Extracting categories field of a restaurant

            String categories = categoryBusinessService.getCategoriesByRestaurant(restaurantEntity.getUuid())
                    .stream().map(rc -> String.valueOf(rc.getCategory_name())).collect(Collectors.joining(","));

            //Populating restaurant List with all necessary fields

            RestaurantList restaurantList = new RestaurantList().id(UUID.fromString(restaurantEntity.getUuid())).
                    restaurantName(restaurantEntity.getReastaurant_name()).photoURL(restaurantEntity.getPhoto_url())
                    .customerRating(restaurantEntity.getCustomer_rating()).
                            averagePrice(restaurantEntity.getAverage_price_for_two()).numberCustomersRated(restaurantEntity.getNumber_of_customers_rated())
                    .address(responseAddress).categories(categories);

            //Populating response field with all the restaurant items

            listResponse.addRestaurantsItem(restaurantList);
        }
        //Returning the response with the desired http status code
        return new ResponseEntity<RestaurantListResponse>(listResponse, HttpStatus.OK);
    }

}


