package com.upgrad.FoodOrderingApp.service.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="address")
@NamedQueries({
        @NamedQuery(name = "addressById", query = "select a from AddressEntity a where a.uuid = :addressId")
})
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
    private String flatBuilNumber;

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
    private StateEntity State;

    @NotNull
    @Column(name="active")
    private Integer active;

    @ManyToOne
    @JoinTable(name = "customer_address", joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private CustomerEntity customer;

    @OneToMany(mappedBy = "address", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<RestaurantEntity> restaurant = new ArrayList<>();

    @OneToMany(mappedBy = "address", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public AddressEntity() {}

    public AddressEntity(String uuid, String flatBuilNo, String locality, String city, String pincode, StateEntity stateEntity) {
        this.uuid = uuid;
        this.flatBuilNumber = flatBuilNo;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.State = stateEntity;
        this.active = 1;
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

    public String getFlatBuilNumber() {
        return flatBuilNumber;
    }

    public void setFlatBuilNumber(String flatBuilNumber) {
        this.flatBuilNumber = flatBuilNumber;
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

    public StateEntity getState() {
        return State;
    }

    public void setState(StateEntity state) {
        State = state;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public List<RestaurantEntity> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(List<RestaurantEntity> restaurant) {
        this.restaurant = restaurant;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
