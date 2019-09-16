package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
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

    public List<OrderEntity> getPastOrders(CustomerEntity customerEntity) {
        final List<OrderEntity> pastOrders;
        try {
            pastOrders = entityManager.createNamedQuery("pastOrdersByDate", OrderEntity.class)
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

    public OrderEntity createNewOrder(OrderEntity order) {
        entityManager.persist(order);
        return order;
    }

    public OrderItemEntity createNewOrderItem(OrderItemEntity orderItemEntity) {
        entityManager.persist(orderItemEntity);
        return orderItemEntity;
    }
}
