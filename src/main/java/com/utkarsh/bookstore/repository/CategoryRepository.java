package com.utkarsh.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utkarsh.bookstore.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Optional: Add custom query methods here

}