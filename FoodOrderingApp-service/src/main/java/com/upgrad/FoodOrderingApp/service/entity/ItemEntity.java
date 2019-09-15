package com.upgrad.FoodOrderingApp.service.entity;
import com.upgrad.FoodOrderingApp.service.common.ItemType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="item")
public class ItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @Column(name="uuid")
    @Size(max=200)
    @NotNull
    private UUID uuid;

    @Column(name="item_name")
    @Size(max=30)
    @NotNull
    private String itemName;

    @NotNull
    @Column(name="price")
    private Integer price;

    @Column(name="type")
    @Size(max=10)
    @NotNull
    private ItemType type;

    @OneToMany(mappedBy = "itemId", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<OrderItemEntity> orderItem = new ArrayList<>();

    @OneToMany(mappedBy = "itemId", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<CategoryItemEntity> categoryItem = new ArrayList<>();

    @OneToMany(mappedBy = "itemId", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<RestaurantItemEntity> restaurantItem = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public List<OrderItemEntity> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemEntity> orderItem) {
        this.orderItem = orderItem;
    }

    public List<CategoryItemEntity> getCategoryItem() {
        return categoryItem;
    }

    public void setCategoryItem(List<CategoryItemEntity> categoryItem) {
        this.categoryItem = categoryItem;
    }

    public List<RestaurantItemEntity> getRestaurantItem() {
        return restaurantItem;
    }

    public void setRestaurantItem(List<RestaurantItemEntity> restaurantItem) {
        this.restaurantItem = restaurantItem;
    }

    public ItemEntity() {
    }
}
