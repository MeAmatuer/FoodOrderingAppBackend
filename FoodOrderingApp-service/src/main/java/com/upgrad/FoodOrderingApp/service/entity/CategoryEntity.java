package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
@NamedQueries({
        @NamedQuery(name="getCategoryByUuid", query = "select q from CategoryEntity q where q.uuid = :uuid")
})
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @Column(name="uuid")
    @Size(max=200)
    @NotNull
    private String uuid;

    @Column(name="category_name")
    @Size(max=255)
    @NotNull
    private String category_name;

    /*@OneToMany(mappedBy = "category_id", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<RestaurantCategoryEntity> restaurantCategory = new ArrayList<>();*/

    @ManyToMany
    @JoinTable(name = "restaurant_category", joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    private List<RestaurantEntity> restaurants = new ArrayList<>();

    @OneToMany(mappedBy = "category_id", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<CategoryItemEntity> categoryItem = new ArrayList<>();

    public List<RestaurantEntity> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantEntity> restaurants) {
        this.restaurants = restaurants;
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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    /*public List<RestaurantCategoryEntity> getRestaurantCategory() {
        return restaurantCategory;
    }

    public void setRestaurantCategory(List<RestaurantCategoryEntity> restaurantCategory) {
        this.restaurantCategory = restaurantCategory;
    }*/

    public List<CategoryItemEntity> getCategoryItem() {
        return categoryItem;
    }

    public void setCategoryItem(List<CategoryItemEntity> categoryItem) {
        this.categoryItem = categoryItem;
    }
}
