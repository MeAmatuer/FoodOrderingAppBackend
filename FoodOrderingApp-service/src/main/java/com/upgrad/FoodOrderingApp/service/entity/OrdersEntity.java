package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "ORDERS")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "BILL")
    @NotNull
    private Integer bill;

    @Column(name = "COUPON_ID")
    private Integer couponId;

    @Column(name = "DISCOUNT")
    private Integer discount;

    @Column(name = "DATE")
    @NotNull
    private ZonedDateTime date;

    @Column(name = "PAYMENT_ID")
    private Integer paymentId;

    @Column(name = "CUSTOMER_ID")
    @NotNull
    private Integer customerId;

    @Column(name = "ADDRESS_ID")
    @NotNull
    private Integer addressId;

    @Column(name = "RESTAURANT_ID")
    @NotNull
    private Integer restaurantId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(bill, that.bill) &&
                Objects.equals(couponId, that.couponId) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(date, that.date) &&
                Objects.equals(paymentId, that.paymentId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(addressId, that.addressId) &&
                Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, bill, couponId, discount, date, paymentId, customerId, addressId, restaurantId);
    }

    @Override
    public String toString() {
        return "OrdersEntity{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", bill=" + bill +
                ", couponId=" + couponId +
                ", discount=" + discount +
                ", date=" + date +
                ", paymentId=" + paymentId +
                ", customerId=" + customerId +
                ", addressId=" + addressId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
