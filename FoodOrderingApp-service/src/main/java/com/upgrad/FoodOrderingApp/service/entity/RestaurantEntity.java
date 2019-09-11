package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "RESTAURANT")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "UUID")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "RESTAURANT_NAME")
    @NotNull
    @Size(max = 50)
    private String restaurantName;

    @Column(name = "PHOTO_URL")
    @Size(max = 255)
    private String photoUrl;

    @Column(name = "CUSTOMER_RATING")
    @NotNull
    private Integer customerRating;

    @Column(name = "AVERAGE_PRICE_FOR_TWO")
    @NotNull
    private Integer averagePriceForTwo;

    @Column(name = "NUMBER_OF_CUSTOMERS_RATED")
    @NotNull
    private Integer noOfCustomersRated;

    @Column(name = "ADDRESS_ID")
    @NotNull
    private Integer addressId;

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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Integer customerRating) {
        this.customerRating = customerRating;
    }

    public Integer getAveragePriceForTwo() {
        return averagePriceForTwo;
    }

    public void setAveragePriceForTwo(Integer averagePriceForTwo) {
        this.averagePriceForTwo = averagePriceForTwo;
    }

    public Integer getNoOfCustomersRated() {
        return noOfCustomersRated;
    }

    public void setNoOfCustomersRated(Integer noOfCustomersRated) {
        this.noOfCustomersRated = noOfCustomersRated;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantEntity that = (RestaurantEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(restaurantName, that.restaurantName) &&
                Objects.equals(photoUrl, that.photoUrl) &&
                Objects.equals(customerRating, that.customerRating) &&
                Objects.equals(averagePriceForTwo, that.averagePriceForTwo) &&
                Objects.equals(noOfCustomersRated, that.noOfCustomersRated) &&
                Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, restaurantName, photoUrl, customerRating, averagePriceForTwo, noOfCustomersRated, addressId);
    }

    @Override
    public String toString() {
        return "RestaurantEntity{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", customerRating=" + customerRating +
                ", averagePriceForTwo=" + averagePriceForTwo +
                ", noOfCustomersRated=" + noOfCustomersRated +
                ", addressId=" + addressId +
                '}';
    }
}
