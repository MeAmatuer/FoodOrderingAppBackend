package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class OrderService {


    @Autowired
    private CustomerAuthDao customerAuthDao;

    @Autowired
    private OrderDao orderDao;

    public CouponEntity getCoupon(String couponName, String accessToken) throws AuthorizationFailedException, CouponNotFoundException {

        final ZonedDateTime now;
        now = ZonedDateTime.now(ZoneId.systemDefault());


        CustomerAuthEntity loggedInCustomerAuth = customerAuthDao.findByAccessToken(accessToken);
        if (loggedInCustomerAuth == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }
        if (loggedInCustomerAuth.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }
        if (loggedInCustomerAuth.getExpiresAt().isAfter(now) ||  loggedInCustomerAuth.getExpiresAt().isEqual(now)) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }
        if(couponName.isEmpty()){
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        }
        CouponEntity couponEntity = orderDao.getCouponByName(couponName);
        if (couponEntity != null) {
            return couponEntity;
        }else {
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }
    }
}
