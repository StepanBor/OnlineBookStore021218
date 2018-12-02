package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.DAO.CategoryDAO;
import com.gmail.stepan1983.model.CategoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public CategoryItem addCategoryItem(CategoryItem categoryItem) {
        return categoryDAO.save(categoryItem);
    }

    @Override
    public CategoryItem getById(Long categoryId) {
        return categoryDAO.getOne(categoryId);
    }

    @Override
    public void updateCategory(CategoryItem categoryItem) {
        categoryDAO.save(categoryItem);
    }

    @Override
    public CategoryItem getByName(String name) {
        return categoryDAO.getByName(name);
    }

    @Override
    public List<CategoryItem> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public long count() {
        return categoryDAO.count();
    }

    @Override
    public boolean existsByName(String name) {
        return categoryDAO.existsByName(name);
    }
}
