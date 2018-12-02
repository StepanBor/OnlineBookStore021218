package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.model.CategoryItem;
import com.gmail.stepan1983.model.Publisher;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryService {


    CategoryItem addCategoryItem(CategoryItem categoryItem);

    CategoryItem getById(Long categoryId);

    void updateCategory(CategoryItem categoryItem);

    List<CategoryItem> findAll();

    CategoryItem getByName(String name);

    boolean existsByName(String name);

    long count();
}
