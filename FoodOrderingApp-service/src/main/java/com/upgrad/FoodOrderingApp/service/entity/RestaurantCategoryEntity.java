package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="restaurant_category")
public class RestaurantCategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurant_id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="category_id")
    private CategoryEntity categoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(RestaurantEntity restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public CategoryEntity getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryEntity categoryId) {
        this.categoryId = categoryId;
    }

    public RestaurantCategoryEntity() {
    }
}
