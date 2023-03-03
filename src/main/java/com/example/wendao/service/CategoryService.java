package com.example.wendao.service;

import com.example.wendao.entity.Category;

import java.util.List;

/**
 * @author: zhk
 * @description: 目录service
 * @date: 2023/3/3 11:12
 * @version: 1.0
 */

public interface CategoryService {
    /**
     * 新增
     * @param category
     */
    void insertCategory(Category category);

    /**
     * 更新
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 根据id删除
     * @param categoryId
     */
    void deleteCategoryById(int categoryId);

    /**
     * 根据id查询
     * @param categoryId
     * @return
     */
    Category selectCategoryById(int categoryId);

    /**
     * 根据名字查询
     * @param categoryName
     * @return
     */
    Category selectCategoryByName(String categoryName);

    /**
     * 查询所有
     * @return
     */
    List<Category> selectAllCategory();
}
