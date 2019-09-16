package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.api.provider.BearerAuthDecoder;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ItemService itemService;

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
        List<OrderEntity> orders = orderService.getPastOrders(accessToken);

        List<OrderList> orderList = new LinkedList<OrderList>();
        for (OrderEntity order : orders) {
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

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveOrderResponse> saveCustomerOrder(final SaveOrderRequest orderRequest, String authorization) throws AuthorizationFailedException, PaymentMethodNotFoundException, RestaurantNotFoundException, ItemNotFoundException, CouponNotFoundException, AddressNotFoundException {
        BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);
        String accessToken = bearerAuthDecoder.getAccessToken();
        final OrderEntity order = getOrderObject(orderRequest);
        final OrderEntity createdOrder = orderService.saveOrder(order);
        SaveOrderResponse orderResponse = new SaveOrderResponse()
                .id(createdOrder.getUuid().toString())
                .status("ORDER SUCCESSFULLY PLACED");
        return new ResponseEntity<SaveOrderResponse>(orderResponse, HttpStatus.CREATED);
    }

    private OrderEntity getOrderObject(SaveOrderRequest orderRequest) throws CouponNotFoundException, PaymentMethodNotFoundException, AddressNotFoundException, RestaurantNotFoundException, ItemNotFoundException {
        OrderEntity ordersEntity = new OrderEntity();
        ordersEntity.setBill(orderRequest.getBill());
        ordersEntity.setCoupon(orderService.getCouponByCouponId(orderRequest.getCouponId()));
        ordersEntity.setDiscount(orderRequest.getDiscount());
        ordersEntity.setDate(ZonedDateTime.now());
        ordersEntity.setPayment(paymentService.getPaymentByUUID(orderRequest.getPaymentId()));
        ordersEntity.setAddress(addressService.getAddressByUUID(orderRequest.getAddressId()));
        ordersEntity.setRestaurant(restaurantService.restaurantByUUID(orderRequest.getRestaurantId()));
        List<ItemQuantity> itemQuantities = orderRequest.getItemQuantities();
        List<OrderItemEntity> orderItemEntities = new LinkedList<>();
        for (ItemQuantity itemQuantity : itemQuantities) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setItemId(itemService.getItemById(itemQuantity.getItemId()));
            orderItemEntity.setPrice(itemQuantity.getPrice());
            orderItemEntity.setQuantity(itemQuantity.getQuantity());
            orderItemEntities.add(orderItemEntity);
        }
        ordersEntity.setOrderItem(orderItemEntities);
        return ordersEntity;
    }
}
