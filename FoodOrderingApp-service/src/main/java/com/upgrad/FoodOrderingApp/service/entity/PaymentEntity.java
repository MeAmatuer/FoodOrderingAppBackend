package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="payment")
@NamedQueries({
        @NamedQuery(name = "paymentsByPaymentId", query = "select p from PaymentEntity p where p.uuid = :paymentId"),
        @NamedQuery(name = "getPaymentMethods", query = "select p from PaymentEntity p")

})
public class PaymentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @Column(name="uuid")
    @Size(max=200)
    @NotNull
    private UUID uuid;

    @Column(name="payment_name")
    @Size(max=255)
    @NotNull
    private String paymentName;

    @OneToMany(mappedBy = "payment", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();

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

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public PaymentEntity() {
    }
}
