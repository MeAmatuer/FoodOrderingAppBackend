package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryBusinessService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private RestaurantDao restaurantDao;

    public List<CategoryEntity> getCategoriesByRestaurant(String RestaurantUuid){
        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUuid(RestaurantUuid);
        return restaurantEntity.getCategories().stream().sorted(Comparator.comparing(CategoryEntity::getCategory_name)).collect(Collectors.toList());
    }
}
