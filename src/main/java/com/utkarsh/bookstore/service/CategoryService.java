package com.utkarsh.bookstore.service;

import java.util.List;

import com.utkarsh.bookstore.model.Category;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}