package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    //This method returns all the categories list ordered by their names

    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        return categoryDao.getAllCategories().stream()
                .sorted(Comparator.comparing(CategoryEntity::getCategoryName))
                .collect(Collectors.toList());
    }


    //This method implements the business logic for 'getCategoryById' endpoint
    //it takes category Id as input parameter
    //And throws Category not found exceptions If the category id field is empty & If there are no categories available by the id provided

    public CategoryEntity getCategoryById(String categoryId) throws CategoryNotFoundException {

        if(categoryId.equals("")){
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryDao.getCategoryByUuid(categoryId);

        if(categoryEntity==null){
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        return categoryEntity;

    }

}
