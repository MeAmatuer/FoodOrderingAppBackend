package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

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
}
