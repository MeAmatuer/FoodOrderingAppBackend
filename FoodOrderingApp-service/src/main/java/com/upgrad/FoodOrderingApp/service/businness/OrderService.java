package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {


    @Autowired
    private CustomerAuthDao customerAuthDao;

    @Autowired
    private OrderDao orderDao;

    public CouponEntity getCoupon(String couponName, String accessToken) throws AuthorizationFailedException, CouponNotFoundException {

        checkAccessToken(accessToken);
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


    public List<OrdersEntity> getPastOrders(String accessToken) throws AuthorizationFailedException {
        checkAccessToken(accessToken);
        CustomerEntity customerEntity = customerAuthDao.findByAccessToken(accessToken).getCustomer();
        List<OrdersEntity> pastOrders = orderDao.getPastOrders(customerEntity);
        return pastOrders;
    }

    private void checkAccessToken(String accessToken) throws AuthorizationFailedException{
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
    }

    public CouponEntity getCouponByUUID(UUID couponId) throws CouponNotFoundException {

        CouponEntity coupon = orderDao.getCouponByUUID(couponId);
        if(coupon == null){
            throw new CouponNotFoundException("CPF-002", "No coupon by this id");
        }

        return coupon;
    }

    public OrdersEntity createOrder(OrdersEntity order) {
        OrdersEntity newOrder = orderDao.createNewOrder(order);
        return newOrder;
    }
}
