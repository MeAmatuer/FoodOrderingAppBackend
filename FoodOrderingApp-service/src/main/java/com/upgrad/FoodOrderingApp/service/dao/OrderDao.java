package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CouponEntity getCouponByName(String couponName){
        final CouponEntity couponEntity;
        try {
            couponEntity = entityManager.createNamedQuery("couponByCouponName", CouponEntity.class)
                    .setParameter("couponName", couponName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return couponEntity;
    }

    public List<OrdersEntity> getPastOrders(CustomerEntity customerEntity) {
        final List<OrdersEntity> pastOrders;
        try {
            pastOrders = entityManager.createNamedQuery("pastOrdersByDate", OrdersEntity.class)
                    .setParameter("customer", customerEntity)
                    .getResultList();
            return pastOrders;
        }catch (NoResultException nre){
            return null;
        }
    }

    public CouponEntity getCouponByUUID(UUID couponId) {
        final CouponEntity couponEntity;
        try {
            couponEntity = entityManager.createNamedQuery("couponByUUID", CouponEntity.class)
                    .setParameter("couponId", couponId)
                    .getSingleResult();
            return couponEntity;
        }catch (NoResultException nre){
            return null;
        }
    }

    public OrdersEntity createNewOrder(OrdersEntity order) {
        entityManager.persist(order);
        return order;
    }
}
