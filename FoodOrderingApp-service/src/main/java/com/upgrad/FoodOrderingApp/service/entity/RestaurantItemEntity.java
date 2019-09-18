package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="restaurant_item")
public class RestaurantItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="item_id")
    private ItemEntity item_id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurant_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemEntity getItem_id() {
        return item_id;
    }

    public void setItem_id(ItemEntity item_id) {
        this.item_id = item_id;
    }

    public RestaurantEntity getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(RestaurantEntity restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
