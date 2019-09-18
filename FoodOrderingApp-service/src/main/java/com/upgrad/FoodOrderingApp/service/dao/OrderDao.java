package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    //This method fetches and returns all the orders of a restaurant

    public List<OrdersEntity> getOrdersByRestaurant(RestaurantEntity restaurant) {
        try {
            return entityManager.createNamedQuery("ordersByRestaurant", OrdersEntity.class).setParameter("restaurant", restaurant).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}