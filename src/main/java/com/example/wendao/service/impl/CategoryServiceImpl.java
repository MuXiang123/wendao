package com.example.wendao.service.impl;

import com.example.wendao.entity.Category;
import com.example.wendao.mapper.CategoryMapper;
import com.example.wendao.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 11:13
 * @version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired(required = false)
    CategoryMapper categoryMapper;

    @Override
    public void insertCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategoryById(int categoryId) {
        categoryMapper.deleteCategoryById(categoryId);
    }

    @Override
    public Category selectCategoryById(int categoryId) {
        return categoryMapper.selectCategoryById(categoryId);
    }

    @Override
    public Category selectCategoryByName(String categoryName) {
        return categoryMapper.selectCategoryByName(categoryName);
    }

    @Override
    public List<Category> selectAllCategory() {
        return categoryMapper.selectAllCategory();
    }
}
