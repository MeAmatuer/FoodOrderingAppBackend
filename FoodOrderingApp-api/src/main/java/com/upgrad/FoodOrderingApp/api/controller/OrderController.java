package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.api.provider.BearerAuthDecoder;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

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

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<OrderList>> getPastOrders(final String authorization) throws AuthorizationFailedException {
        BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);
        String accessToken = bearerAuthDecoder.getAccessToken();
        List<OrdersEntity> orders = orderService.getPastOrders(accessToken);

        List<OrderList> orderList = new LinkedList<OrderList>();
        for (OrdersEntity order : orders) {
            List<OrderItemEntity> itemList = order.getOrderItem();
            List<ItemQuantityResponse> itemQuantityResponses = new LinkedList<ItemQuantityResponse>();
            for (OrderItemEntity itemEntity : itemList) {
                ItemQuantityResponseItem item = new ItemQuantityResponseItem().id(itemEntity.getItemId().getUuid()).
                        itemName(itemEntity.getItemId().getItemName()).itemPrice(itemEntity.getPrice()).type(ItemQuantityResponseItem.TypeEnum.fromValue(itemEntity.getItemId().getType().getValue()));
                ItemQuantityResponse itemQuantityResponse = new ItemQuantityResponse().quantity(itemEntity.getQuantity()).price(itemEntity.getPrice());
                itemQuantityResponses.add(itemQuantityResponse);
            }
            CouponEntity coupon = order.getCoupon();
            OrderListCoupon couponDetails = new OrderListCoupon().id(coupon.getUuid()).couponName(coupon.getCouponName()).percent(coupon.getPercent());
            OrderListPayment orderListPayment = new OrderListPayment()
                    .id(order.getPayment().getUuid())
                    .paymentName(order.getPayment().getPaymentName());
            OrderListCustomer orderListCustomer = new OrderListCustomer()
                    .id(order.getCustomer().getUuid())
                    .firstName(order.getCustomer().getFirstName())
                    .lastName(order.getCustomer().getLastName())
                    .emailAddress(order.getCustomer().getEmail())
                    .contactNumber(order.getCustomer().getContactNumber());
            OrderListAddressState state = new OrderListAddressState()
                    .id(order.getAddress().getStateId().getUuid())
                    .stateName(order.getAddress().getStateId().getStateName());
            OrderListAddress orderListAddress = new OrderListAddress()
                    .id(order.getAddress().getUuid())
                    .flatBuildingName(order.getAddress().getFlatBuilNumber())
                    .locality(order.getAddress().getLocality())
                    .city(order.getAddress().getCity())
                    .pincode(order.getAddress().getPincode())
                    .state(state);
            OrderList orderDetails = new OrderList()
                    .id(order.getUuid())
                    .bill(order.getBill())
                    .coupon(couponDetails)
                    .discount(order.getDiscount())
                    .date(order.getDate().toString())
                    .payment(orderListPayment)
                    .customer(orderListCustomer)
                    .address(orderListAddress)
                    .itemQuantities(itemQuantityResponses);
            orderList.add(orderDetails);
        }
        return new ResponseEntity<List<OrderList>>(orderList, HttpStatus.OK);
    }

}
