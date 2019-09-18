package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="restaurant")
@NamedQueries({

        @NamedQuery(name = "getAllRestaurantsByRating", query = "select q from RestaurantEntity q order by q.customer_rating desc"),
        @NamedQuery(name = "restaurantByUUID", query = "select q from RestaurantEntity q where q.uuid = :uuid"),
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
    private String reastaurant_name;

    @Column(name="photo_url")
    @Size(max=255)
    @NotNull
    private String photo_url;

    @Column(name="customer_rating")
    @NotNull
    private BigDecimal customer_rating;

    @Column(name="average_price_for_two")
    @NotNull
    private Integer average_price_for_two;

    @Column(name="number_of_customers_rated")
    @NotNull
    private Integer number_of_customers_rated;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="address_id")
    private AddressEntity address;

    @OneToMany(mappedBy = "restaurant", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<OrdersEntity> orders = new ArrayList<>();

   /* @OneToMany(mappedBy = "restaurant_id", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<RestaurantCategoryEntity> restaurantCategory = new ArrayList<>();*/

    @ManyToMany
    @JoinTable(name = "restaurant_category", joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> categories = new ArrayList<>();

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    @OneToMany(mappedBy = "restaurant_id", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
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

    public String getReastaurant_name() {
        return reastaurant_name;
    }

    public void setReastaurant_name(String reastaurant_name) {
        this.reastaurant_name = reastaurant_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public BigDecimal getCustomer_rating() {
        return customer_rating;
    }

    public void setCustomer_rating(BigDecimal customer_rating) {
        this.customer_rating = customer_rating;
    }

    public Integer getAverage_price_for_two() {
        return average_price_for_two;
    }

    public void setAverage_price_for_two(Integer average_price_for_two) {
        this.average_price_for_two = average_price_for_two;
    }

    public Integer getNumber_of_customers_rated() {
        return number_of_customers_rated;
    }

    public void setNumber_of_customers_rated(Integer number_of_customers_rated) {
        this.number_of_customers_rated = number_of_customers_rated;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<OrdersEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersEntity> orders) {
        this.orders = orders;
    }




   /* public List<RestaurantCategoryEntity> getRestaurantCategory() {
        return restaurantCategory;
    }

    public void setRestaurantCategory(List<RestaurantCategoryEntity> restaurantCategory) {
        this.restaurantCategory = restaurantCategory;
    }*/

    public List<RestaurantItemEntity> getRestaurantItem() {
        return restaurantItem;
    }

    public void setRestaurantItem(List<RestaurantItemEntity> restaurantItem) {
        this.restaurantItem = restaurantItem;
    }
}

