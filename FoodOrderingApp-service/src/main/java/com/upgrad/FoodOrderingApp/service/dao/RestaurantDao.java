package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;


    public RestaurantEntity getRestaurantById(UUID restaurantId) {
        try {
            RestaurantEntity restaurantEntity = entityManager.createNamedQuery("restaurantById", RestaurantEntity.class)
                    .setParameter("restaurantId", restaurantId)
                    .getSingleResult();
            return restaurantEntity;
        }catch (NoResultException nre){
            return null;
        }
    }
}
