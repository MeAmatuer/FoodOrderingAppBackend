package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name="order_item")
@NamedQueries({
        @NamedQuery(name = "itemsByOrder", query = "select q from OrderItemEntity q where q.order = :ordersEntity"),
})
public class OrderItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="order_id")
    private OrdersEntity order;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="item_id")
    private ItemEntity item_id;

    @NotNull
    @Column(name="quantity")
    private Integer quantity;

    @NotNull
    @Column(name="price")
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }

    public ItemEntity getItem_id() {
        return item_id;
    }

    public void setItem_id(ItemEntity item_id) {
        this.item_id = item_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
