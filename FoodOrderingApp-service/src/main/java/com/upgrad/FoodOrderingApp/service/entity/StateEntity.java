package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="state")
public class StateEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private Integer id;

    @Column(name="uuid")
    @Size(max=200)
    @NotNull
    private String uuid;

    @Column(name="state_name")
    @Size(max=30)
    @NotNull
    private String state_name;

    @OneToMany(mappedBy = "state_id", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<AddressEntity> Addresses = new ArrayList<>();

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

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public List<AddressEntity> getAddresses() {
        return Addresses;
    }

    public void setAddresses(List<AddressEntity> addresses) {
        Addresses = addresses;
    }
}
