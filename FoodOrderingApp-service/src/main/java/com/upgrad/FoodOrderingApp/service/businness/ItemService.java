package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.*;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CategoryDao categoryDao;


    //This method fetches and returns Items list (All items) based on popularity of a given restaurant

    public List<ItemEntity> getItemsByPopularity(RestaurantEntity restaurantEntity) {
        List<ItemEntity> itemEntityList = new ArrayList<ItemEntity>();
        for (OrdersEntity orderEntity : orderDao.getOrdersByRestaurant(restaurantEntity)) {
            for (OrderItemEntity orderItemEntity : orderItemDao.getItemsByOrder(orderEntity)) {
                itemEntityList.add(orderItemEntity.getItem_id());
            }
        }

        // counting all items with map
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (ItemEntity itemEntity : itemEntityList) {
            Integer count = map.get(itemEntity.getUuid());
            map.put(itemEntity.getUuid(), (count == null) ? 1 : count + 1);
        }

        Map<String, Integer> map1 = new TreeMap<String, Integer>(map);
        List<ItemEntity> sortedItemEntityList = new ArrayList<ItemEntity>();
        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            sortedItemEntityList.add(itemDao.getItemByUUID(entry.getKey()));
        }
        Collections.reverse(sortedItemEntityList);

        return sortedItemEntityList;
    }


}

