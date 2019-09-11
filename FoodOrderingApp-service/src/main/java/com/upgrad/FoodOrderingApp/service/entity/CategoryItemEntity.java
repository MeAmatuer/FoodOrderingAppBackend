package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="category_item")
public class CategoryItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="item_id")
    private ItemEntity item_id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="category_id")
    private CategoryEntity category_id;

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

    public CategoryEntity getCategory_id() {
        return category_id;
    }

    public void setCategory_id(CategoryEntity category_id) {
        this.category_id = category_id;
    }
}


