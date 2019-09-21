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

      @NamedQuery(name = "getAllRestaurantsByRating", query = "select q from RestaurantEntity q order by q.CustomerRating desc"),
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
    private String RestaurantName;


    @Column(name="photo_url")
    @Size(max=255)
    @NotNull
    private String PhotoUrl;

    @Column(name="customer_rating")
    @NotNull
    private BigDecimal CustomerRating;

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
    private List<OrdersEntity> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurant_category", joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> categories = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurant_item", joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<ItemEntity> items = new ArrayList<>();

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

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
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public Double getCustomerRating() {
        return CustomerRating.doubleValue();
    }

    public void setCustomerRating(Double customerRating) {
        this.CustomerRating = new BigDecimal(customerRating).setScale(2, RoundingMode.HALF_UP);
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

    public List<OrdersEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersEntity> orders) {
        this.orders = orders;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

}

