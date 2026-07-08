package com.utkarsh.bookstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utkarsh.bookstore.exception.ResourceNotFoundException;
import com.utkarsh.bookstore.model.Category;
import com.utkarsh.bookstore.repository.CategoryRepository;
import com.utkarsh.bookstore.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with ID: " + id));
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category categoryDetails) {

        Category category = getCategoryById(id);

        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = getCategoryById(id);

        categoryRepository.delete(category);
    }
}