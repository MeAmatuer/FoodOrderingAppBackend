package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.api.provider.BearerAuthDecoder;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "order/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCouponByName(@PathVariable(value = "coupon_name") final String couponName, @RequestHeader(value = "authorization") final String authorization) throws AuthorizationFailedException, CouponNotFoundException {
        BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);
        String accessToken = bearerAuthDecoder.getAccessToken();
        final CouponEntity couponEntity = orderService.getCoupon(couponName, accessToken);
        CouponDetailsResponse couponDetails = new CouponDetailsResponse().id(couponEntity.getUuid()).couponName(couponEntity.getCouponName()).percent(couponEntity.getPercent());
        return new ResponseEntity<CouponDetailsResponse>(couponDetails, HttpStatus.OK);
    }

}
