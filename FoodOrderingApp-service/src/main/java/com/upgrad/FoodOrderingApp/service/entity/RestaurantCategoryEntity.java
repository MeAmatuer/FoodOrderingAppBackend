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
    private CategoryEntity category_id;

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

    public CategoryEntity getCategory_id() {
        return category_id;
    }

    public void setCategory_id(CategoryEntity category_id) {
        this.category_id = category_id;
    }
}
