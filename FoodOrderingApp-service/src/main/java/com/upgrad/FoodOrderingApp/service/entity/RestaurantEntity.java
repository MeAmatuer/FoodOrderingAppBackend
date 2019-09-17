package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="restaurant")
@NamedQueries({
        @NamedQuery(name = "restaurantById", query = "select r from RestaurantEntity r where r.uuid = :restaurantId")
})
public class RestaurantEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @Column(name="uuid")
    @Size(max=200)
    @NotNull
    private String uuid;

    @Column(name="restaurant_name")
    @Size(max=30)
    @NotNull
    private String restaurantName;

    @Column(name="photo_url")
    @Size(max=255)
    @NotNull
    private String photoUrl;

    @Column(name="customer_rating")
    @NotNull
    private BigDecimal customerRating;

    @Column(name="average_price_for_two")
    @NotNull
    private Integer AvgPrice;

    @Column(name="number_of_customers_rated")
    @NotNull
    private Integer NumberCustomersRated;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="address_id")
    private AddressEntity address;

    @OneToMany(mappedBy = "restaurant", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();

    @OneToMany(mappedBy = "restaurantId", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<RestaurantCategoryEntity> restaurantCategory = new ArrayList<>();

    @OneToMany(mappedBy = "restaurantId", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<RestaurantItemEntity> restaurantItem = new ArrayList<>();

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

    public Double getCustomerRating() {
        return customerRating.doubleValue();
    }

    public void setCustomerRating(Double customerRating) {
        this.customerRating = new BigDecimal(customerRating).setScale(2, RoundingMode.HALF_UP);;
    }

    public Integer getAvgPrice() {
        return AvgPrice;
    }

    public void setAvgPrice(Integer avgPrice) {
        AvgPrice = avgPrice;
    }

    public Integer getNumberCustomersRated() {
        return NumberCustomersRated;
    }

    public void setNumberCustomersRated(Integer numberCustomersRated) {
        NumberCustomersRated = numberCustomersRated;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public List<RestaurantCategoryEntity> getRestaurantCategory() {
        return restaurantCategory;
    }

    public void setRestaurantCategory(List<RestaurantCategoryEntity> restaurantCategory) {
        this.restaurantCategory = restaurantCategory;
    }

    public List<RestaurantItemEntity> getRestaurantItem() {
        return restaurantItem;
    }

    public void setRestaurantItem(List<RestaurantItemEntity> restaurantItem) {
        this.restaurantItem = restaurantItem;
    }

    public RestaurantEntity() {
    }
}

