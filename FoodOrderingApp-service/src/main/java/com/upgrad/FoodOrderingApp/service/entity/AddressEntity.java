package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="address")
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @Column(name="uuid")
    @Size(max=200)
    @NotNull
    private String uuid;

    @Column(name="flat_buil_number")
    @Size(max=255)
    @NotNull
    private String flat_buil_number;

    @Column(name="locality")
    @Size(max=255)
    @NotNull
    private String locality;

    @Column(name="city")
    @Size(max=30)
    @NotNull
    private String city;

    @Column(name="pincode")
    @Size(max=30)
    @NotNull
    private String pincode;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="state_id")
    private StateEntity state_id;

    @NotNull
    @Column(name="active")
    private Integer active;

    @OneToMany(mappedBy = "address", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<CustomerAddressEntity> customerAddresses = new ArrayList<>();

    @OneToMany(mappedBy = "address", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<RestaurantEntity> restaurant = new ArrayList<>();

    @OneToMany(mappedBy = "address", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<OrdersEntity> orders = new ArrayList<>();

    public List<OrdersEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersEntity> orders) {
        this.orders = orders;
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

    public String getFlat_buil_number() {
        return flat_buil_number;
    }

    public void setFlat_buil_number(String flat_buil_number) {
        this.flat_buil_number = flat_buil_number;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public StateEntity getState_id() {
        return state_id;
    }

    public void setState_id(StateEntity state_id) {
        this.state_id = state_id;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public List<CustomerAddressEntity> getCustomerAddresses() {
        return customerAddresses;
    }

    public void setCustomerAddresses(List<CustomerAddressEntity> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    public List<RestaurantEntity> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(List<RestaurantEntity> restaurant) {
        this.restaurant = restaurant;
    }

    public AddressEntity() {
    }
}
