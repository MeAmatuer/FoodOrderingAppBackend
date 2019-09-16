package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;


    public ItemEntity getItemByUUID(String itemId) throws ItemNotFoundException {
        ItemEntity itemEntity = itemDao.getItemById(itemId);
        if(itemEntity == null){
            throw new ItemNotFoundException("INF-003", "No item by this id exist");
        }else {
            return itemEntity;
        }
    }
}
