package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    /**
     * Method that implements the business logic to verify that the access token passed to it is valid or not
     * @param accessToken
     * @return
     * @throws AuthorizationFailedException
     */
    public CustomerEntity getCustomer(String accessToken) throws AuthorizationFailedException {

        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthByAccessToken(accessToken);

        if (customerAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }

        if (customerAuthEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }

        ZonedDateTime now = ZonedDateTime.now();
        if (customerAuthEntity.getExpiresAt().isBefore(now)) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }

        return customerAuthEntity.getCustomer();
    }
}
