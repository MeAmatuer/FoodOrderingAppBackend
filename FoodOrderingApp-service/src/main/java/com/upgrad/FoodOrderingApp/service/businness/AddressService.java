package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CustomerAddressDao customerAddressDao;

    /**
     * Service Class method that is called to get the Address entity when the Address UUID is passed
     * @param addressId
     * @param customerEntity
     * @return
     * @throws AddressNotFoundException
     * @throws AuthorizationFailedException
     */
    public AddressEntity getAddressByUUID(String addressId, CustomerEntity customerEntity) throws AddressNotFoundException, AuthorizationFailedException{
        if (addressId == null) {
            throw new AddressNotFoundException("ANF-005","Address id can not be empty");
        }
        AddressEntity addressEntity = addressDao.getAddressById(addressId);
        if(addressEntity == null){
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        }

        CustomerAddressEntity customerAddressEntity = customerAddressDao.getCustomerAddressByAddress(addressEntity);

        if (!customerAddressEntity.getCustomer().getUuid().equals(customerEntity.getUuid())) {
            throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
        }
        return addressEntity;
    }

   }

