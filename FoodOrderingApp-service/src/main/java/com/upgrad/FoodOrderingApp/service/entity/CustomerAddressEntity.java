package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="customer_address")
@NamedQueries({
        @NamedQuery(name = "getAllCustomerAddressByCustomer",query = "SELECT c from CustomerAddressEntity c where c.customer = :customer_entity"),
        @NamedQuery(name = "getCustomerAddressByAddress",query = "SELECT c from CustomerAddressEntity c where c.address = :address_entity")
})
public class CustomerAddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="address_id")
    private AddressEntity address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public CustomerAddressEntity() {
    }
}
