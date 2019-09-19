package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AddressDao {

    @PersistenceContext
    private EntityManager entityManager;


    public AddressEntity getAddressById(String addressId) {
        try {
            AddressEntity addressEntity = entityManager.createNamedQuery("addressById", AddressEntity.class)
                    .setParameter("addressId", addressId)
                    .getSingleResult();
            return addressEntity;
        }catch (NoResultException nre){
            return null;
        }
    }
}
