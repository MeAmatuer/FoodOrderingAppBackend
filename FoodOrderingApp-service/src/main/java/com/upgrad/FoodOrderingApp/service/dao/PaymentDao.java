package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class PaymentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public PaymentEntity getPayment(UUID paymentId) {
        try {
            PaymentEntity paymentEntity = entityManager.createNamedQuery("paymentsByPaymentId", PaymentEntity.class)
                    .setParameter("paymentId", paymentId)
                    .getSingleResult();
            return paymentEntity;
        }catch (NoResultException nre){
            return null;
        }
    }
}
