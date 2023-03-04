package com.example.wendao.mapper;

import com.example.wendao.entity.Category;

import java.util.List;

/**
 * @author: zhk
 * @description: 分类Mapper
 * @date: 2023/3/3 10:59
 * @version: 1.0
 */
public interface CategoryMapper {
    /**
     * 新增目录
     * @param category
     */
    void insertCategory(Category category);

    /**
     * 更新目录
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 根据id删除目录
     * @param categoryId
     */
    void deleteCategoryById(int categoryId);

    /**
     * 根据id查找目录
     * @param categoryId
     * @return
     */
    Category selectCategoryById(int categoryId);

    /**
     * 根据目录名查找目录
     * @param categoryName
     * @return
     */
    Category selectCategoryByName(String categoryName);

    /**
     * 查询所有目录
     * @return
     */
    List<Category> selectAllCategory();
}
