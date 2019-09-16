package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;


    public AddressEntity getAddressById(String addressId) throws AddressNotFoundException {

        AddressEntity addressEntity = addressDao.getAddressById(addressId);
        if(addressEntity == null){
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        }else {
            return addressEntity;
        }
    }
}
